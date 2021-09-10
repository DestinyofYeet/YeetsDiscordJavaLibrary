package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.thumbnail;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.embed.generic.GenericEmbedImage;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class EmbedThumbnail extends GenericEmbedImage {

    public EmbedThumbnail(JSONObject data) {
        super(data);
    }
}
