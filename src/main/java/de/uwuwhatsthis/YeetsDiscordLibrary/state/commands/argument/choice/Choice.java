package de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.argument.choice;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.applicationCommands.ValueType;

public class Choice {
    private final String name;
    private final Object value;

    private ValueType optionType;

    public Choice(String name, int value){
        this.name = name;
        this.value = value;

        optionType = ValueType.INTEGER;
    }

    public Choice(String name, String value){
        this.name = name;
        this.value = value;

        optionType = ValueType.STRING;
    }

    public Choice(String name, boolean value){
        this.name = name;
        this.value = value;

        optionType = ValueType.BOOLEAN;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public ValueType getOptionType() {
        return optionType;
    }
}
