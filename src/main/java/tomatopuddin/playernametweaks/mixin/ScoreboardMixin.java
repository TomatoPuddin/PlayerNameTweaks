package tomatopuddin.playernametweaks.mixin;

import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Scoreboard.class)
public class ScoreboardMixin {

    @Redirect(method = {
            "getOrCreateScore(Ljava/lang/String;Lnet/minecraft/scoreboard/ScoreObjective;)Lnet/minecraft/scoreboard/Score;",
            "addPlayerToTeam(Ljava/lang/String;Lnet/minecraft/scoreboard/ScorePlayerTeam;)Z",
            "func_197905_a(Lnet/minecraft/nbt/ListNBT;)V"},
            at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I", remap = false))
    private int length(String instance) {
        return 7;
    }
}
