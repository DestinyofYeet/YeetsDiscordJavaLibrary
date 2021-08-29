package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.nsfwLevel;

public enum NSFWLevel {
    DEFAULT(0),
    EXPLICIT(1),
    SAFE(2),
    AGE_RESTRICTED(3);

    private int value;

    NSFWLevel(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NSFWLevel getFromValue(int value){
        for (NSFWLevel nsfwLevel : values()) {
            if (nsfwLevel.getValue() == value) return nsfwLevel;
        }

        return null;
    }
}
