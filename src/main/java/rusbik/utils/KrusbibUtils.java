package rusbik.utils;

import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class KrusbibUtils {
    public static String getDimensionWithColor(ServerPlayerEntity player) {
        Identifier dimensionType = player.world.getRegistryKey().getValue();
        String msg = player.world.getDimension().toString();
        if (dimensionType.equals(World.OVERWORLD.getValue())) msg = Formatting.GREEN + "[Overworld]";
        else if (dimensionType.equals(World.NETHER.getValue())) msg = Formatting.RED + "[Nether]";
        else if (dimensionType.equals(World.END.getValue())) msg = Formatting.DARK_PURPLE + "[End]";
        return msg;
    }

    public static String getDimensionWithColor(World world) {
        Identifier dimensionType = world.getRegistryKey().getValue();
        String msg = world.getDimension().toString();
        if (dimensionType.equals(World.OVERWORLD.getValue())) msg = Formatting.GREEN + "[Overworld]";
        else if (dimensionType.equals(World.NETHER.getValue())) msg = Formatting.RED + "[Nether]";
        else if (dimensionType.equals(World.END.getValue())) msg = Formatting.DARK_PURPLE + "[End]";
        return msg;
    }

    public static String getDimensionWithColor(Identifier dimensionType) {
        String msg = dimensionType.toString();
        if (dimensionType.equals(World.OVERWORLD.getValue())) msg = Formatting.GREEN + "[Overworld]";
        else if (dimensionType.equals(World.NETHER.getValue())) msg = Formatting.RED + "[Nether]";
        else if (dimensionType.equals(World.END.getValue())) msg = Formatting.DARK_PURPLE + "[End]";
        return msg;
    }

    public static String getPlayerWithColor(ServerPlayerEntity player) {
        return Formatting.YELLOW + player.getName().asString();
    }

    public static String getDim(World world){
        Identifier dimensionType = world.getRegistryKey().getValue();
        String msg = world.getDimension().toString();
        if (dimensionType.equals(World.OVERWORLD.getValue())) msg = "Overworld";
        else if (dimensionType.equals(World.NETHER.getValue())) msg = "Nether";
        else if (dimensionType.equals(World.END.getValue())) msg = "End";
        return msg;
    }

    public static String formatCoords(double x, double y, double z){
        return Formatting.WHITE + String.format("[x: %d, y: %d, z: %d]", (int) x, (int) y, (int) z);
    }

    // Todos los jugadores conectados.
    public static Collection<String> getPlayers(ServerCommandSource source) {
        Set<String> players = Sets.newLinkedHashSet();
        players.addAll(source.getPlayerNames());
        return players;
    }

    public static ServerWorld getWorld(String dim, ServerPlayerEntity player){
        ServerWorld dimension;
        switch (dim){
            case "Overworld":
                dimension = Objects.requireNonNull(player.getServer()).getWorld(World.OVERWORLD);
                break;
            case "Nether":
                dimension = Objects.requireNonNull(player.getServer()).getWorld(World.NETHER);
                break;
            default:
                dimension = Objects.requireNonNull(player.getServer()).getWorld(World.END);
                break;
        }
        return dimension;
    }

    // Fecha para el logger de bloques.
    public static String getDate(){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(format);
    }

    // Bloques que se deberían registrar en la base de datos al interactuar con ellos.
    public static boolean shouldRegisterBlock(Block block, ServerPlayerEntity player){
        return !player.isInSneakingPose() && (block instanceof BlockWithEntity
                || block instanceof DoorBlock || block instanceof FenceGateBlock
                || block instanceof TrapdoorBlock || block instanceof LeverBlock
                || block instanceof AbstractButtonBlock || block instanceof NoteBlock);
    }

    // Acciones que se deberían registrar en la base de datos.
    public static boolean shouldRegisterItem(ServerPlayerEntity player, ItemStack itemStack){
        return !player.isInSneakingPose() && (itemStack.getItem() == Items.LAVA_BUCKET
                || itemStack.getItem() == Items.WATER_BUCKET);
    }

    // Formateo para el comando blockInfo.
    public static String buildLine(ResultSet rs) throws SQLException {
        String line;
        switch (rs.getInt("action")){
            case 0:
                line = String.format("[%s] <%s> ha roto '%s'", rs.getString("date"), Formatting.WHITE + rs.getString("name"), Formatting.DARK_PURPLE + rs.getString("block").split("\\.")[rs.getString("block").split("\\.").length - 1] + Formatting.WHITE);
                break;
            case 1:
                line = String.format("[%s] <%s> ha puesto '%s'", rs.getString("date"), Formatting.WHITE + rs.getString("name"), Formatting.DARK_PURPLE + rs.getString("block").split("\\.")[rs.getString("block").split("\\.").length - 1] + Formatting.WHITE);
                break;
            default:
                line = String.format("[%s] <%s> ha usado '%s'", rs.getString("date"), Formatting.WHITE + rs.getString("name"), Formatting.DARK_PURPLE + rs.getString("block").split("\\.")[rs.getString("block").split("\\.").length - 1] + Formatting.WHITE);
                break;
        }
        return line;
    }
}
