package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class Role {
    /*
    position = position in the server hierarchy
     */
    private int color, position;

    private String permissions, name, iconHash, id;

    private Long idLong;

    /*
    managed = whether this role is managed by an integration
    isPinned = is this role pinned in the sidebar
     */
    private boolean isManaged, isMentionable, isPinned;

    private RoleTags tags;

    public Role(JSONObject data){
        color = Helper.getValueInt(data, "color");
        permissions = Helper.getValueString(data, "permissions");
        isManaged = Helper.getValueBool(data, "managed");
        name = Helper.getValueString(data, "name");
        iconHash = Helper.getValueString(data, "icon");
        isMentionable = Helper.getValueBool(data, "mentionable");
        position = Helper.getValueInt(data, "position");
        id = Helper.getValueString(data, "id");
        idLong = Helper.parseLong(id);
        isPinned = Helper.getValueBool(data, "hoist");

        tags = null;
        if (data.has("tags")){
            tags = new RoleTags(data.getJSONObject("tags"));
        }
    }

    public Optional<Integer> getColor() {
        return Optional.of(color);
    }

    public Optional<Integer> getPosition() {
        return Optional.of(position);
    }

    public Optional<String> getPermissions() {
        return Optional.of(permissions);
    }

    public Optional<String> getName() {
        return Optional.of(name);
    }

    public Optional<String> getIconHash() {
        return Optional.of(iconHash);
    }

    public Optional<String> getId() {
        return Optional.of(id);
    }

    public Optional<Long> getIdLong() {
        return Optional.of(idLong);
    }

    public Optional<Boolean> isManaged() {
        return Optional.of(isManaged);
    }

    public Optional<Boolean> isMentionable() {
        return Optional.of(isMentionable);
    }

    public Optional<Boolean> isPinned() {
        return Optional.of(isPinned);
    }

    public Optional<RoleTags> getTags() {
        return Optional.of(tags);
    }
}
