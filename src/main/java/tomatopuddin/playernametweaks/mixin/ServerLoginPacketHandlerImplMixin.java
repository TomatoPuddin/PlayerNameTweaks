package tomatopuddin.playernametweaks.mixin;

import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ServerLoginPacketListenerImpl.class)
public class ServerLoginPacketHandlerImplMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public static boolean isValidUsername(String p_203793_) {
        return true;
    }
}
