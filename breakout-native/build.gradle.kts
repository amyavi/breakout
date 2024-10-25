import org.gradle.internal.jvm.Jvm

plugins {
    `cpp-library`
}

val jvmHome = Jvm.current().javaHome

library {
    linkage = listOf(Linkage.SHARED)
    targetMachines = listOf(machines.linux.x86_64)

    binaries.configureEach {
        this as CppSharedLibrary

        // builds a statically linked dynamic library
        // this shit is CURSED bro
        linkTask.get().linkerArgs = listOf("-nodefaultlibs", "-fPIC", "-s", "-flto")
    }
}

tasks.withType<CppCompile>().configureEach {
    compilerArgs.addAll("-Wl,-z,noexecstack", "-s", "-flto")

    includes.from(jvmHome.resolve("include"))
    includes.from(jvmHome.resolve("include/linux"))
}

// TODO: figure out a way to get rid of the debug thing
tasks.assemble {
    dependsOn("assembleRelease")
}
