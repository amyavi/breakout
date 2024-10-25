package com.github.amyavi.breakout.paper;

import com.github.amyavi.breakout.Breakout;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public final class BreakoutPaperBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(final BootstrapContext bootstrapContext) {
    }

    static {
        Breakout.init();
    }
}
