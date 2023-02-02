package me.xsenny.msbox.methods;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShortMethods {

    public static void setTempBanned(String uuid, Long endofban, String reason, Player who, String where){
        MsBox.currentTempBans.put(uuid, endofban);
        Database.onUpdate("INSERT INTO BANS VALUES(\""+uuid+"\", \"0\", "+endofban+", \""+reason+"\", 0, "+who
                .getName()+", "+where +")");
    }



    public static void sendSilentlyMessage(String message){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.isOp()){
                p.sendMessage(message);
            }
        }
    }

    public static void setPermBanned(String uuid, String reason, Player who, String where){
        MsBox.permBans.add(uuid);
        Database.onUpdate("INSERT INTO BANS VALUES(\""+uuid+"\", 1, NULL, \""+reason+"\", 0, "+who.getName()+", "+where+")");
    }

    public static void setPermMute(String uuid, String reason, Player who, String where){
        MsBox.permMutes.add(uuid);
        Database.onUpdate("INSERT INTO MUTES VALUES(\""+uuid+"\", 1, NULL, \""+reason+"\", 0, "+who.getName()+", " + where +")");
    }

    public static void setTempMute(String uuid, String reason, Player who, Long endOfMute, String where){
        MsBox.currentTempMutes.put(uuid, endOfMute);
        Database.onUpdate("INSERT INTO MUTES VALUES(\""+uuid+"\", \"0\", "+endOfMute+", \""+reason+"\", 0, "+who
                .getName()+", "+where+")");
    }

    public static void setKick(String uuid, String reason, Player who, String where){
        Database.onUpdate("INSERT INTO KICKS VALUES(\""+uuid+"\", \""+reason+"\", "+who.getName()+", "+where+")");
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


    public static String getWhen(long millis){
        Date date = new Date(millis);
        DateFormat df = new SimpleDateFormat("yy:MM:dd HH:mm");
        return df.format(date);
    }

}
