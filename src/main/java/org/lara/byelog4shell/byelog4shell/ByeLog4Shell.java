package org.lara.byelog4shell.byelog4shell;

import net.md_5.bungee.api.plugin.Plugin;
import org.lara.byelog4shell.byelog4shell.listener.PlayerChatListener;

public final class ByeLog4Shell extends Plugin {

    @Override
    public void onEnable() {
        new PlayerChatListener(this);
    }
}
