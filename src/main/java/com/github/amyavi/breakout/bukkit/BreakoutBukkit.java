package com.github.amyavi.breakout.bukkit;

import com.github.amyavi.breakout.Breakout;
import org.bukkit.plugin.java.JavaPlugin;

public final class BreakoutBukkit extends JavaPlugin {
    static {
        Breakout.init();
    }
}
