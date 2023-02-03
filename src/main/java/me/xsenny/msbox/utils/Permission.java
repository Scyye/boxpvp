package me.xsenny.msbox.utils;

public enum Permission {
    MUTE("msbox.mute"), TEMPMUTE("msbox.tempmute"), BAN("msbox.ban"), TEMPBAN("msbox.tempban"), KICK("msbox.kick"), VIEW_REPORTS("msbox.view_reports"), ALL("msbox.*");

    private String permission;
    Permission(String per){
        permission = per;
    }

    public String getPermission() {
        return permission;
    }
}
