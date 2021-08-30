package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.Channel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.GenericChannel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel.TextChannel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel.VoiceChannel;
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
import java.util.Optional;

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
    private List<Channel> TextChannel, VoiceChannel, GenericChannels;

    private VerificationLevel verificationLevel;
    private ExplicitContentFilterLevel explicitContentFilterLevel;

    private final static Debugger debugger = new Debugger("Guild");

    public Guild(JSONObject data){
        // --------------- String --------------------------

        preferredLocale = Helper.getValueString(data, "preferred_locale");
        ownerId = Helper.getValueString(data, "owner_id");
        iconHash = Helper.getValueString(data, "icon");
        description = Helper.getValueString(data, "description");
        systemChannelId = Helper.getValueString(data, "system_channel_id");
        rulesChannelId = Helper.getValueString(data, "rules_channel_id");
        afkChannelId = Helper.getValueString(data, "afk_channel_id");
        guildId = Helper.getValueString(data, "id");
        vanityUrlCode = Helper.getValueString(data, "vanity_url_code");
        bannerHash = Helper.getValueString(data, "banner");
        discordNotificationChannelId = Helper.getValueString(data, "public_updates_channel_id");
        applicationId = Helper.getValueString(data, "application_id");
        botJoinedGuildTimestamp = Helper.getValueString(data, "joined_at");

        // --------------- Int -------------------------------

        defaultMessageNotifications = Helper.getValueInt(data, "default_message_notifications");
        afkTimeout = Helper.getValueInt(data, "afk_timeout");
        maxMembers = Helper.getValueInt(data, "max_members");
        maxVideoChannelUsers = Helper.getValueInt(data, "max_video_channel_users");
        memberCount = Helper.getValueInt(data, "member_count");
        nsfwLevelInt = Helper.getValueInt(data, "nsfw_level");
        numberOfBoosts = Helper.getValueInt(data, "premium_subscription_count");
        verificationLevelInt = Helper.getValueInt(data, "verification_level");
        explicitContentFilterLevelInt = Helper.getValueInt(data, "explicit_content_filter");

        // ---------------- Bool ------------------------------

        isUnavailable = Helper.getValueBool(data, "unavailable");
        isLazy = Helper.getValueBool(data, "lazy");
        isLarge = Helper.getValueBool(data, "large");
        isNSFW = Helper.getValueBool(data, "nsfw");

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

            Optional<VoiceState> voiceState = getVoiceStateById(memberData.getJSONObject("user").getString("id"));
            members.add(new Member(memberData, voiceState.orElse(null), this));
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

        if (data.has("channels")){
            TextChannel = VoiceChannel = new ArrayList<>();

            data.getJSONArray("channels").forEach(rawData -> {
                JSONObject channelData = (JSONObject) rawData;

                GenericChannel genericChannel = new GenericChannel(channelData);

                genericChannel.getType().ifPresent(type -> {
                    switch (type){
                        case GUILD_TEXT:
                            TextChannel.add(new TextChannel(genericChannel));
                            break;

                        case GUILD_VOICE:
                            VoiceChannel.add(new VoiceChannel(genericChannel));
                            break;
                    }
                });
            });
        }

    }

    public Optional<Role> getRoleById(String id){
        for (Role role : roles) {
            if (role.getId().equals(id)) return Optional.of(role);
        }
        return Optional.empty();
    }

    public Optional<VoiceState> getVoiceStateById(String id){
        for (VoiceState voiceState : voiceStates) {
            Optional<Member> memberOptional = voiceState.getMember();
            if (memberOptional.isPresent()){
                if (memberOptional.get().getUser().getId().equals(id)) return Optional.of(voiceState);
            }
        }

        return Optional.empty();
    }

    public Optional<String> getPreferredLocale() {
        return Optional.of(preferredLocale);
    }

    public Optional<String> getOwnerId() {
        return Optional.of(ownerId);
    }

    public Optional<Long> getOwnerIdLong() {
        return Optional.of(ownerIdLong);
    }

    public Optional<String> getIconHash() {
        return Optional.of(iconHash);
    }

    public Optional<String> getDescription() {
        return Optional.of(description);
    }

    public Optional<String> getSystemChannelId() {
        return Optional.of(systemChannelId);
    }

    public Optional<String> getRulesChannelId() {
        return Optional.of(rulesChannelId);
    }

    public Optional<String> getAfkChannelId() {
        return Optional.of(afkChannelId);
    }

    public Optional<String> getGuildId() {
        return Optional.of(guildId);
    }

    public Optional<Long> getSystemChannelIdLong() {
        return Optional.of(systemChannelIdLong);
    }

    public Optional<Long> getAfkChannelIdLong() {
        return Optional.of(afkChannelIdLong);
    }

    public Optional<Long> getGuildIdLong() {
        return Optional.of(guildIdLong);
    }

    public Optional<Integer> getDefaultMessageNotifications() {
        return Optional.of(defaultMessageNotifications);
    }

    public Optional<Integer> getAfkTimeout() {
        return Optional.of(afkTimeout);
    }

    public Optional<Boolean> isUnavailable() {
        return Optional.of(isUnavailable);
    }

    public Optional<Boolean> isLazy() {
        return Optional.of(isLazy);
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
