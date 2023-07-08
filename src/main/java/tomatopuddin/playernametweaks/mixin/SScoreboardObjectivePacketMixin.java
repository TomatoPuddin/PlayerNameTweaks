package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SScoreboardObjectivePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SScoreboardObjectivePacket.class)
public class SScoreboardObjectivePacketMixin {

    @Redirect(method = "readPacketData(Lnet/minecraft/network/PacketBuffer;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketBuffer;readString(I)Ljava/lang/String;"))
    private String readUtfName(PacketBuffer instance, int i) {
        return instance.readString();
    }
}
