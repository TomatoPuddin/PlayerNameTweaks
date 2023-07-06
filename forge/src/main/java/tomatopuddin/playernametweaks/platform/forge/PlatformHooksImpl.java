package tomatopuddin.playernametweaks.platform.forge;

import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.maven.artifact.versioning.ComparableVersion;

public class PlatformHooksImpl {

    public static String getGameVersion() {
        return FMLLoader.versionInfo().mcVersion();
    }

    public static int compareVersion(String ver1, String ver2) {
        return new ComparableVersion(ver1).compareTo(new ComparableVersion((ver2)));
    }
}
