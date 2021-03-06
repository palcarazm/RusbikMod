package rusbik.helpers;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import rusbik.database.RusbikDatabase;
import rusbik.utils.KrusbibUtils;

import java.sql.SQLException;

public class HomeManager {
    public static void tpHome(ServerPlayerEntity player) throws SQLException {
        HomePos homePos = RusbikDatabase.getHomePos(player.getName().getString());
        // Intentar hacer tp a tu última home.
        if (homePosExists(homePos.X)){
            player.teleport(KrusbibUtils.getWorld(homePos.dim, player), homePos.X, homePos.Y, homePos.Z, player.yaw, player.pitch);
            player.addExperience(0); // xp gets reset when you tp from other dimension and needs to update smh, mojang pls.
        }
        else player.sendMessage(new LiteralText("Aun no has configurado tu casa, usa /setHome"), false);
    }

    public static boolean homePosExists(double X){
        return X != 0.0;
    }
}
