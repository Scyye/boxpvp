package me.xsenny.msbox.methods;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShortMethods {

    public static void setTempBanned(String uuid, Long endofban, String reason){
        MsBox.currentTempBans.put(uuid, endofban);
        Database.onUpdate("INSERT INTO BANS("+uuid+", "+false+", "+endofban+", "+reason+")");
    }

    public static void sendSilentlyMessage(String message){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.isOp()){
                p.sendMessage(message);
            }
        }
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
