package me.xsenny.msbox.utils;

public enum Permission {
    MUTE("msbox.mute"), TEMPMUTE("msbox.tempmute"), BAN("msbox.ban"), TEMPBAN("msbox.tempban");

    private String permission;
    Permission(String per){
        permission = per;
    }

    public String getPermission() {
        return permission;
    }
}
