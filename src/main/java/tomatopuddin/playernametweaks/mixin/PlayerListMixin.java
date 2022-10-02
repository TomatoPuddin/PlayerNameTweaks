package tomatopuddin.playernametweaks.mixin;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.integrated.IntegratedPlayerList;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.HashSet;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    private static final HashSet<Character> invalidUserNameChars = new HashSet<>(Lists.charactersOf(" @\u3000\n\t\r\"\'\0"));

    @Inject(method = "canPlayerLogin(Ljava/net/SocketAddress;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/util/text/ITextComponent;",
            at = @At("HEAD"), cancellable = true)
    public void canPlayerLogin(SocketAddress p_206258_1_, GameProfile p_206258_2_, CallbackInfoReturnable<ITextComponent> cir) {
        for(char c : p_206258_2_.getName().toCharArray()) {
            if(invalidUserNameChars.contains(c)) {
                cir.setReturnValue(new StringTextComponent("")
                        .mergeStyle(TextFormatting.YELLOW)
                        .appendString("角色名不能包含@、空白字符、英文引号"));
                return;
            }
        }
    }
}
