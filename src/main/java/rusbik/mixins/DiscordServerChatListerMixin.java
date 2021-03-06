package rusbik.mixins;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rusbik.discord.utils.DiscordListener;

@Mixin(ServerPlayNetworkHandler.class)
// Mixin para redirigir el chat de minecraft a discord.
public class DiscordServerChatListerMixin {
    @Shadow
    public ServerPlayerEntity player;
    @Inject(method = "onGameMessage", at = @At("RETURN"))
    public void chatMessage(ChatMessageC2SPacket packet, CallbackInfo ci){
        if (!packet.getChatMessage().startsWith("/")) DiscordListener.sendMessage("`<" + player.getName().getString() + ">` " + packet.getChatMessage());
        else System.out.printf("<%s> %s%n", player.getName().getString(), packet.getChatMessage());
    }

    // Cuando un jugador se va.
    @Inject(method = "onDisconnected", at = @At("RETURN"))
    private void onPlayerLeft(Text reason, CallbackInfo ci){
        if (DiscordListener.chatBridge) DiscordListener.sendMessage(":arrow_left: **" + player.getName().getString().replace("_", "\\_") + " left the game!**");
    }
}
