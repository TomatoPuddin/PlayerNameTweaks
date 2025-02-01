package tomatopuddin.playernametweaks.mixin;

import com.mojang.brigadier.arguments.StringArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tomatopuddin.playernametweaks.PlayerNameTweaks;

import java.nio.charset.Charset;

@Mixin(targets = "net.minecraft.server.dedicated.DedicatedServer$1")
public class ServerConsoleHandlerMixin {
    @Redirect(method = "run", remap = false, at = @At(value = "FIELD", target = "Ljava/nio/charset/StandardCharsets;UTF_8:Ljava/nio/charset/Charset;", remap = false))
    public Charset getConsoleCharset() {
        StringArgumentType.word();
        Charset consoleCharset = null;
        var nativeEncoding = System.getProperties().getProperty("native.encoding");
        if (nativeEncoding != null) {
            consoleCharset = Charset.forName(nativeEncoding);
            PlayerNameTweaks.LOGGER.info("Set console charset to system native: {}", consoleCharset.name());
        }
        if (consoleCharset == null) {
            consoleCharset = Charset.defaultCharset();
            PlayerNameTweaks.LOGGER.info("Set console charset to jvm default: {}", consoleCharset.name());
        }
        return consoleCharset;
    }
}
