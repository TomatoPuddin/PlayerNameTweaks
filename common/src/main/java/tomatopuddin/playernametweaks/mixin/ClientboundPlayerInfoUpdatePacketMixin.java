package tomatopuddin.playernametweaks.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientboundPlayerInfoUpdatePacket.Action.class)
public class ClientboundPlayerInfoUpdatePacketMixin {
    @Redirect(method = "method_46342(Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$EntryBuilder;Lnet/minecraft/network/RegistryFriendlyByteBuf;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/RegistryFriendlyByteBuf;readUtf(I)Ljava/lang/String;"))
    private static String readUtfName(RegistryFriendlyByteBuf instance, int len) {
        return instance.readUtf();
    }

    @Redirect(method = "method_46341(Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/RegistryFriendlyByteBuf;writeUtf(Ljava/lang/String;I)Lnet/minecraft/network/FriendlyByteBuf;"))
    private static FriendlyByteBuf writeUtfName(RegistryFriendlyByteBuf instance, String name, int len) {
        instance.writeUtf(name);
        return instance;
    }
}
