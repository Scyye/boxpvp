package me.xsenny.msbox.commands;

import me.xsenny.msbox.methods.ShortMethods;
import me.xsenny.msbox.utils.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnmuteUnbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.equals("unban")){
            if (!(sender instanceof Player)){
                sender.sendMessage("At the moment we dont support console commands");
                return true;
            }
            Player p = (Player) sender;
            if (args.length != 1){
                p.sendMessage("/unban <player>");
                return true;
            }
            String uuid = ShortMethods.getUUIDFromName(args[0]);
            if (!ShortMethods.isPlayerBanned(uuid)){
                p.sendMessage(args[0] + " is not banned");
                return true;
            }
            if (!(p.hasPermission(Permission.ALL.getPermission()) || p.hasPermission(Permission.UNBAN.getPermission()) || p.hasPermission(Permission.STAFF.getPermission()))){
                p.sendMessage("You do not have permission to do this.");
                return true;
            }
            ShortMethods.unbanAPlayer(uuid, p);
            Bukkit.broadcastMessage("The player " + args[0] + " was unbanned by "+p.getName());
            return true;
        }
        if (command.equals("unmute")){
            if (!(sender instanceof Player)){
                sender.sendMessage("At the moment we dont support console commands");
                return true;
            }
            Player p = (Player) sender;
            if (args.length != 1){
                p.sendMessage("/unmute <player>");
                return true;
            }
            String uuid = ShortMethods.getUUIDFromName(args[0]);
            if (!ShortMethods.isPlayerMuted(uuid)){
                p.sendMessage(args[0] + " is not muted");
                return true;
            }
            if (!(p.hasPermission(Permission.ALL.getPermission()) || p.hasPermission(Permission.UNMUTE.getPermission()) || p.hasPermission(Permission.STAFF.getPermission()))){
                p.sendMessage("You do not have permission to do this.");
                return true;
            }
            ShortMethods.unmuteAPlayer(uuid, p);
            Bukkit.broadcastMessage("The player " + args[0] + " was unbanned by "+p.getName());
            return true;
        }

        return true;
    }
}
