package me.xsenny.msbox.listeners;

import me.xsenny.msbox.MsBox;
import me.xsenny.msbox.database.Database;
import me.xsenny.msbox.methods.ShortMethods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TempBanListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerLoginEvent e){
        if (ShortMethods.isPlayerBanned(e.getPlayer())){
            long endOfBan = MsBox.currentTempBans.get(e.getPlayer().getUniqueId().toString());
            long now = System.currentTimeMillis();
            long diff = endOfBan - now;
            if (diff <= 0){
                MsBox.currentTempMutes.remove(e.getPlayer().getUniqueId());
            }else{
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are banned for "+ShortMethods.getMessage(endOfBan) + " because "+reasonOfBan(e.getPlayer().getUniqueId().toString()));
            }
        }
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
