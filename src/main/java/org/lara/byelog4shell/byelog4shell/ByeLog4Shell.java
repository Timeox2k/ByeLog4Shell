package org.lara.byelog4shell.byelog4shell;

import java.io.IOException;
import net.md_5.bungee.api.plugin.Plugin;
import org.lara.byelog4shell.byelog4shell.config.Config;
import org.lara.byelog4shell.byelog4shell.config.JsonConfig;
import org.lara.byelog4shell.byelog4shell.listener.DiscordListener;
import org.lara.byelog4shell.byelog4shell.listener.PlayerChatListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ByeLog4Shell extends Plugin {

  private static final Logger LOGGER = LoggerFactory.getLogger(ByeLog4Shell.class);

  @Override
  public void onEnable() {
    try {
      new JsonConfig().createConfig(getDataFolder());
    } catch (IOException e) {
      LOGGER.error("An error occurred while creating config file", e);
    }

    DiscordListener discordListener = new DiscordListener(JsonConfig
      .getString(Config.DISCORD_LINK), this);
    new PlayerChatListener(this, discordListener);
  }
}
