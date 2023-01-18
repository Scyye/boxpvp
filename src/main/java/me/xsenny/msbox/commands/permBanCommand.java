package me.xsenny.msbox.commands;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.methods.PunishmentMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.crypto.MacSpi;

public class permBanCommand implements CommandExecutor {

    public static MsBox plugin = MsBox.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.isOp()){
                if (args.length == 0){
                    p.sendMessage("/ban <name> {reason} {-s}");
                    return true;
                }
                if (args.length == 1){
                    String nameOfPlayer = args[0];
                    PunishmentMethods.permBan(nameOfPlayer, p, "No reason", false);
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
                }PunishmentMethods.permBan(args[0], p, sb.toString(), silent);
            }
        }

        return true;
    }
}
