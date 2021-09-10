package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.member;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role.Role;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.voiceState.VoiceState;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if (data.has("user"))
            user = new User(data.getJSONObject("user"));

        roleIds = new ArrayList<>();
        if (data.has("roles")){
            data.getJSONArray("roles").forEach(rawData -> {
                String roleId = (String) rawData;
                roleIds.add(roleId);
            });
        }


        roles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            guild.getRoles().ifPresent(guildRoles -> guildRoles.forEach(role -> {
                if (role.getId().isPresent()){
                    if (role.getId().get().equals(roleId)){
                        roles.add(role);
                    }
                }
            }));
        });

    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public Optional<String> getNickname() {
        return Optional.ofNullable(nickname);
    }

    public Optional<String> getJoinedAtTimestamp() {
        return Optional.ofNullable(joinedAtTimestamp);
    }

    public Optional<String> getPremiumSinceTimestamp() {
        return Optional.ofNullable(premiumSinceTimestamp);
    }

    public Optional<String> getPermissions() {
        return Optional.ofNullable(permissions);
    }

    public Optional<List<String>> getRoleIds() {
        return Optional.ofNullable(roleIds);
    }

    public Optional<List<Role>> getRoles() {
        return Optional.ofNullable(roles);
    }

    public Optional<Boolean> isDeaf() {
        return Optional.ofNullable(isDeaf);
    }

    public Optional<Boolean> isMuted() {
        return Optional.ofNullable(isMuted);
    }

    public Optional<Boolean> isPending() {
        return Optional.ofNullable(isPending);
    }

    public Optional<Guild> getGuild() {
        return Optional.ofNullable(guild);
    }

    public Optional<VoiceState> getVoiceState() {
        return Optional.ofNullable(voiceState);
    }

    public Optional<String> getAsMention(){
        if (user != null && user.getId().isPresent()){
            String mention = "<@! " + user.getId().get() + ">";
            return Optional.of(mention);
        }

        return Optional.empty();
    }
}
