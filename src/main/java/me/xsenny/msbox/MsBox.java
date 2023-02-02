package me.xsenny.msbox;

import me.xsenny.msbox.commands.*;
import me.xsenny.msbox.database.Database;
import me.xsenny.msbox.listeners.BanListener;
import me.xsenny.msbox.listeners.MuteListener;
import me.xsenny.msbox.reports.Report;
import me.xsenny.msbox.reports.ReportAPlayerCommand;
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

        File file = new File(banPath);
        new File("plugins/MsBox").mkdir();
        if (file.exists()){
            currentTempBans = loadTempBans();
        }
        if (currentTempBans == null){
            currentTempBans = new HashMap<>();
        }
        File file1 = new File(mutePath);
        if (file1.exists()){
            currentTempMutes = loadTempMutes();
        }
        if (currentTempMutes == null){
            currentTempMutes = new HashMap<>();
        }
        File file2 = new File("plugins/MsBox"+File.separator + "PermBanList.dat");
        if (file2.exists()){
            permBans = loadPermBans();
        }
        if (permBans == null){
            permBans = new ArrayList<>();
        }

        File file3 = new File("plugins/MsBox"+File.separator + "PermMuteList.dat");
        if (file3.exists()){
            permMutes = loadPermMutes();
        }
        if (permMutes == null){
            permMutes = new ArrayList<>();
        }
        File file4 = new File(reportsPath);
        if (file4.exists()){
            reports = loadReports();
        }
        if (reports == null){
            reports =new ArrayList<>();
        }
        Database.connect();
        Database.onUpdate("CREATE TABLE IF NOT EXISTS BANS (uuid varchar(40), is_perm integer, time integer, reason string, unbanned integer, by_who varchar(40), cand string)");
        Database.onUpdate("CREATE TABLE IF NOT EXISTS MUTES (uuid varchar(40), is_perm integer, time integer, reason string, unbanned integer, by_who varchar(40), cand string)");
        System.out.println("eroare aici");
        Database.onUpdate("CREATE TABLE IF NOT EXISTS KICKS (uuid varchar(40), reason string, by_who varchar(40), cand string)");


        getCommand("tempban").setExecutor(new tempBanCommands());
        getCommand("ban").setExecutor(new permBanCommand());
        getCommand("mute").setExecutor(new permMuteCommand());
        getCommand("tempmute").setExecutor(new tempMuteCommand());
        getCommand("kick").setExecutor(new kickPlayer());
        getCommand("report").setExecutor(new ReportAPlayerCommand());

        getServer().getPluginManager().registerEvents(new MuteListener(), this);
        getServer().getPluginManager().registerEvents(new BanListener(), this);

    }

    @Override
    public void onDisable() {
        saveTempBans();
        saveTempMutes();
        savePermBans();
        savePermMutes();
        saveReports();
    }

    public static ArrayList<String> loadPermBans(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("plugins/MsBox"+File.separator + "PermBanList.dat"));
            Object result = ois.readObject();
            ois.close();
            return (ArrayList<String>)  result;
        }catch (Exception e){
            return null;
        }
    }

    public static ArrayList<String> loadPermMutes(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("plugins/MsBox"+File.separator + "PermMuteList.dat"));
            Object result = ois.readObject();
            ois.close();
            return (ArrayList<String>)  result;
        }catch (Exception e){
            return null;
        }
    }

    public static void savePermMutes(){
        File file = new File("plugins/MsBox"+File.separator + "PermMuteList.dat");
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("plugins/MsBox"+File.separator + "PermMuteList.dat"));
            oos.writeObject(permMutes);
            oos.flush();
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void savePermBans(){
        File file = new File("plugins/MsBox"+File.separator + "PermBanList.dat");
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("plugins/MsBox"+File.separator + "PermBanList.dat"));
            oos.writeObject(permBans);
            oos.flush();
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveTempBans(){
        File file = new File(banPath);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(banPath));
            oos.writeObject(currentTempBans);
            oos.flush();
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<String, Long> loadTempBans(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(banPath));
            Object result = ois.readObject();
            ois.close();
            return (HashMap<String, Long>) result;
        }catch (Exception e){
            return null;
        }
    }

    public static void saveTempMutes(){
        File file = new File(mutePath);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mutePath));
            oos.writeObject(currentTempMutes);
            oos.flush();
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static HashMap<String, Long> loadTempMutes(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mutePath));
            Object result = ois.readObject();
            ois.close();
            return (HashMap<String, Long>) result;
        }catch (Exception e){
            return null;
        }
    }

    public static ArrayList<Report> loadReports(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(reportsPath));
            Object result = ois.readObject();
            ois.close();
            return (ArrayList<Report>)  result;
        }catch (Exception e){
            return null;
        }
    }

    public static void saveReports(){
        File file = new File(reportsPath);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reportsPath));
            oos.writeObject(reports);
            oos.flush();
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
