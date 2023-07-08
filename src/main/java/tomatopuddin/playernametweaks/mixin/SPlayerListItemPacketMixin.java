package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SPlayerListItemPacket.class)
public class SPlayerListItemPacketMixin {

    @Redirect(method = "readPacketData(Lnet/minecraft/network/PacketBuffer;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/PacketBuffer;readString(I)Ljava/lang/String;", ordinal = 0))
    private String readUtfName(PacketBuffer instance, int i) {
        return instance.readString();
    }
}
