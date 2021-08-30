package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions;

public enum PermissionType {
    ROLE(0),
    MEMBER(1);

    private final int value;

    PermissionType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PermissionType getFromValue(int value){
        for (PermissionType permissionType : values()) {
            if (permissionType.getValue() == value) return permissionType;
        }

        return null;
    }
}
