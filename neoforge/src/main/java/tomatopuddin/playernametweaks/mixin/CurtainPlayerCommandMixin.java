package tomatopuddin.playernametweaks.mixin;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(dev.dubhe.curtain.commands.PlayerCommand.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public class CurtainPlayerCommandMixin {
    @Redirect(method = "register(Lcom/mojang/brigadier/CommandDispatcher;)V", remap = false, require = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/Commands;argument(Ljava/lang/String;Lcom/mojang/brigadier/arguments/ArgumentType;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;"))
    private static RequiredArgumentBuilder argument(String string, ArgumentType argumentType) {
        if ("player".equals(string) && argumentType instanceof StringArgumentType) {
            argumentType = StringArgumentType.string();
        }
        return RequiredArgumentBuilder.argument(string, argumentType);
    }

    @Inject(method = "getPlayers(Lnet/minecraft/commands/CommandSourceStack;)Ljava/util/Collection;", require = 0,
            at = @At(value = "TAIL"), cancellable = true)
    private static void getPlayerSuggestionsEx(CommandSourceStack source, CallbackInfoReturnable<Collection<String>> cir) {
        var list = cir.getReturnValue();
        if (list != null && !list.isEmpty()) {
            cir.setReturnValue(list.stream().map(name -> playernametweaks$isValidName(name) ? name : String.format("\"%s\"", name)).collect(Collectors.toList()));
        }
    }

    @Unique
    private static boolean playernametweaks$isValidName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!StringReader.isAllowedInUnquotedString(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
