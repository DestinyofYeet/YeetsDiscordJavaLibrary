package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.author;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class EmbedAuthor {
    private String name, authorUrl, sourceIconUrl, proxyIconUrl;

    public EmbedAuthor(JSONObject data){
        name = Helper.getValueString(data, "name");
        authorUrl = Helper.getValueString(data, "url");
        sourceIconUrl = Helper.getValueString(data, "icon_url");
        proxyIconUrl = Helper.getValueString(data, "proxy_icon_url");
    }

    public Optional<String> getName() {
        return Optional.of(name);
    }

    public Optional<String> getAuthorUrl() {
        return Optional.ofNullable(authorUrl);
    }

    public Optional<String> getSourceIconUrl() {
        return Optional.ofNullable(sourceIconUrl);
    }

    public Optional<String> getProxyIconUrl() {
        return Optional.ofNullable(proxyIconUrl);
    }
}
