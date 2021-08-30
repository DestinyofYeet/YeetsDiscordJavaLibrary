package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.member;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role.Role;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.voiceState.VoiceState;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private User user;
    private String nickname, joinedAtTimestamp, premiumSinceTimestamp, permissions;
    private List<String> roleIds;
    private List<Role> roles;
    private boolean isDeaf, isMuted, isPending;
    private Guild guild;
    private VoiceState voiceState;

    public Member(JSONObject data, VoiceState voiceState, Guild guild){
        this.guild = guild;
        this.voiceState = voiceState;
        nickname = Helper.getValueString(data, "nick");
        joinedAtTimestamp = Helper.getValueString(data, "joined_at");
        premiumSinceTimestamp = Helper.getValueString(data, "premium_since");
        permissions = Helper.getValueString(data, "permissions");

        isDeaf = Helper.getValueBool(data, "deaf");
        isMuted = Helper.getValueBool(data, "mute");
        isPending = Helper.getValueBool(data, "pending");

        user = new User(data.getJSONObject("user"));

        roleIds = new ArrayList<>();
        data.getJSONArray("roles").forEach(rawData -> {
            String roleId = (String) rawData;
            roleIds.add(roleId);
        });

        roles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            guild.getRoles().forEach(role -> {
                if (role.getId().equals(roleId)){
                    roles.add(role);
                }
            });
        });

    }

    public User getUser() {
        return user;
    }

    public String getNickname() {
        return nickname;
    }

    public String getJoinedAtTimestamp() {
        return joinedAtTimestamp;
    }

    public String getPremiumSinceTimestamp() {
        return premiumSinceTimestamp;
    }

    public String getPermissions() {
        return permissions;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean isDeaf() {
        return isDeaf;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public boolean isPending() {
        return isPending;
    }

    public Guild getGuild() {
        return guild;
    }

    public VoiceState getVoiceState() {
        return voiceState;
    }
}
