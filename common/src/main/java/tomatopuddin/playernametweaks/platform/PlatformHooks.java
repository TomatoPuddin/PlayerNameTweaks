package tomatopuddin.playernametweaks.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class PlatformHooks {

    @ExpectPlatform
    public static String getGameVersion() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int compareVersion(String ver1, String ver2) {
        throw new AssertionError();
    }
}
