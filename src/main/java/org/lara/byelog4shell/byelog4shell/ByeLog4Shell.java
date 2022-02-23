package org.lara.byelog4shell.byelog4shell;

import java.io.IOException;
import net.md_5.bungee.api.plugin.Plugin;
import org.lara.byelog4shell.byelog4shell.config.Config;
import org.lara.byelog4shell.byelog4shell.config.JsonConfig;
import org.lara.byelog4shell.byelog4shell.listener.DiscordListener;
import org.lara.byelog4shell.byelog4shell.listener.PlayerChatListener;

public final class ByeLog4Shell extends Plugin {

  @Override
  public void onEnable() {
    try {
      new JsonConfig().createConfig(getDataFolder());
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    DiscordListener discordListener = new DiscordListener(JsonConfig
      .getString(Config.DISCORD_LINK), this);
    new PlayerChatListener(this, discordListener);
  }
}
