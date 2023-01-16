package me.xsenny.msbox.methods;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.utils.PunishUnit;
import org.bukkit.entity.Player;

public class PunishmentMethods {

    public static MsBox plugin = MsBox.plugin;

    public static void tempBan(String name, String amount, String unit, Player player, String reason, Boolean silently){
        Player target = plugin.getServer().getPlayer(name);
        if (target == null || !target.isOnline()){
            player.sendMessage("PLAYER ISNT ONLINE");
            return;
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
            ShortMethods.setTempBanned(target.getUniqueId().toString(), endOfban, reason);
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



}
