package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.emojis.Emoji;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.explicitContentFilterLevel.ExplicitContentFilterLevel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.features.GuildFeatures;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.member.Member;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role.Role;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.stickers.Sticker;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.verification.VerificationLevel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.voiceState.VoiceState;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Guild {
    private String preferredLocale, ownerId, iconHash, description, systemChannelId, rulesChannelId, afkChannelId, guildId,
            vanityUrlCode, bannerHash, discordNotificationChannelId, applicationId, botJoinedGuildTimestamp;

    private Long ownerIdLong, systemChannelIdLong, afkChannelIdLong, guildIdLong, discordNotificationChannelIdLong,
            applicationIdLong;

    private int defaultMessageNotifications, afkTimeout, maxMembers, maxVideoChannelUsers, memberCount, nsfwLevelInt,
            numberOfBoosts, verificationLevelInt, explicitContentFilterLevelInt;

    private boolean isUnavailable, isLazy, isLarge, isNSFW;

    private List<Role> roles;
    private List<GuildFeatures> guildFeatures;
    private List<Member> members;
    private List<Sticker> stickers;
    private List<VoiceState> voiceStates;
    private List<Emoji> emojis;

    private VerificationLevel verificationLevel;
    private ExplicitContentFilterLevel explicitContentFilterLevel;

    private final static Debugger debugger = new Debugger("Guild");

    public Guild(JSONObject data){
        // --------------- String --------------------------

        preferredLocale = Helper.applyValueString(data, "preferred_locale");
        ownerId = Helper.applyValueString(data, "owner_id");
        iconHash = Helper.applyValueString(data, "icon");
        description = Helper.applyValueString(data, "description");
        systemChannelId = Helper.applyValueString(data, "system_channel_id");
        rulesChannelId = Helper.applyValueString(data, "rules_channel_id");
        afkChannelId = Helper.applyValueString(data, "afk_channel_id");
        guildId = Helper.applyValueString(data, "id");
        vanityUrlCode = Helper.applyValueString(data, "vanity_url_code");
        bannerHash = Helper.applyValueString(data, "banner");
        discordNotificationChannelId = Helper.applyValueString(data, "public_updates_channel_id");
        applicationId = Helper.applyValueString(data, "application_id");
        botJoinedGuildTimestamp = Helper.applyValueString(data, "joined_at");

        // --------------- Int -------------------------------

        defaultMessageNotifications = Helper.applyValueInt(data, "default_message_notifications");
        afkTimeout = Helper.applyValueInt(data, "afk_timeout");
        maxMembers = Helper.applyValueInt(data, "max_members");
        maxVideoChannelUsers = Helper.applyValueInt(data, "max_video_channel_users");
        memberCount = Helper.applyValueInt(data, "member_count");
        nsfwLevelInt = Helper.applyValueInt(data, "nsfw_level");
        numberOfBoosts = Helper.applyValueInt(data, "premium_subscription_count");
        verificationLevelInt = Helper.applyValueInt(data, "verification_level");
        explicitContentFilterLevelInt = Helper.applyValueInt(data, "explicit_content_filter");

        // ---------------- Bool ------------------------------

        isUnavailable = Helper.applyValueBool(data, "unavailable");
        isLazy = Helper.applyValueBool(data, "lazy");
        isLarge = Helper.applyValueBool(data, "large");
        isNSFW = Helper.applyValueBool(data, "nsfw");

        // --------------- Long ------------------------------

        ownerIdLong = Helper.parseLong(ownerId);
        systemChannelIdLong = Helper.parseLong(systemChannelId);
        afkChannelIdLong = Helper.parseLong(afkChannelId);
        guildIdLong = Helper.parseLong(guildId);
        discordNotificationChannelIdLong = Helper.parseLong(discordNotificationChannelId);
        applicationIdLong = Helper.parseLong(applicationId);


        // -------------- Custom things -----------------

        verificationLevel = VerificationLevel.getFromValue(verificationLevelInt);
        explicitContentFilterLevel = ExplicitContentFilterLevel.getFromValue(verificationLevelInt);

        // --------------- Lists ---------------------------

        roles = new ArrayList<>();
        data.getJSONArray("roles").forEach(rawData -> {
            JSONObject roleData = (JSONObject) rawData;
            roles.add(new Role(roleData));
        });


        guildFeatures = new ArrayList<>();
        data.getJSONArray("features").forEach(rawData -> {
            String feature = (String) rawData;

            guildFeatures.add(GuildFeatures.valueOf(feature));
        });

        voiceStates = new ArrayList<>();
        if (data.has("voice_states")){
            data.getJSONArray("voice_states").forEach(rawData -> {
                JSONObject voiceData = (JSONObject) rawData;

                voiceStates.add( new VoiceState(voiceData, this));
            });
        }

        members = new ArrayList<>();
        data.getJSONArray("members").forEach(rawData -> {
            JSONObject memberData = (JSONObject) rawData;

            members.add(new Member(memberData, getVoiceStateById(memberData.getJSONObject("user").getString("id")),this));
        });

        stickers = new ArrayList<>();
        if (data.has("stickers")){
            data.getJSONArray("stickers").forEach(rawData -> {
                JSONObject stickerData = (JSONObject) rawData;
                stickers.add(new Sticker(stickerData));
            });
        }

        emojis = new ArrayList<>();
        if (data.has("emojis")){
            data.getJSONArray("emojis").forEach(rawData -> {
                JSONObject emojiData = (JSONObject) rawData;

                emojis.add(new Emoji(emojiData, this));
            });
        }

    }

    public Role getRoleById(String id){
        for (Role role : roles) {
            if (role.getId().equals(id)) return role;
        }
        return null;
    }

    public VoiceState getVoiceStateById(String id){
        for (VoiceState voiceState : voiceStates) {
            if (voiceState.getMember().getUser().getId().equals(id)) return voiceState;
        }

        return null;
    }

    public String getPreferredLocale() {
        return preferredLocale;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getIconHash() {
        return iconHash;
    }

    public String getDescription() {
        return description;
    }

    public String getSystemChannelId() {
        return systemChannelId;
    }

    public String getRulesChannelId() {
        return rulesChannelId;
    }

    public String getAfkChannelId() {
        return afkChannelId;
    }

    public String getGuildId() {
        return guildId;
    }

    public Long getOwnerIdLong() {
        return ownerIdLong;
    }

    public Long getSystemChannelIdLong() {
        return systemChannelIdLong;
    }

    public Long getAfkChannelIdLong() {
        return afkChannelIdLong;
    }

    public Long getGuildIdLong() {
        return guildIdLong;
    }

    public int getDefaultMessageNotifications() {
        return defaultMessageNotifications;
    }

    public int getAfkTimeout() {
        return afkTimeout;
    }

    public boolean isUnavailable() {
        return isUnavailable;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<GuildFeatures> getGuildFeatures() {
        return guildFeatures;
    }

    public List<Member> getMembers() {
        return members;
    }
}
