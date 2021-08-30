package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.stickers;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

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

        guild = StateManager.getInstance().getGuildById(guildId);

    }

    public String getId() {
        return id;
    }

    public String getPackId() {
        return packId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public String getGuildId() {
        return guildId;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public int getFormatTypeInt() {
        return formatTypeInt;
    }

    public int getSortValue() {
        return sortValue;
    }

    public boolean isAvailable() {
        return available;
    }

    public User getUploadedByUser() {
        return uploadedByUser;
    }

    public StickerType getStickerType() {
        return stickerType;
    }

    public StickerFormatType getStickerFormatType() {
        return stickerFormatType;
    }

    public Guild getGuild() {
        return guild;
    }
}
