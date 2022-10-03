package tomatopuddin.playernametweaks.mixin;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.HashSet;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    private static final HashSet<Character> invalidUserNameChars = new HashSet<>(Lists.charactersOf(" @\u3000\n\t\r\"'\0"));

    @Inject(method = "canPlayerLogin(Ljava/net/SocketAddress;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/network/chat/Component;",
            at = @At("HEAD"), cancellable = true)
    public void canPlayerLogin(SocketAddress p_206258_1_, GameProfile p_206258_2_, CallbackInfoReturnable<Component> cir) {
        for(char c : p_206258_2_.getName().toCharArray()) {
            if(invalidUserNameChars.contains(c)) {
                cir.setReturnValue(new TextComponent("")
                        .withStyle(ChatFormatting.YELLOW)
                        .append("角色名不能包含@、空白字符、英文引号"));
                return;
            }
        }
    }
}
