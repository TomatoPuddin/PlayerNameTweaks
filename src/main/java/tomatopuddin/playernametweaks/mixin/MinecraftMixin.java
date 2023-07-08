package tomatopuddin.playernametweaks.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ErrorScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Session;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomatopuddin.playernametweaks.PlayerNameTweaksMod;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Final
    private Session session;

    @Inject(method = "loadWorld(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    public void loadWorld(String worldName, CallbackInfo ci) {
        playerNameTweaks$checkPlayerName(ci);
    }

    @Inject(method = "createWorld(Ljava/lang/String;Lnet/minecraft/world/WorldSettings;Lnet/minecraft/util/registry/DynamicRegistries$Impl;Lnet/minecraft/world/gen/settings/DimensionGeneratorSettings;)V", at = @At("HEAD"), cancellable = true)
    public void createWorld(String worldName, WorldSettings worldSettings, DynamicRegistries.Impl dynamicRegistriesIn, DimensionGeneratorSettings dimensionGeneratorSettings, CallbackInfo ci) {
        playerNameTweaks$checkPlayerName(ci);
    }

    @Unique
    private void playerNameTweaks$checkPlayerName(CallbackInfo ci) {
        GameProfile profile = session.getProfile();
        PlayerNameTweaksMod.getConfig().checkPlayerName(profile.getId(), profile.getName(), hint -> {
            displayGuiScreen(new ErrorScreen(new StringTextComponent("Invalid Player Name"), hint));
            ci.cancel();
        });
    }

    @Shadow
    public void displayGuiScreen(@Nullable Screen guiScreenIn) {
    }
}
