package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.field;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class EmbedField {
    private String name, value;
    private boolean isInline;

    public EmbedField(JSONObject data){
        name = Helper.getValueString(data, "name");
        value = Helper.getValueString(data, "value");

        isInline = Helper.getValueBool(data, "inline");
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<Boolean> isInline() {
        return Optional.ofNullable(isInline);
    }
}
