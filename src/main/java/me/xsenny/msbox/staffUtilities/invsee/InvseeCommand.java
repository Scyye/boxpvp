package me.xsenny.msbox.staffUtilities.invsee;

import me.xsenny.msbox.staffUtilities.Staff;
import me.xsenny.msbox.utils.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)){
            System.out.println("At the moment, we dont support commands to console");
            return true;
        }

        Player p = (Player) sender;
        if (!(p.isOp() || p.hasPermission(Permission.ALL.getPermission()) || p.hasPermission(Permission.STAFF.getPermission())) || p.hasPermission(Permission.INVSEE.getPermission())){
            if (args.length != 1){
                p.sendMessage("/invsee <player>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()){
                p.sendMessage("He isnt online");
                return true;
            }
            p.openInventory(InvseeMenu.getItems(target));
            Staff.getStaff(p).setInvseePlayer(target);
            p.sendMessage("Opened");
        }

        return true;
    }
}
