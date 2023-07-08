package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.STeamsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(STeamsPacket.class)
public class STeamsPacketMixin {

    @Redirect(method = "readPacketData(Lnet/minecraft/network/PacketBuffer;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/PacketBuffer;readString(I)Ljava/lang/String;", ordinal = 3))
    private String readUtfName(PacketBuffer instance, int i) {
        return instance.readString();
    }
}
