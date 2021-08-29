package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.emojis;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role.Role;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Emoji {
    private String id, name;
    private Long idLong;
    private boolean doesRequireColons, isManaged, isAnimated, isAvailable;
    private User userThatCreatedThisEmoji;
    private List<String> accessibleByRolesIds;
    private List<Role> accessibleByRoles;

    public Emoji(JSONObject data, Guild guild){
        id = Helper.applyValueString(data, "id");
        name = Helper.applyValueString(data, "name");

        doesRequireColons = Helper.applyValueBool(data, "require_colons");
        isManaged = Helper.applyValueBool(data, "managed");
        isAnimated = Helper.applyValueBool(data, "animated");
        isAvailable = Helper.applyValueBool(data, "available");

        idLong = Helper.parseLong(id);

        accessibleByRolesIds = new ArrayList<>();
        if (data.has("roles")){
            data.getJSONArray("roles").forEach(rawData -> {
                String roleId = (String) rawData;
                accessibleByRolesIds.add(roleId);

                Role guildRole = guild.getRoleById(roleId);
                if (guildRole != null){
                    accessibleByRoles.add(guildRole);
                }
            });
        }

        userThatCreatedThisEmoji = null;
        if (data.has("user")){
            userThatCreatedThisEmoji = new User(data.getJSONObject("user"));
        }

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getIdLong() {
        return idLong;
    }

    public boolean isDoesRequireColons() {
        return doesRequireColons;
    }

    public boolean isManaged() {
        return isManaged;
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public User getUserThatCreatedThisEmoji() {
        return userThatCreatedThisEmoji;
    }

    public List<String> getAccessibleByRolesIds() {
        return accessibleByRolesIds;
    }

    public List<Role> getAccessibleByRoles() {
        return accessibleByRoles;
    }
}
