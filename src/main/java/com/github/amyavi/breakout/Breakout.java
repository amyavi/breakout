package com.github.amyavi.breakout;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public final class Breakout {
    private static final File CONFIG_FILE = new File("./config/breakout.txt");
    private static final List<String> DEFAULT_PARAMS = List.of("/bin/sh", "./start.sh");

    private Breakout() {
    }

    public static void log(final String format, final Object... args) {
        System.out.printf("[breakout] " + format + "%n", args);
        for (final Object arg : args) {
            if (!(arg instanceof Throwable)) continue;

            final StackTraceElement[] stack = ((Throwable) arg).getStackTrace();
            for (final StackTraceElement elem : stack) {
                System.out.println("\tat " + elem);
            }
        }
    }

    public static List<String> getLaunchParameters(final File file) {
        final List<String> out = new ArrayList<>();

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) out.add(line);
        } catch (final FileNotFoundException e) {
            log("failed to open %s: file not found", file.getPath());
        } catch (final IOException e) {
            log("failed to open %s: %s", file.getPath(), e);
        }

        return out;
    }

    public static void init() {
        if (System.getProperty("breakout.init") != null) {
            return;
        }
        System.setProperty("breakout.init", "true");
        log("initialized in %dms", ManagementFactory.getRuntimeMXBean().getUptime());

        final List<String> launchParams = getLaunchParameters(CONFIG_FILE);
        if (launchParams.isEmpty()) {
            launchParams.addAll(DEFAULT_PARAMS);
            log("defaulting launch params");
        }

        log("launch parameters: [%s]", String.join(", ", launchParams));
        log("okay, execvp'ing now");
        try {
            final int ret = BreakoutNative.execvp(launchParams.getFirst(), launchParams.toArray(new String[0]));
            log("execvp returned: %d", ret);
        } catch (final UnsatisfiedLinkError e) {
            log("failed to call execvp: %s", e);
        }

        log("halting JVM...");
        Runtime.getRuntime().exit(-1);
    }
}
