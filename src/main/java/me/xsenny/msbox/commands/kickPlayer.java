package me.xsenny.msbox.commands;

import me.xsenny.msbox.methods.PunishmentMethods;
import me.xsenny.msbox.utils.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class kickPlayer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.isOp() || p.hasPermission(Permission.KICK.getPermission())){
                if (args.length == 0){
                    p.sendMessage("/kick <player> {reason}");
                    return true;
                }
                if (args.length == 1){
                    PunishmentMethods.kickAPlayer(args[0], p, "no reason", false);
                    return true;
                }
                String playerName = args[0];
                boolean silently = false;
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++){
                    if (!args[i].equals("-s")){
                        sb.append(args[i]).append(" ");
                    }else{
                        silently = true;
                    }
                }
                PunishmentMethods.kickAPlayer(playerName, p, sb.toString(), silently);
            }else{
                sender.sendMessage("This command is only for staff.");
            }
        }

        return true;
    }
}
