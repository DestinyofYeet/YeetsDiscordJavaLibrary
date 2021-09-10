package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.footer;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class EmbedFooter {
    private String text, iconSourceUrl, iconProxyUrl;

    public EmbedFooter(JSONObject data){
        text = Helper.getValueString(data, "text");
        iconSourceUrl = Helper.getValueString(data, "icon_url");
        iconProxyUrl = Helper.getValueString(data, "proxy_icon_url");
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    public Optional<String> getIconSourceUrl() {
        return Optional.ofNullable(iconSourceUrl);
    }

    public Optional<String> getIconProxyUrl() {
        return Optional.ofNullable(iconProxyUrl);
    }
}
