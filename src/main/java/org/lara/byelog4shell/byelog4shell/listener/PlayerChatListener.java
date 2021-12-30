package org.lara.byelog4shell.byelog4shell.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
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
        String message = event.getMessage();

        if(message.toLowerCase().startsWith("${jndi:")) {
            event.setCancelled(true);
            event.getSender().disconnect(new TextComponent("§cHow stupid do you think we are?\n\n§c§lGo get a Job."));
        }
    }

}
