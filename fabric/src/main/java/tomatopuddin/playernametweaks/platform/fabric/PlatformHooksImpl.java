package tomatopuddin.playernametweaks.platform.fabric;

import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.impl.FabricLoaderImpl;

public class PlatformHooksImpl {

    public static String getGameVersion() {
        return FabricLoaderImpl.INSTANCE.tryGetGameProvider().getNormalizedGameVersion();
    }

    public static int compareVersion(String ver1, String ver2) throws VersionParsingException {
        return Version.parse(ver1).compareTo(Version.parse(ver2));
    }
}
