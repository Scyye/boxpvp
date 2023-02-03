package me.xsenny.msbox;

import me.kodysimpson.simpapi.menu.MenuManager;
import me.xsenny.msbox.commands.*;
import me.xsenny.msbox.database.Database;
import me.xsenny.msbox.database.Files;
import me.xsenny.msbox.listeners.BanListener;
import me.xsenny.msbox.listeners.MuteListener;
import me.xsenny.msbox.reports.Report;
import me.xsenny.msbox.reports.ReportAPlayerCommand;
import me.xsenny.msbox.reports.ReportsCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class MsBox extends JavaPlugin {

    public static HashMap<String, Long> currentTempBans = new HashMap<>();
    public static HashMap<String, Long> currentTempMutes = new HashMap<>();
    public static String banPath = "plugins/MsBox"+File.separator + "BanList.dat";
    public static String mutePath = "plugins/MsBox"+File.separator+"MuteList.dat";
    public static final String reportsPath = "plugins/MsBox" + File.separator+"ReportsList.dat";
    public static ArrayList<String> permBans = new ArrayList<>();
    public static ArrayList<String> permMutes = new ArrayList<>();
    public static ArrayList<Report> reports = new ArrayList<>();
    public static MsBox plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Files.loadAll();
        Database.connect();
        Database.onUpdate("CREATE TABLE IF NOT EXISTS BANS (uuid varchar(40), is_perm integer, time integer, reason string, unbanned integer, by_who varchar(40), cand string)");
        Database.onUpdate("CREATE TABLE IF NOT EXISTS MUTES (uuid varchar(40), is_perm integer, time integer, reason string, unbanned integer, by_who varchar(40), cand string)");
        Database.onUpdate("CREATE TABLE IF NOT EXISTS KICKS (uuid varchar(40), reason string, by_who varchar(40), cand string)");

        MenuManager.setup(getServer(), this);

        getCommand("tempban").setExecutor(new tempBanCommands());
        getCommand("ban").setExecutor(new permBanCommand());
        getCommand("mute").setExecutor(new permMuteCommand());
        getCommand("tempmute").setExecutor(new tempMuteCommand());
        getCommand("kick").setExecutor(new kickPlayer());
        getCommand("report").setExecutor(new ReportAPlayerCommand());
        getCommand("reports").setExecutor(new ReportsCommand());

        getServer().getPluginManager().registerEvents(new MuteListener(), this);
        getServer().getPluginManager().registerEvents(new BanListener(), this);

    }

    @Override
    public void onDisable() {
        Files.saveTempBans();
        Files.saveTempMutes();
        Files.savePermBans();
        Files.savePermMutes();
        Files.saveReports();
    }



}
