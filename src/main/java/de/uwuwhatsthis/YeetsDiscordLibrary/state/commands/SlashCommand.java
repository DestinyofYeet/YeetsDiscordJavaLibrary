package de.uwuwhatsthis.YeetsDiscordLibrary.state.commands;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.argument.Argument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlashCommand {
    private String commandName, description, guildID;

    private boolean isGuild;

    private final List<Argument> arguments;

    private SlashCommand(){
        arguments = new ArrayList<>();
    }

    protected SlashCommand(String commandName, String description){
        this();

        this.commandName = commandName;
        this.description = description;
        this.isGuild = false;
        this.guildID = null;
    }

    protected SlashCommand(String commandName, String description, String guildID){
        this();

        this.commandName = commandName;
        this.description = description;
        this.guildID = guildID;
        this.isGuild = true;
    }

    protected void addArgument(Argument argument){
        arguments.add(argument);
    }

    protected void addArguments(Argument... arguments){
        this.arguments.addAll(Arrays.asList(arguments));
    }

    protected void onTrigger(){

    }

    public String getCommandName() {
        return commandName;
    }

    public String getDescription() {
        return description;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public String getGuildID() {
        return guildID;
    }

    public boolean isGuild() {
        return isGuild;
    }
}
