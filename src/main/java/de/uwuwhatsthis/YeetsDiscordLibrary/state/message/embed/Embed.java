package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.author.EmbedAuthor;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.field.EmbedField;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.footer.EmbedFooter;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.image.EmbedImage;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.provider.EmbedProvider;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.thumbnail.EmbedThumbnail;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.video.EmbedVideo;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Embed {
    private String title, description, url, timestamp;
    private int colorInt;

    private Color color;
    private LocalDateTime dateTime;

    private EmbedFooter footer;
    private EmbedImage image;
    private EmbedThumbnail thumbnail;
    private EmbedVideo video;
    private EmbedProvider provider;
    private EmbedAuthor author;
    private List<EmbedField> fields;

    public Embed(JSONObject data){
        title = Helper.getValueString(data, "title");
        description = Helper.getValueString(data, "description");
        url = Helper.getValueString(data, "url");
        timestamp = Helper.getValueString(data, "timestamp");

        colorInt = Helper.getValueInt(data, "color");

        if (colorInt != -1)
            color = Color.getColor(String.valueOf(colorInt));

        if (timestamp != null)
            dateTime = Helper.parseISO8601(timestamp);

        if (data.has("footer")){
            footer = new EmbedFooter(data.getJSONObject("footer"));
        }

        if (data.has("image")){
            image = new EmbedImage(data.getJSONObject("image"));
        }

        if (data.has("thumbnail")){
            thumbnail = new EmbedThumbnail(data.getJSONObject("thumbnail"));
        }

        if (data.has("video")){
            video = new EmbedVideo(data.getJSONObject("video"));
        }

        if (data.has("provider")){
            provider = new EmbedProvider(data.getJSONObject("provider"));
        }

        if (data.has("author")){
            author = new EmbedAuthor(data.getJSONObject("author"));
        }

        if (data.has("fields")){
            fields = new ArrayList<>();
            data.getJSONArray("fields").forEach(rawData -> {
                JSONObject fieldData = (JSONObject) rawData;
                fields.add(new EmbedField(fieldData));
            });
        }
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getUrl() {
        return Optional.ofNullable(url);
    }

    public Optional<String> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    public Optional<Integer> getColorInt() {
        return Optional.ofNullable(colorInt);
    }

    public Optional<Color> getColor() {
        return Optional.ofNullable(color);
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.ofNullable(dateTime);
    }

    public Optional<EmbedFooter> getFooter() {
        return Optional.ofNullable(footer);
    }

    public Optional<EmbedImage> getImage() {
        return Optional.ofNullable(image);
    }

    public Optional<EmbedThumbnail> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public Optional<EmbedVideo> getVideo() {
        return Optional.ofNullable(video);
    }

    public Optional<EmbedProvider> getProvider() {
        return Optional.ofNullable(provider);
    }

    public Optional<EmbedAuthor> getAuthor() {
        return Optional.ofNullable(author);
    }

    public Optional<List<EmbedField>> getFields() {
        return Optional.ofNullable(fields);
    }
}
