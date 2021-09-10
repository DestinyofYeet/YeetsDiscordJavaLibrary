package de.uwuwhatsthis.YeetsDiscordLibrary.state.applicationCommands;

public enum ValueType {
    STRING(3),
    INTEGER(4),
    BOOLEAN(5);

    private final int value;

    ValueType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
