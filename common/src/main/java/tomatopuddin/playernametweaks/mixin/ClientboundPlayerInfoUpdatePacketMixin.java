package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Action")
public class ClientboundPlayerInfoUpdatePacketMixin {

    @Redirect(method = "method_46342(Lnet/minecraft/class_2703$class_7831;Lnet/minecraft/class_2540;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;readUtf(I)Ljava/lang/String;"))
    private static String readUtfName(FriendlyByteBuf instance, int i) {
        return instance.readUtf();
    }

    @Redirect(method = "method_46341(Lnet/minecraft/class_2540;Lnet/minecraft/class_2703$class_2705;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;writeUtf(Ljava/lang/String;I)Lnet/minecraft/network/FriendlyByteBuf;"))
    private static FriendlyByteBuf writeUtfName(FriendlyByteBuf instance, String name, int i) {
        instance.writeUtf(name);
        return instance;
    }
}
