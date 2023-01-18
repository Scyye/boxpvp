package me.xsenny.msbox.commands;

import me.xsenny.msbox.methods.PunishmentMethods;
import me.xsenny.msbox.utils.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class permMuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.isOp() || p.hasPermission(Permission.MUTE.getPermission())){
                if (args.length == 0){
                    p.sendMessage("/mute <name> {reason} {-s}");
                    return true;
                }
                if (args.length == 1){
                    String nameOfPlayer = args[0];
                    PunishmentMethods.permMute(nameOfPlayer, p, "No reason", false);
                    return true;
                }
                Boolean silent = false;
                StringBuffer sb = new StringBuffer();
                for (String strings : args){
                    if (strings.equals("-s")){
                        silent = true;
                    }else{
                        sb.append(strings).append(" ");
                    }
                }PunishmentMethods.permMute(args[0], p, sb.toString(), silent);
            }
        }

        return true;
    }
}
