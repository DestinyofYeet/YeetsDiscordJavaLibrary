package de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.argument;

import de.uwuwhatsthis.YeetsDiscordLibrary.errors.ChoiceTypeNotMatchingArgumentTypeError;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.applicationCommands.ValueType;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.argument.choice.Choice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Argument {
    private final String name, description;
    private final boolean isRequired;
    private final ValueType optionType;

    private List<Choice> choices;
    private List<Argument> arguments;

    public Argument(String name, String description, boolean required, ValueType type){
        this.name = name;
        this.description = description;
        this.isRequired = required;
        this.optionType = type;

        choices = new ArrayList<>();
        arguments = new ArrayList<>();
    }

    public void addChoice(Choice choice) {
        if (!choice.getOptionType().equals(optionType)){
            try {
                throw new ChoiceTypeNotMatchingArgumentTypeError("Choice type is not matching argument type!\nChoice: " + choice.getOptionType().toString() + "\nArgument: " + optionType.toString());
            } catch (ChoiceTypeNotMatchingArgumentTypeError e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        choices.add(choice);
    }

    public void addChoices(Choice... choices) {
        for (Choice choice : choices) {
            addChoice(choice);
        }
    }

    public void addArgument(Argument argument){
        arguments.add(argument);
    }

    public void addArguments(Argument... arguments){
        this.arguments.addAll(Arrays.asList(arguments));
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Boolean> isRequired() {
        return Optional.ofNullable(isRequired);
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public ValueType getOptionType() {
        return optionType;
    }
}
