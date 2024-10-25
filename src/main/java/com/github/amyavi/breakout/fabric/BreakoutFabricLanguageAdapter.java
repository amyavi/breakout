package com.github.amyavi.breakout.fabric;

import com.github.amyavi.breakout.Breakout;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.LanguageAdapterException;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public final class BreakoutFabricLanguageAdapter implements LanguageAdapter {
    @Override
    public <T> T create(final ModContainer mod, final String value, final Class<T> type) throws LanguageAdapterException {
        if (type != PreLaunchEntrypoint.class)
            throw new LanguageAdapterException("Language adapter can only be used with PreLaunchEntrypoint");
        return type.cast((PreLaunchEntrypoint) () -> {
        });
    }

    static {
        Breakout.init();
    }
}