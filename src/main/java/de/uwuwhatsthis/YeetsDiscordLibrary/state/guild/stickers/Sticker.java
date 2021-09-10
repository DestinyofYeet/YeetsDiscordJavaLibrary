package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.stickers;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class Sticker {
    private String id, packId, name, description, tags, guildId;
    private int typeInt, formatTypeInt, sortValue;
    private boolean available;
    private User uploadedByUser;
    private StickerType stickerType;
    private StickerFormatType stickerFormatType;
    private Guild guild;

    public Sticker(JSONObject data){
        id = Helper.getValueString(data, "id");
        packId = Helper.getValueString(data, "pack_id");
        name = Helper.getValueString(data, "name");
        description = Helper.getValueString(data, "description");
        tags = Helper.getValueString(data, "tags");
        guildId = Helper.getValueString(data, "guild_id");

        typeInt = Helper.getValueInt(data, "type");
        formatTypeInt = Helper.getValueInt(data, "format_type");
        sortValue = Helper.getValueInt(data, "sort_value");

        available = Helper.getValueBool(data, "available");

        stickerType = StickerType.getFromValue(typeInt);
        stickerFormatType = StickerFormatType.getFromValue(formatTypeInt);


        if (data.has("user")){
            uploadedByUser = new User(data.getJSONObject("user"));
        }

        guild = StateManager.getInstance().getGuildById(guildId).orElse(null);

    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getPackId() {
        return Optional.ofNullable(packId);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getTags() {
        return Optional.ofNullable(tags);
    }

    public Optional<String> getGuildId() {
        return Optional.ofNullable(guildId);
    }

    public Optional<Integer> getTypeInt() {
        return Optional.ofNullable(typeInt);
    }

    public Optional<Integer> getFormatTypeInt() {
        return Optional.ofNullable(formatTypeInt);
    }

    public Optional<Integer> getSortValue() {
        return Optional.ofNullable(sortValue);
    }

    public Optional<Boolean> isAvailable() {
        return Optional.ofNullable(available);
    }

    public Optional<User> getUploadedByUser() {
        return Optional.ofNullable(uploadedByUser);
    }

    public Optional<StickerType> getStickerType() {
        return Optional.ofNullable(stickerType);
    }

    public Optional<StickerFormatType> getStickerFormatType() {
        return Optional.ofNullable(stickerFormatType);
    }

    public Optional<Guild> getGuild() {
        return Optional.ofNullable(guild);
    }
}
