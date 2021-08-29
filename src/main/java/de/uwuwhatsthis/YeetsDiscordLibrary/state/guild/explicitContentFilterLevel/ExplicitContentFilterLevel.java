package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.explicitContentFilterLevel;

public enum ExplicitContentFilterLevel {
    DISABLED(0),
    MEMBERS_WITHOUT_ROLES(1),
    ALL_MEMBERS(2);

    private final int level;

    ExplicitContentFilterLevel(int level){
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static ExplicitContentFilterLevel getFromValue(int level){
        for (ExplicitContentFilterLevel value : values()) {
            if (value.getLevel() == level) return value;
        }

        return null;
    }
}
