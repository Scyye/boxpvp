package me.xsenny.msbox.utils;

public enum PunishUnit {
    SECOND("sec", 1/60), MINUTE("min", 1), HOUR("hour", 60), DAY("day", 60*24), WEEK("week", 60*24*7),
    MONTH("month", 30*60*24), YEAR("year", 20*60*24*12);
    public String name;
    public int multi;
    PunishUnit(String n, int mult){
        name = n;
        multi = mult;
    }

    public static long getTicks(String un, int time){
        long sec;
        try{
            sec = time * 60;
        }catch (NumberFormatException ex){
            return 0;
        }
        for (PunishUnit unit : PunishUnit.values()){
            if (un.startsWith(unit.name)){
                return (sec *= unit.multi) * 1000;
            }
        }
        return 0;
    }
}
