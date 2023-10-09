package tomatopuddin.playernametweaks.mixin;

import net.minecraft.server.players.OldUsersConverter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(OldUsersConverter.class)
public class OldUserConvertMixin {
    @ModifyConstant(method = "convertMobOwnerIfNecessary(Lnet/minecraft/server/MinecraftServer;Ljava/lang/String;)Ljava/util/UUID;", constant = @Constant(intValue = 16), expect = 1)
    private static int maxStringLength(int length) {
        return Integer.MAX_VALUE;
    }
}
