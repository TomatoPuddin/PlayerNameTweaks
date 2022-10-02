package tomatopuddin.playernametweaks.mixin;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntitySelectorParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashSet;

@Mixin(EntitySelectorParser.class)
public class EntitySelectorParserMixin {
    private static final HashSet<Character> invalidChars = new HashSet<>(Lists.charactersOf(" "));

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
        return readUnquotedStringEx(reader);
    }

    private String readUnquotedStringEx(StringReader reader) {
        final int start = reader.getCursor();
        while (reader.canRead() && !invalidChars.contains(reader.peek())) {
            reader.skip();
        }
        return reader.getString().substring(start, reader.getCursor());
    }
}
