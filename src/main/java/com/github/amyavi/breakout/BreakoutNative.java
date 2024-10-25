package com.github.amyavi.breakout;

import java.io.*;

public final class BreakoutNative {
    private static File createLibraryTemp(final String suffix) throws IOException {
        try {
            return File.createTempFile("lib", suffix);
        } catch (final IOException e) {
            // explicit toString because exception is spammy
            Breakout.log("failed to create file in temp directory: %s", e.toString());
        }

        return File.createTempFile("lib", suffix, new File("./"));
    }

    private static void pipe(final InputStream in, final File out) throws IOException {
        final FileOutputStream outStream = new FileOutputStream(out);

        final byte[] buf = new byte[4096];
        int n;
        while ((n = in.read(buf)) != -1) {
            outStream.write(buf, 0, n);
        }

        outStream.close();
    }

    public static native int execvp(final String file, final String[] argv);

    static {
        try {
            final InputStream nativeInputStream = BreakoutNative.class.getResourceAsStream("/libbreakout-native.so");
            if (nativeInputStream == null) throw new FileNotFoundException("native not found in resources");

            final File tempFile = createLibraryTemp(".so");
            tempFile.deleteOnExit();

            pipe(nativeInputStream, tempFile);
            nativeInputStream.close();

            System.load(tempFile.getPath());

            final boolean ignored = tempFile.delete();
        } catch (final Exception e) {
            Breakout.log("failed to initialize native library: %s", e);
        }
    }
}
