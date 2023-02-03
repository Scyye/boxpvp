package me.xsenny.msbox.reports;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.methods.ShortMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportAPlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            System.out.println("This command can be used only by a player");
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 2){
            player.sendMessage("/report <name> <reason>");
            return true;
        }
        Player who = MsBox.plugin.getServer().getPlayer(args[0]);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++){
            sb.append(args[i]).append(" ");
        }
        ReportsMethods.createReport(player, who, sb.toString());
        ShortMethods.sendSilentlyMessage("Player "+ player.getName()+" reported " +who.getName()+" because of "+sb.toString());
        return true;
    }
}
