package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role;

import org.json.JSONObject;

public class Role {
    /*
    position = position in the server hierarchy
     */
    private int color, position;

    private String permissions, name, icon, id;

    private Long idLong;

    /*
    managed = whether this role is managed by an integration
    isPinned = is this role pinned in the sidebar
     */
    private boolean isManaged, isMentionable, isPinned;

    private RoleTags tags;

    public Role(JSONObject data){
        color = data.getInt("color");
        permissions = data.getString("permissions");
        isManaged = data.getBoolean("managed");
        name = data.getString("name");
        icon = data.get("icon").equals(JSONObject.NULL) ? null : data.getString("icon");
        isMentionable = data.getBoolean("mentionable");
        position = data.getInt("position");
        id = data.getString("id");
        idLong = Long.parseLong(id);
        isPinned = data.getBoolean("hoist");
        if (data.has("tags")){
            tags = new RoleTags(data.getJSONObject("tags"));
        }
    }

    public int getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public Long getIdLong() {
        return idLong;
    }

    public boolean isManaged() {
        return isManaged;
    }

    public boolean isMentionable() {
        return isMentionable;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public RoleTags getTags() {
        return tags;
    }
}
