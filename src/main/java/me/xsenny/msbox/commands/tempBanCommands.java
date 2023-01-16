package me.xsenny.msbox.commands;

import me.xsenny.msbox.methods.PunishmentMethods;
import me.xsenny.msbox.methods.ShortMethods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tempBanCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 3){
            String playerName = args[0];
            String amount = args[1];
            String unit = args[2];
            PunishmentMethods.tempBan(playerName, amount, unit, (Player) sender, "No reason", false);
        }else if (args.length == 4){
            String playerName = args[0];
            String amount = args[1];
            String unit = args[2];
            if (args[3].equals("-s")){
                Boolean silently = true;
                PunishmentMethods.tempBan(playerName, amount, unit, (Player) sender, "No reason", silently);
            }else{
                String reason = args[3];
                PunishmentMethods.tempBan(playerName, amount, unit, (Player) sender, reason, false);
            }
        }else if (args.length > 4){
            
        }

        return true;
    }
}
