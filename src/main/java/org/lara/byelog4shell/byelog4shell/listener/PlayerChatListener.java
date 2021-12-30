package org.lara.byelog4shell.byelog4shell.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {

    public PlayerChatListener(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        final String message = event.getMessage();
        final ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
        final ProxyServer proxyServer = ProxyServer.getInstance();

        if(message.toLowerCase().startsWith("${jndi:")) {
            event.setCancelled(true);
            proxiedPlayer.disconnect(new TextComponent("§cHow stupid do you think we are?\n\n§c§lGo get a Job."));

            final String prefix = "§cByeLog4Shell §8> ";
            proxyServer.getPlayers().forEach(player -> {if (player.hasPermission("ByeLog4Shell.notify")) proxiedPlayer.sendMessage(new TextComponent(prefix + "§cThe Player §4" + player.getName() + " §ctried to use §4Log4Shell§c!"));});
            proxyServer.getLogger().info(proxiedPlayer.getName() + " tried to use the Log4Shell Exploit!");

        }
    }

}
