package tomatopuddin.playernametweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tomatopuddin.playernametweaks.PlayerNameTweaksMod;

import java.nio.charset.Charset;

@Mixin(targets = "net.minecraft.server.dedicated.DedicatedServer$1")
public class ServerConsoleHandlerMixin {

    @Redirect(method = "run", remap = false,
            at = @At(value = "FIELD", target = "Ljava/nio/charset/StandardCharsets;UTF_8:Ljava/nio/charset/Charset;", remap = false))
    public Charset getConsoleCharset() {
        Charset consoleCharset = Charset.defaultCharset();
        PlayerNameTweaksMod.LOGGER.info("Console Charset: {}", consoleCharset.name());
        return consoleCharset;
    }
}
