package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.provider;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class EmbedProvider {
    private String name, url;

    public EmbedProvider(JSONObject data){
        name = Helper.getValueString(data, "name");
        url = Helper.getValueString(data, "url");
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getUrl() {
        return Optional.ofNullable(url);
    }
}
