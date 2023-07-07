package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerboundHelloPacket.class)
public class ServerboundHelloPacketMixin {

    @Redirect(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/FriendlyByteBuf;readUtf(I)Ljava/lang/String;"))
    private String readUtfName(FriendlyByteBuf instance, int i) {
        return instance.readUtf();
    }
}
