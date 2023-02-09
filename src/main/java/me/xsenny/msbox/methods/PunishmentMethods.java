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
            String uuid = ShortMethods.getUUIDFromName(name);
            if (uuid == null){
                player.sendMessage("Nu a fost online niciodata");
                return;
            }
            if (MsBox.currentTempBans.containsKey(uuid)){
                player.sendMessage("He is already tempbanned");
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
                ShortMethods.setTempBanned(uuid, endOfban, reason, player, ShortMethods.getWhen(System.currentTimeMillis()));
                String message = ShortMethods.getMessage(endOfban);
                if (!silently){
                    plugin.getServer().broadcastMessage(name+" a fost banat de catre "
                            +player.getDisplayName()+" cu motivul "+reason+" pentru "+message);
                }else{
                    ShortMethods.sendSilentlyMessage(name+" a fost banat de catre "
                            +player.getDisplayName()+" cu motivul "+reason+" pentru "+message +" [silent]");
                }
            }else{
                player.sendMessage("ERROR, nu ai introdus timpul corect.");
            }
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op");
            return;
        }
        if (MsBox.currentTempBans.containsKey(target.getUniqueId().toString())){
            player.sendMessage("He is already tempbanned");
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
            ShortMethods.setTempBanned(target.getUniqueId().toString(), endOfban, reason, player, ShortMethods.getWhen(System.currentTimeMillis()));
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
            String uuid = ShortMethods.getUUIDFromName(name);
            if (MsBox.permBans.contains(uuid)){
                player.sendMessage("He is already banned");
                return;
            }
            if (uuid == null){
                player.sendMessage("He wasnt online niciodata");
                return;
            }
            ShortMethods.setPermBanned(uuid, reason, player, ShortMethods.getWhen(System.currentTimeMillis()));
            if (silently){
                ShortMethods.sendSilentlyMessage("Player "+name+" was banned by "+player.getName()+" because "
                        +reason+ " permanently [silent]");
            }else{
                Bukkit.broadcastMessage("Player " + name + " was banned by " + player.getName() + " because "
                        + reason + " permanently");
            }
            return;
        }
        if (MsBox.permBans.contains(target.getUniqueId().toString())){
            player.sendMessage("He is already banned");
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op");
            return;
        }
        ShortMethods.setPermBanned(target.getUniqueId().toString(), reason, player, ShortMethods.getWhen(System.currentTimeMillis()));
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
            String uuid = ShortMethods.getUUIDFromName(name);
            if (uuid == null){
                player.sendMessage(name + " wasn't online niciodata");
                return;
            }
            if (MsBox.permMutes.contains(uuid)){
                player.sendMessage("He is already muted");
                return;
            }
            ShortMethods.setPermMute(uuid, reason, player, ShortMethods.getWhen(System.currentTimeMillis()));
            if (silently){
                ShortMethods.sendSilentlyMessage("Player "+name+" was muted by "+player.getName()+" because "
                        +reason+" permanently. [silent]");
            }else{
                Bukkit.broadcastMessage("Player "+name + " was muted by "+player.getName()+" because "
                        +reason+" permanently.");
            }
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op, you can t mute him");
            return;
        }
        if (MsBox.permMutes.contains(target.getUniqueId().toString())){
            player.sendMessage("He is already muted");
            return;
        }
        ShortMethods.setPermMute(target.getUniqueId().toString(), reason, player, ShortMethods.getWhen(System.currentTimeMillis()));
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
            String uuid = ShortMethods.getUUIDFromName(name);
            if (uuid == null){
                player.sendMessage("He wasnt online niciodata");
                return;
            }
            if (MsBox.currentTempMutes.containsKey(uuid)){
                player.sendMessage("He is already tempmuted");
                return;
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
                ShortMethods.setTempMute(uuid, reason, player, endOfMute, ShortMethods.getWhen(System.currentTimeMillis()));
                String message = ShortMethods.getMessage(endOfMute);
                if (!silently){
                    plugin.getServer().broadcastMessage(name+" a primit mute de la "
                            +player.getDisplayName()+" cu motivul "+reason+" pentru "+message);
                }else{
                    ShortMethods.sendSilentlyMessage(name+" a primit mute de la "
                            +player.getDisplayName()+" cu motivul "+reason+" pentru "+message +" [silent]");
                }
            }else{
                player.sendMessage("ERROR, nu ai introdus timpul corect.");
            }
            return;
        }
        if (target.isOp()){
            player.sendMessage("He is op");
        }
        if (MsBox.currentTempMutes.containsKey(target.getUniqueId().toString())){
            player.sendMessage("He is already tempmuted.");
            return;
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
            ShortMethods.setTempMute(target.getUniqueId().toString(), reason, player, endOfMute, ShortMethods.getWhen(System.currentTimeMillis()));
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

    public static void kickAPlayer(String name, Player who, String reason, Boolean silently){
        Player target = plugin.getServer().getPlayer(name);
        if (target == null || !target.isOnline()){
            who.sendMessage("PlAYER ISNT ONLINE");
            return;
        }
        if (target.isOp()){
            who.sendMessage("TARGET IS OP");
            return;
        }
        target.kickPlayer("Kicked by "+ who.getName()+" because: "+reason);
        ShortMethods.setKick(target.getUniqueId().toString(), reason, who, ShortMethods.getWhen(System.currentTimeMillis()));
        if (silently){
            ShortMethods.sendSilentlyMessage("Player "+name+" was kicked beause of " +reason + " by "+ who.getName() +" [Silent]");
        }else{
            Bukkit.broadcastMessage("Player "+name+" was kicked because of " +reason + " by "+who.getName());
        }
    }



}
