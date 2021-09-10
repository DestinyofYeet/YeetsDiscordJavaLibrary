package de.uwuwhatsthis.YeetsDiscordLibrary.state.applicationCommands;

public enum ApplicationCommandType {
    SLASH_COMMAND(1),
    USER(2),
    MESSAGE(3);

    private final int type;

    ApplicationCommandType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ApplicationCommandType getFromValue(int type){
        for (ApplicationCommandType value : values()) {
            if (value.type == type) return value;
        }

        return null;
    }
}
