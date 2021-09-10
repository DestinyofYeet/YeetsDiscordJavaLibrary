package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.generic;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class GenericEmbedImage {
    protected String sourceUrl, proxyUrl;
    protected int height, width;

    public GenericEmbedImage(JSONObject data){
        sourceUrl = Helper.getValueString(data, "url");
        proxyUrl = Helper.getValueString(data, "proxy_url");

        height = Helper.getValueInt(data, "height");
        width = Helper.getValueInt(data, "width");
    }

    public Optional<String> getSourceUrl() {
        return Optional.ofNullable(sourceUrl);
    }

    public Optional<String> getProxyUrl() {
        return Optional.ofNullable(proxyUrl);
    }

    public Optional<Integer> getHeight() {
        return Optional.ofNullable(height);
    }

    public Optional<Integer> getWidth() {
        return Optional.ofNullable(width);
    }
}
