package org.lara.byelog4shell.byelog4shell.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.lara.byelog4shell.byelog4shell.config.Config;
import org.lara.byelog4shell.byelog4shell.config.JsonConfig;

public class PlayerChatListener implements Listener {

    public PlayerChatListener(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        final String message = event.getMessage();
        final ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
        final ProxyServer proxyServer = ProxyServer.getInstance();

        if(message.toLowerCase().contains("${jndi:")) {
            event.setCancelled(true);
            proxiedPlayer.disconnect(TextComponent.fromLegacyText(ChatColor.RED + JsonConfig.getString(Config.KICK_MESSAGE)));

            if (JsonConfig.getBoolean(Config.INGAME_STATE)) {
                final String prefix = "§cByeLog4Shell §8> ";
                proxyServer.getPlayers().forEach(player -> {
                    if (player.hasPermission("byelog4shell.notify"))
                        proxiedPlayer.sendMessage(JsonConfig.getHolderValue(Config.INGAME_MESSAGE, proxiedPlayer.getName(), message));
                });
            }

            if (!JsonConfig.getBoolean(Config.DISCORD_STATE)) return;
            new DiscordListener(JsonConfig.getString(Config.DISCORD_LINK))
                .setContent(JsonConfig.getHolderValue(Config.DISCORD_MESSAGE, proxiedPlayer.getName(), message))
                .setAvatarUrl(" https://crafatar.com/avatars/" + proxiedPlayer.getUniqueId())
                .setUsername(proxiedPlayer.getName())
                .runTask();
        }
    }
}
