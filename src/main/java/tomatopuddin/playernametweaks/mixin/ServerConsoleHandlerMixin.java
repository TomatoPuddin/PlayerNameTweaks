package tomatopuddin.playernametweaks.mixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Mixin(targets = "net.minecraft.server.dedicated.DedicatedServer$1")
public class ServerConsoleHandlerMixin {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static BufferedReader consoleReader;

    static {
        Charset charset = Charset.defaultCharset();
        consoleReader = new BufferedReader(new InputStreamReader(System.in, charset));
        LOGGER.info("Console Charset: " + charset.name());
    }

    @Redirect(method = "run", remap = false, at = @At(value = "INVOKE",
            target = "Ljava/io/BufferedReader;readLine()Ljava/lang/String;", ordinal = 0))
    public String readCommandLine(BufferedReader instance) throws IOException {
        return consoleReader.readLine();
    }
}
