package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.emojis;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role.Role;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Emoji {
    private String id, name;
    private Long idLong;
    private boolean doesRequireColons, isManaged, isAnimated, isAvailable;
    private User userThatCreatedThisEmoji;
    private List<String> accessibleByRolesIds;
    private List<Role> accessibleByRoles;

    public Emoji(JSONObject data, Guild guild){
        id = Helper.getValueString(data, "id");
        name = Helper.getValueString(data, "name");

        doesRequireColons = Helper.getValueBool(data, "require_colons");
        isManaged = Helper.getValueBool(data, "managed");
        isAnimated = Helper.getValueBool(data, "animated");
        isAvailable = Helper.getValueBool(data, "available");

        idLong = Helper.parseLong(id);

        accessibleByRolesIds = new ArrayList<>();
        if (data.has("roles")){
            data.getJSONArray("roles").forEach(rawData -> {
                String roleId = (String) rawData;
                accessibleByRolesIds.add(roleId);

                Optional<Role> guildRole = guild.getRoleById(roleId);
                guildRole.ifPresent(role -> accessibleByRoles.add(role));
            });
        }

        userThatCreatedThisEmoji = null;
        if (data.has("user")){
            userThatCreatedThisEmoji = new User(data.getJSONObject("user"));
        }

    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Long> getIdLong() {
        return Optional.ofNullable(idLong);
    }

    public Optional<Boolean> isDoesRequireColons() {
        return Optional.ofNullable(doesRequireColons);
    }

    public Optional<Boolean> isManaged() {
        return Optional.ofNullable(isManaged);
    }

    public Optional<Boolean> isAnimated() {
        return Optional.ofNullable(isAnimated);
    }

    public Optional<Boolean> isAvailable() {
        return Optional.ofNullable(isAvailable);
    }

    public Optional<User> getUserThatCreatedThisEmoji() {
        return Optional.ofNullable(userThatCreatedThisEmoji);
    }

    public Optional<List<String>> getAccessibleByRolesIds() {
        return Optional.ofNullable(accessibleByRolesIds);
    }

    public Optional<List<Role>> getAccessibleByRoles() {
        return Optional.ofNullable(accessibleByRoles);
    }
}
