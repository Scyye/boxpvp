package me.xsenny.msbox.methods;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.utils.PunishUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishmentMethods {

    public static MsBox plugin = MsBox.plugin;

    public static void tempBan(String name, String amount, String unit, Player player, String reason, Boolean silently){
        Player target = plugin.getServer().getPlayer(name);
        if (target == null || !target.isOnline()){
            player.sendMessage("PLAYER ISNT ONLINE");
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op");
        }
        Integer integer;
        try{
            integer = Integer.parseInt(amount);
        }catch (NumberFormatException e){
            player.sendMessage("ASTA NU E NUMAR");
            return;
        }
        long endOfban = System.currentTimeMillis() + PunishUnit.getTicks(unit, integer);
        long now = System.currentTimeMillis();
        long diff = endOfban - now;
        if (diff > 0){
            ShortMethods.setTempBanned(target.getUniqueId().toString(), endOfban, reason, player);
            String message = ShortMethods.getMessage(endOfban);
            if (!silently){
                plugin.getServer().broadcastMessage(target.getDisplayName()+" a fost banat de catre "
                +player.getDisplayName()+" cu motivul "+reason+" pentru "+message);
            }else{
                ShortMethods.sendSilentlyMessage(target.getDisplayName()+" a fost banat de catre "
                        +player.getDisplayName()+" cu motivul "+reason+" pentru "+message +" [silent]");
            }
            target.kickPlayer("Esti banat pentru "+message + " because of "+reason);
        }else{
            player.sendMessage("ERROR, nu ai introdus timpul corect.");
        }
    }

    public static void permBan(String name, Player player, String reason, Boolean silently){
        Player target = plugin.getServer().getPlayer(name);
        if (target == null || !target.isOnline()){
            player.sendMessage("Player isnt online");
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op");
            return;
        }
        ShortMethods.setPermBanned(target.getUniqueId().toString(), reason, player);
        if (silently){
            ShortMethods.sendSilentlyMessage("Player "+target.getName()+" was banned by "+player.getName()+" because "
            +reason+ " permanently [silent]");
        }else{
            Bukkit.broadcastMessage("Player " + target.getName() + " was banned by " + player.getName() + " because "
                    + reason + " permanently");
        }
        target.kickPlayer("Esti banat permanent " + " because of "+reason);
    }

    public static void permMute(String name, Player player, String reason, Boolean silently){
        Player target = plugin.getServer().getPlayer(name);
        if (target == null || !target.isOnline()){
            player.sendMessage("Player isn't online");
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op, you can t mute him");
            return;
        }
        ShortMethods.setPermMute(target.getUniqueId().toString(), reason, player);
        if (silently){
            ShortMethods.sendSilentlyMessage("Player "+target.getName()+" was muted by "+player.getName()+" because "
            +reason+" permanently. [silent]");
        }else{
            Bukkit.broadcastMessage("Player "+target.getName()+" was muted by "+player.getName()+" because "
                    +reason+" permanently.");
        }
        target.sendMessage("You were muted permanently because of "+reason);
    }

    public static void tempMute(String name, String amount, String unit, Player player, String reason, Boolean silently){
        Player target = plugin.getServer().getPlayer(name);
        if (target == null || !target.isOnline()){
            player.sendMessage("PLAYER ISNT ONLINE");
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op");
        }
        Integer integer;
        try{
            integer = Integer.parseInt(amount);
        }catch (NumberFormatException e){
            player.sendMessage("ASTA NU E NUMAR");
            return;
        }
        long endOfMute = System.currentTimeMillis() + PunishUnit.getTicks(unit, integer);
        long now = System.currentTimeMillis();
        long diff = endOfMute - now;
        if (diff > 0){
            ShortMethods.setTempBanned(target.getUniqueId().toString(), endOfMute, reason, player);
            String message = ShortMethods.getMessage(endOfMute);
            if (!silently){
                plugin.getServer().broadcastMessage(target.getDisplayName()+" a primit mute de la "
                        +player.getDisplayName()+" cu motivul "+reason+" pentru "+message);
            }else{
                ShortMethods.sendSilentlyMessage(target.getDisplayName()+" a primit mute de la "
                        +player.getDisplayName()+" cu motivul "+reason+" pentru "+message +" [silent]");
            }
            target.sendMessage("Ai primit mute de la "+player.getName()+" pentru "+message+", deoarece "+reason);
        }else{
            player.sendMessage("ERROR, nu ai introdus timpul corect.");
        }
    }

}
