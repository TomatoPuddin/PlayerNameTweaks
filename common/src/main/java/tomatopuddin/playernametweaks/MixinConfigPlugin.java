package tomatopuddin.playernametweaks;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import tomatopuddin.playernametweaks.platform.PlatformHooks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    private final Map.Entry<String, String>[] additionalMixins = new Map.Entry[]{
            Map.entry("1.18.2", "ServerLoginPacketHandlerImplMixin")
    };

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        String gameVer = PlatformHooks.getGameVersion();
        return Arrays.stream(additionalMixins)
                .filter(pair -> PlatformHooks.compareVersion(gameVer, pair.getKey()) >= 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
