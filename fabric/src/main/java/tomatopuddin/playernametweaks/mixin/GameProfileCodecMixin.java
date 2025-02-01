package tomatopuddin.playernametweaks.mixin;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.Utf8String;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {
        "net.minecraft.class_9135$20",
        "net.minecraft.class_9135$22",
})
public class GameProfileCodecMixin {
    @Unique
    private static final int MAX_LENGTH = 32767;

    @Redirect(method = "*(Lio/netty/buffer/ByteBuf;)Lcom/mojang/authlib/GameProfile;", require = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Utf8String;read(Lio/netty/buffer/ByteBuf;I)Ljava/lang/String;"))
    private String readUtfName(ByteBuf buf, int i) {
        return Utf8String.read(buf, MAX_LENGTH);
    }

    @Redirect(method = "*(Lio/netty/buffer/ByteBuf;Lcom/mojang/authlib/GameProfile;)V", require = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Utf8String;write(Lio/netty/buffer/ByteBuf;Ljava/lang/CharSequence;I)V"))
    private void writeUtfName(ByteBuf buf, CharSequence name, int i) {
        Utf8String.write(buf, name, MAX_LENGTH);
    }
}
