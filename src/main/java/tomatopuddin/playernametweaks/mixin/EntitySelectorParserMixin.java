package tomatopuddin.playernametweaks.mixin;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntitySelectorParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashSet;

@Mixin(EntitySelectorParser.class)
public class EntitySelectorParserMixin {
    @Unique
    private static final HashSet<Character> playerNameTweaks$stopChars = new HashSet<>(Lists.charactersOf(" \t"));

    @Redirect(method = "parseSingleEntity",
            at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I", remap = false))
    public int length(String instance) {
        return 7;
    }

    @Redirect(method = "parseSingleEntity",
            at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;readString()Ljava/lang/String;", remap = false))
    public String readPlayerName(StringReader reader) throws CommandSyntaxException {
        if (!reader.canRead()) {
            return "";
        }
        final char next = reader.peek();
        if (StringReader.isQuotedStringStart(next)) {
            reader.skip();
            return reader.readStringUntil(next);
        }
        return playerNameTweaks$readUnquotedStringEx(reader);
    }

    @Unique
    private String playerNameTweaks$readUnquotedStringEx(StringReader reader) {
        final int start = reader.getCursor();
        while (reader.canRead() && !playerNameTweaks$stopChars.contains(reader.peek())) {
            reader.skip();
        }
        return reader.getString().substring(start, reader.getCursor());
    }
}
