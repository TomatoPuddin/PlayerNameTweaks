package tomatopuddin.playernametweaks.mixin;

import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLoginPacketListenerImpl.class)
public class ServerLoginPacketHandlerImplMixin {

    @Inject(method = "isValidUsername(Ljava/lang/String;)Z", at = @At("HEAD"), cancellable = true)
    private static void isValidUsername(String string, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
