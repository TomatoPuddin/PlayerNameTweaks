package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FriendlyByteBuf.class)
public class FriendlyByteBufMixin {

    @Redirect(method = "readGameProfile()Lcom/mojang/authlib/GameProfile;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;readUtf(I)Ljava/lang/String;"))
    private String readUtfName(FriendlyByteBuf instance, int i) {
        return instance.readUtf();
    }

    // mysql直呼内行
    @Inject(method = "getMaxEncodedUtfLength(I)I",
            at = @At("HEAD"), cancellable = true)
    private static void getMaxEncodedUtf8mb4Length(int i, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(i * 4);
    }
}
