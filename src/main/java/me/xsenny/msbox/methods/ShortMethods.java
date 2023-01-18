package me.xsenny.msbox.methods;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShortMethods {

    public static void setTempBanned(String uuid, Long endofban, String reason, Player who){
        MsBox.currentTempBans.put(uuid, endofban);
        Database.onUpdate("INSERT INTO BANS VALUES(\""+uuid+"\", \"0\", "+endofban+", \""+reason+"\", 0, "+who
                .getName()+")");
    }



    public static void sendSilentlyMessage(String message){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.isOp()){
                p.sendMessage(message);
            }
        }
    }

    public static void setPermBanned(String uuid, String reason, Player who){
        MsBox.permBans.add(uuid);
        Database.onUpdate("INSERT INTO BANS VALUES(\""+uuid+"\", 1, NULL, \""+reason+"\", 0, "+who.getName()+")");
    }

    public static void setPermMute(String uuid, String reason, Player who){
        MsBox.permMutes.add(uuid);
        Database.onUpdate("INSERT INTO MUTES VALUES(\""+uuid+"\", 1, NULL, \""+reason+"\", 0, "+who.getName()+")");
    }

    public static void setTempMute(String uuid, String reason, Player who, Long endOfMute){
        MsBox.currentTempMutes.put(uuid, endOfMute);
        Database.onUpdate("INSERT INTO MUTES VALUES(\""+uuid+"\", \"0\", "+endOfMute+", \""+reason+"\", 0, "+who
                .getName()+")");
    }

    public static boolean isPlayerMuted(Player player){
        if (MsBox.currentTempMutes.containsKey(player.getUniqueId().toString())){
            if (MsBox.currentTempMutes.get(player.getUniqueId().toString()) != null){
                return true;
            }
        }if (MsBox.permMutes.contains(player.getUniqueId().toString())){
            return true;
        }
        return false;
    }

    public static boolean isPlayerBanned(Player player){
        if (MsBox.currentTempBans.containsKey(player.getUniqueId().toString())){
            if (MsBox.currentTempBans.get(player.getUniqueId().toString()) != null){
                return true;
            }
        }
        if (MsBox.permBans.contains(player.getUniqueId().toString())){
            return true;
        }
        return false;
    }

    public static String getMessage(long endOfBan){
        String message = "";

        long now = System.currentTimeMillis();
        long diff = endOfBan - now;
        int seconds = (int) (diff / 1000);

        if(seconds >= 60*60*24){
            int days = seconds / (60*60*24);
            seconds = seconds % (60*60*24);

            message += days + " Day(s) ";
        }
        if(seconds >= 60*60){
            int hours = seconds / (60*60);
            seconds = seconds % (60*60);

            message += hours + " Hour(s) ";
        }
        if(seconds >= 60){
            int min = seconds / 60;
            seconds = seconds % 60;

            message += min + " Minute(s) ";
        }
        if(seconds >= 0){
            message += seconds + " Second(s) ";
        }

        return message;
    }

}
