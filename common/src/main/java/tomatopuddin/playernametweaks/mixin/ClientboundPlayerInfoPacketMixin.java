package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket$Action$1")
public class ClientboundPlayerInfoPacketMixin {

    @Redirect(method = "Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoPacket$Action$1;read(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoPacket$PlayerUpdate;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;readUtf(I)Ljava/lang/String;"))
    private String readUtfName(FriendlyByteBuf instance, int i) {
        return instance.readUtf();
    }
}
