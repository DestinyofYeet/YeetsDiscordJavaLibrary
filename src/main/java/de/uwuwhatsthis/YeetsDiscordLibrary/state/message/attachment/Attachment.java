package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.attachment;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class Attachment {
    private String id, filename, contentType, sourceUrl, proxyUrl;
    private int sizeInBytes, height, width;

    public Attachment(JSONObject data){
        id = Helper.getValueString(data, "id");
        filename = Helper.getValueString(data, "filename");
        contentType = Helper.getValueString(data, "content_type");
        sourceUrl = Helper.getValueString(data, "url");
        proxyUrl = Helper.getValueString(data, "proxy_url");

        sizeInBytes = Helper.getValueInt(data, "size");
        height = Helper.getValueInt(data, "height");
        width = Helper.getValueInt(data, "width");
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getFilename() {
        return Optional.ofNullable(filename);
    }

    public Optional<String> getContentType() {
        return Optional.ofNullable(contentType);
    }

    public Optional<String> getSourceUrl() {
        return Optional.ofNullable(sourceUrl);
    }

    public Optional<String> getProxyUrl() {
        return Optional.ofNullable(proxyUrl);
    }

    public Optional<Integer> getSizeInBytes() {
        return Optional.ofNullable(sizeInBytes);
    }

    public Optional<Integer> getHeight() {
        return Optional.ofNullable(height);
    }

    public Optional<Integer> getWidth() {
        return Optional.ofNullable(width);
    }
}
