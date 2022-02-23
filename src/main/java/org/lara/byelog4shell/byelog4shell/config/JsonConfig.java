package org.lara.byelog4shell.byelog4shell.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonConfig {

  private File file;
  private static JSONObject jsonObject;
  

  public void createConfig(File dataFolder) throws IOException {

    file = new File(dataFolder + "/config.json");

    if (!dataFolder.exists()) {
      dataFolder.mkdir();
    }

    if (!file.exists()) {
      file.createNewFile();
      jsonObject = new JSONObject();
      writeDefaultValue();
      writeConfig();
    }

    try (FileReader fileReader = new FileReader(file)) {
      JSONTokener jsonTokener = new JSONTokener(fileReader);
      jsonObject = new JSONObject(jsonTokener);
      // config created
    } catch (IOException | JSONException exception) {
      exception.printStackTrace();
    }
  }

  public void writeConfig() {
    try (FileWriter writer = new FileWriter(file)){
      // pretty print jsonObject
      writer.write(jsonObject.toString(4));
      writer.flush();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public void writeDefaultValue() {
    try {
      for (Config config : Config.values()) {
        // Write default value
        jsonObject.put(config.getKey(), config.getValue());
      }
    } catch (JSONException exception) {
      exception.printStackTrace();
    }
  }

  public static String getString(Config config) {
    try {
      return (String) jsonObject.get(config.getKey());
    } catch (JSONException | NullPointerException exception) {
      exception.printStackTrace();
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
      return getString(config)
                .replace("%player%", proxiedPlayer)
                .replace("%content%", message);
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
    return null;
  }

}
