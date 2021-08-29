package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.verification;

public enum VerificationLevel {
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4);

    private final int level;

    VerificationLevel(int level){
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static VerificationLevel getFromValue(int level){
        for (VerificationLevel value : values()) {
            if (value.getLevel() == level) return value;
        }

        return null;
    }
}
