package de.uwuwhatsthis.YeetsDiscordLibrary.utils;

import de.uwuwhatsthis.YeetsDiscordLibrary.errors.InvalidArgumentError;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.argument.Argument;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.argument.choice.Choice;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Helper {
    public static String getValueString(JSONObject data, String key){
        if (!data.has(key) || data.get(key).equals(JSONObject.NULL)) return null;
        return data.getString(key);
    }

    public static boolean getValueBool(JSONObject data, String key){
        if (!data.has(key)) return false;
        return data.getBoolean(key);
    }

    public static int getValueInt(JSONObject data, String key){
        if (!data.has(key) || data.get(key).equals(JSONObject.NULL)) return -1;
        return data.getInt(key);
    }

    public static Long parseLong(String value){
        return Long.parseLong(value != null ? value : String.valueOf(-1));
    }

    public static LocalDateTime parseISO8601(String isoTimestamp){
        if (isoTimestamp == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("+yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.of("UTC"));

        return LocalDateTime.parse(isoTimestamp, formatter);
    }

    public static boolean isValidCommandName(String input){
        if (input == null) return false;

        return input.matches("^[\\w-]{1,32}$");
    }

    public static JSONObject getArgument(Argument argument) throws InvalidArgumentError {
        JSONObject jsonObject = new JSONObject();

        if (!argument.getName().isPresent()){
            throw new InvalidArgumentError("No name is present");
        }

        String argumentName = argument.getName().get();

        if (!argument.getDescription().isPresent()){
            throw new InvalidArgumentError("No description is present");
        }

        String argumentDescription = argument.getDescription().get();

        if (!isValidCommandName(argumentName)){
            throw new InvalidArgumentError("The argument name is invalid! Must be lowercase and must not be longer than 32 characters!");
        }

        jsonObject.put("name", argumentName);
        jsonObject.put("description", argumentDescription);
        jsonObject.put("required", argument.isRequired().orElse(false));
        jsonObject.put("type", argument.getOptionType().getValue());

        JSONArray choicesArray = new JSONArray();

        for (Choice choice : argument.getChoices()) {
            choicesArray.put(new JSONObject()
                    .put("name", choice.getValue())
                    .put("value", choice.getValue()));
        }

        if (!choicesArray.isEmpty()){
            jsonObject.put("choices", choicesArray);
        }


        JSONArray argumentsArray = new JSONArray();

        for (Argument argument1 : argument.getArguments()){
            argumentsArray.put(getArgument(argument1));
        }

        if (!argumentsArray.isEmpty()){
            jsonObject.put("options", argumentsArray);
        }

        return jsonObject;
    }

}
