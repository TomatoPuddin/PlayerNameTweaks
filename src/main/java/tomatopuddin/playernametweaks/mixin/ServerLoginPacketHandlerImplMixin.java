package tomatopuddin.playernametweaks.mixin;

import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// do not overwrite isValidUsername directly.
@Mixin(ServerLoginPacketListenerImpl.class)
public class ServerLoginPacketHandlerImplMixin {

    @Redirect(method = "handleHello(Lnet/minecraft/network/protocol/login/ServerboundHelloPacket;)V",
    at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/Validate;validState(ZLjava/lang/String;[Ljava/lang/Object;)V", remap = false))
    void validState(boolean expression, String message, Object[] values) {
        if (expression || message.toLowerCase().contains("username")) return;
        throw new IllegalStateException(String.format(message, values));
    }
}
