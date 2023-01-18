package me.xsenny.msbox.commands;

import me.xsenny.msbox.methods.PunishmentMethods;
import me.xsenny.msbox.utils.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tempMuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.isOp() || p.hasPermission(Permission.TEMPMUTE.getPermission())){
                if (args.length == 3){
                    String playerName = args[0];
                    String amount = args[1];
                    String unit = args[2];
                    PunishmentMethods.tempMute(playerName, amount, unit, (Player) sender, "No reason", false);
                }else if (args.length == 4){
                    String playerName = args[0];
                    String amount = args[1];
                    String unit = args[2];
                    if (args[3].equals("-s")){
                        Boolean silently = true;
                        PunishmentMethods.tempMute(playerName, amount, unit, (Player) sender, "No reason", silently);
                    }else{
                        String reason = args[3];
                        PunishmentMethods.tempMute(playerName, amount, unit, (Player) sender, reason, false);
                    }
                }else if (args.length > 4){
                    String playerName = args[0];
                    String amount = args[1];
                    String unit = args[2];
                    Boolean silently = false;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 3; i < args.length; i++){
                        if (!args[i].equals("-s")){
                            sb.append(args[i]).append(" ");
                        }else{
                            silently = true;
                        }
                    }
                    PunishmentMethods.tempMute(playerName, amount, unit, (Player) sender, sb.toString(), silently);
                }else{
                    sender.sendMessage("/tempmmute <player> <amount> <min, hours> <reason> {-s (silently)}");
                }
            }else{
                sender.sendMessage("This command is only for staff.");
            }
        }

        return true;
    }
}
