package org.lara.byelog4shell.byelog4shell.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscordListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DiscordListener.class);

  private final String link;
  private final Plugin plugin;
  private String content;
  private String username;
  private String avatarUrl;

  public void sendWebHook() {
    URL url;
    HttpsURLConnection connection;

    try {
      if (link.isBlank()) return;
      url = new URL(this.link);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.addRequestProperty("Content-Type", "application/json");
      connection.addRequestProperty("User-Agent", "Mozilla/5.0");

      JSONObject post = new JSONObject();
      post.put("content", content)
          .put("username", username);

      if (avatarUrl != null) post.put("avatar_url", avatarUrl);

      try (OutputStream stream = connection.getOutputStream()) {
        if (stream == null) return;
        stream.write(post.toString().getBytes(StandardCharsets.UTF_8));
        stream.flush();
      }

      connection.getInputStream().close();
      connection.disconnect();
    } catch (IOException | JSONException e) {
      LOGGER.error("An error occurred while creating or sending the" +
        " Discord Webhook", e);
    }
  }

  public void runTask() {
    ProxyServer.getInstance().getScheduler().runAsync(plugin, this::sendWebHook);
  }

  public DiscordListener (String url, Plugin plugin) {
    this.link = url;
    this.plugin = plugin;
  }

  public DiscordListener setContent(String content) {
    this.content = content;
    return this;
  }

  public DiscordListener setUsername(String username) {
    this.username = username;
    return this;
  }

  public DiscordListener setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
    return this;
  }
}
