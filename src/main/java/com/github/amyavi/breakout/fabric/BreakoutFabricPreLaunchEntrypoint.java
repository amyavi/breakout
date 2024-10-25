package com.github.amyavi.breakout.fabric;

import com.github.amyavi.breakout.Breakout;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public final class BreakoutFabricPreLaunchEntrypoint implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
    }

    static {
        Breakout.init();
    }
}
