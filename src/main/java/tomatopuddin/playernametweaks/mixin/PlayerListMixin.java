package tomatopuddin.playernametweaks.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tomatopuddin.playernametweaks.PlayerNameTweaksMod;

import java.net.SocketAddress;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(method = "canPlayerLogin(Ljava/net/SocketAddress;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/util/text/ITextComponent;",
            at = @At("HEAD"), cancellable = true)
    public void canPlayerLogin(SocketAddress p_206258_1_, GameProfile profile, CallbackInfoReturnable<ITextComponent> cir) {
        PlayerNameTweaksMod.getConfig().checkPlayerName(profile.getId(), profile.getName(), cir::setReturnValue);
    }
}
