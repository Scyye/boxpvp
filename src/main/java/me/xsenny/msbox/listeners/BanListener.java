package me.xsenny.msbox.listeners;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.database.Database;
import me.xsenny.msbox.methods.ShortMethods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BanListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerLoginEvent e){
        if (ShortMethods.isPlayerBanned(e.getPlayer())){
            if (!MsBox.permBans.contains(e.getPlayer().getUniqueId().toString())){
                long endOfBan = MsBox.currentTempBans.get(e.getPlayer().getUniqueId().toString());
                long now = System.currentTimeMillis();
                long diff = endOfBan - now;
                if (diff <= 0){
                    MsBox.currentTempMutes.remove(e.getPlayer().getUniqueId());
                }else{
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are banned for "+ShortMethods.getMessage(endOfBan) + " because "+reasonOfBan(e.getPlayer().getUniqueId().toString())+ "by: "+getByWho(e.getPlayer().getUniqueId().toString()));
                }
            }else{
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are banned permanently because "+reasonOfBan(e.getPlayer().getUniqueId().toString())+" by: "+getByWho(e.getPlayer().getUniqueId().toString()));
            }
        }
    }

    public static String getByWho(String uuid){
        ResultSet rs = Database.onQuery("SELECT * FROM BANS WHERE uuid = '" + uuid+"'");
        String name = "";
        if (rs != null){
            while(true){
                try {
                    if (!rs.next()) break;
                    name = rs.getString("by_who");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        return name;
    }

    public static String reasonOfBan(String uuid){
        ResultSet rs = Database.onQuery("SELECT * FROM BANS WHERE uuid = '" + uuid+"'");
        String reason = "";
        if (rs != null){
            while(true){
                try {
                    if (!rs.next()) break;
                    reason = rs.getString("reason");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        return reason;
    }

}
