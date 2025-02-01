package tomatopuddin.playernametweaks.mixin;

import net.minecraft.util.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StringUtil.class)
public class StringUtilMixin {
    @Inject(method = "isValidPlayerName(Ljava/lang/String;)Z", at = @At("HEAD"), cancellable = true)
    private static void isValidPlayerName(String string, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
