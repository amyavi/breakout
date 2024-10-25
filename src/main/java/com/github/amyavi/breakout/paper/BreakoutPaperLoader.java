package com.github.amyavi.breakout.paper;

import com.github.amyavi.breakout.Breakout;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;

public final class BreakoutPaperLoader implements PluginLoader {
    @Override
    public void classloader(final PluginClasspathBuilder pluginClasspathBuilder) {
    }

    static {
        Breakout.init();
    }
}
