package org.lara.byelog4shell.byelog4shell.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.lara.byelog4shell.byelog4shell.ByeLog4Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(ByeLog4Shell.class);

  private File file;
  private static JSONObject jsonObject;

  public void createConfig(File dataFolder) throws IOException {

    file = new File(dataFolder + "/config.json");

    if (!dataFolder.exists()) {
      if (!dataFolder.mkdir()) {
        LOGGER.error("Could not create data folder");
      }
    }

    if (!file.exists()) {
      if (!file.createNewFile()) {
        LOGGER.error("Could not create config file");
      }
      setJsonObject(new JSONObject());
      writeDefaultValue();
      writeConfig();
    }

    try (FileReader fileReader = new FileReader(file)) {
      JSONTokener jsonTokener = new JSONTokener(fileReader);
      setJsonObject(new JSONObject(jsonTokener));
      // config created
    } catch (IOException | JSONException e) {
      LOGGER.error("Could not read the config file", e);
    }
  }

  public void writeConfig() {
    try (FileWriter writer = new FileWriter(file)){
      // pretty print jsonObject
      writer.write(jsonObject.toString(4));
      writer.flush();
    } catch (IOException e) {
      LOGGER.error("Could not write config file", e);
    }
  }

  public void writeDefaultValue() {
    try {
      for (Config config : Config.values()) {
        // Write default value
        jsonObject.put(config.getKey(), config.getValue());
      }
    } catch (JSONException e) {
      LOGGER.error("Could not put the default values into the config", e);
    }
  }

  public static String getString(Config config) {
    try {
      return (String) jsonObject.get(config.getKey());
    } catch (JSONException | NullPointerException e) {
      LOGGER.error("Could not get the value {} from the config", config.getKey(), e);
    }
    return null;
  }

  public static boolean getBoolean(Config config) {
    try {
      return (boolean) jsonObject.get(config.getKey());
    } catch (JSONException | NullPointerException exception) {
      return false;
    }
  }

  public static String getHolderValue(Config config, String proxiedPlayer, String message) {
    try {
      String string = getString(config);
      if (string == null) return null;
      return string
                .replace("%player%", proxiedPlayer)
                .replace("%content%", message);
    } catch (NullPointerException e) {
      LOGGER.error("Could not get the value {} from the config", config.getKey(), e);
    }
    return null;
  }

  private static void setJsonObject(JSONObject jsonObject) {
    JsonConfig.jsonObject = jsonObject;
  }

}
