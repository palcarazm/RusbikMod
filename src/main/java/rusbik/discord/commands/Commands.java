package rusbik.discord.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.minecraft.server.MinecraftServer;
import rusbik.discord.utils.DiscordPermission;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    private String cBody;
    private DiscordPermission permission;

    Commands() {}

    public void execute(MessageReceivedEvent event, MinecraftServer server) {}

    public void setCBody(String cBody) {
        this.cBody = cBody;
    }

    public String getCBody() {
        return cBody;
    }

    public void setPermission(DiscordPermission permission) {
        this.permission = permission;
    }

    public DiscordPermission getPermission() {
        return permission;
    }
}
