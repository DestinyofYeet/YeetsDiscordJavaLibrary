package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.Channel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.GenericChannel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel.GuildChannel;
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
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Guild {
    private String preferredLocale, ownerId, iconHash, description, systemChannelId, rulesChannelId, afkChannelId, guildId,
            vanityUrlCode, bannerHash, discordNotificationChannelId, applicationId, botJoinedGuildTimestamp, name, region;

    private Long ownerIdLong, systemChannelIdLong, afkChannelIdLong, guildIdLong, discordNotificationChannelIdLong,
            applicationIdLong;

    private int defaultMessageNotifications, afkTimeout, maxMembers, maxVideoChannelUsers, memberCount, nsfwLevelInt,
            numberOfBoosts, verificationLevelInt, explicitContentFilterLevelInt, mfaLevel;

    private boolean isUnavailable, isLazy, isLarge, isNSFW;

    private List<Role> roles;
    private List<GuildFeatures> guildFeatures;
    private List<Member> members;
    private List<Sticker> stickers;
    private List<VoiceState> voiceStates;
    private List<Emoji> emojis;
    private List<GuildChannel> channelList;

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
        name = Helper.getValueString(data, "name");
        region = Helper.getValueString(data, "region");

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
        mfaLevel = Helper.getValueInt(data, "mfa_level");

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
        if (data.has("roles")){
            data.getJSONArray("roles").forEach(rawData -> {
                JSONObject roleData = (JSONObject) rawData;

                roles.add(new Role(roleData));
            });
        }


        guildFeatures = new ArrayList<>();
        if (data.has("features")){
            data.getJSONArray("features").forEach(rawData -> {
                String feature = (String) rawData;

                guildFeatures.add(GuildFeatures.valueOf(feature));
            });
        }

        voiceStates = new ArrayList<>();
        if (data.has("voice_states")){
            data.getJSONArray("voice_states").forEach(rawData -> {
                JSONObject voiceData = (JSONObject) rawData;

                voiceStates.add( new VoiceState(voiceData, this));
            });
        }

        members = new ArrayList<>();
        if (data.has("members")){
            data.getJSONArray("members").forEach(rawData -> {
                JSONObject memberData = (JSONObject) rawData;

                Optional<VoiceState> voiceState = getVoiceStateById(memberData.getJSONObject("user").getString("id"));
                members.add(new Member(memberData, voiceState.orElse(null), this));
            });
        }

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
            channelList = new ArrayList<>();

            data.getJSONArray("channels").forEach(rawData -> {
                JSONObject channelData = (JSONObject) rawData;

                GenericChannel genericChannel = new GenericChannel(channelData);

                genericChannel.getType().ifPresent(type -> {
                    switch (type){
                        case GUILD_TEXT:
                            channelList.add(new TextChannel(genericChannel));
                            break;

                        case GUILD_VOICE:
                            channelList.add(new VoiceChannel(genericChannel));
                            break;
                    }
                });
            });
        }
    }

    public Optional<TextChannel> getTextChannelById(String id){
        return getChannelById(id).map(value -> (TextChannel) value);

    }

    public Optional<VoiceChannel> getVoiceChannelById(String id){
        return getChannelById(id).map(value -> (VoiceChannel) value);

    }

    public Optional<Channel> getChannelById(String id){
        for (Channel channel: channelList){
            if (channel.getId().isPresent()){
                if (channel.getId().get().equals(id)) return Optional.of(channel);
            }
        }

        return Optional.empty();
    }

    public Optional<Role> getRoleById(String id){
        for (Role role : roles) {
            if (role.getId().isPresent()){
                if (role.getId().get().equals(id)) return Optional.of(role);
            }
        }

        return Optional.empty();
    }

    public Optional<VoiceState> getVoiceStateById(String id){
        for (VoiceState voiceState : voiceStates) {
            Optional<Member> memberOptional = voiceState.getMember();
            if (memberOptional.isPresent()){
                Optional<User> userOptional = memberOptional.get().getUser();
                if (userOptional.isPresent()){
                    Optional<String> idOptional = userOptional.get().getId();
                    if (idOptional.isPresent()){
                        if (idOptional.get().equals(id)) return Optional.of(voiceState);
                    }
                }
            }
        }

        return Optional.empty();
    }

    public Optional<String> getPreferredLocale() {
        return Optional.ofNullable(preferredLocale);
    }

    public Optional<String> getOwnerId() {
        return Optional.ofNullable(ownerId);
    }

    public Optional<String> getIconHash() {
        return Optional.ofNullable(iconHash);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getSystemChannelId() {
        return Optional.ofNullable(systemChannelId);
    }

    public Optional<String> getRulesChannelId() {
        return Optional.ofNullable(rulesChannelId);
    }

    public Optional<String> getAfkChannelId() {
        return Optional.ofNullable(afkChannelId);
    }

    public Optional<String> getGuildId() {
        return Optional.ofNullable(guildId);
    }

    public Optional<String> getVanityUrlCode() {
        return Optional.ofNullable(vanityUrlCode);
    }

    public Optional<String> getBannerHash() {
        return Optional.ofNullable(bannerHash);
    }

    public Optional<String> getDiscordNotificationChannelId() {
        return Optional.ofNullable(discordNotificationChannelId);
    }

    public Optional<String> getApplicationId() {
        return Optional.ofNullable(applicationId);
    }

    public Optional<String> getBotJoinedGuildTimestamp() {
        return Optional.ofNullable(botJoinedGuildTimestamp);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getRegion() {
        return Optional.ofNullable(region);
    }

    public Optional<Long> getOwnerIdLong() {
        return Optional.ofNullable(ownerIdLong);
    }

    public Optional<Long> getSystemChannelIdLong() {
        return Optional.ofNullable(systemChannelIdLong);
    }

    public Optional<Long> getAfkChannelIdLong() {
        return Optional.ofNullable(afkChannelIdLong);
    }

    public Optional<Long> getGuildIdLong() {
        return Optional.ofNullable(guildIdLong);
    }

    public Optional<Long> getDiscordNotificationChannelIdLong() {
        return Optional.ofNullable(discordNotificationChannelIdLong);
    }

    public Optional<Long> getApplicationIdLong() {
        return Optional.ofNullable(applicationIdLong);
    }

    public Optional<Integer> getDefaultMessageNotifications() {
        return Optional.ofNullable(defaultMessageNotifications);
    }

    public Optional<Integer> getAfkTimeout() {
        return Optional.ofNullable(afkTimeout);
    }

    public Optional<Integer> getMaxMembers() {
        return Optional.ofNullable(maxMembers);
    }

    public Optional<Integer> getMaxVideoChannelUsers() {
        return Optional.ofNullable(maxVideoChannelUsers);
    }

    public Optional<Integer> getMemberCount() {
        return Optional.ofNullable(memberCount);
    }

    public Optional<Integer> getNsfwLevelInt() {
        return Optional.ofNullable(nsfwLevelInt);
    }

    public Optional<Integer> getNumberOfBoosts() {
        return Optional.ofNullable(numberOfBoosts);
    }

    public Optional<Integer> getVerificationLevelInt() {
        return Optional.ofNullable(verificationLevelInt);
    }

    public Optional<Integer> getExplicitContentFilterLevelInt() {
        return Optional.ofNullable(explicitContentFilterLevelInt);
    }

    public Optional<Integer> getMfaLevel() {
        return Optional.ofNullable(mfaLevel);
    }

    public Optional<Boolean> isUnavailable() {
        return Optional.ofNullable(isUnavailable);
    }

    public Optional<Boolean> isLazy() {
        return Optional.ofNullable(isLazy);
    }

    public Optional<Boolean> isLarge() {
        return Optional.ofNullable(isLarge);
    }

    public Optional<Boolean> isNSFW() {
        return Optional.ofNullable(isNSFW);
    }

    public Optional<List<Role>> getRoles() {
        return Optional.ofNullable(roles);
    }

    public Optional<List<GuildFeatures>> getGuildFeatures() {
        return Optional.ofNullable(guildFeatures);
    }

    public Optional<List<Member>> getMembers() {
        return Optional.ofNullable(members);
    }

    public Optional<List<Sticker>> getStickers() {
        return Optional.ofNullable(stickers);
    }

    public Optional<List<VoiceState>> getVoiceStates() {
        return Optional.ofNullable(voiceStates);
    }

    public Optional<List<Emoji>> getEmojis() {
        return Optional.ofNullable(emojis);
    }

    public Optional<List<GuildChannel>> getChannelList() {
        return Optional.ofNullable(channelList);
    }

    public Optional<VerificationLevel> getVerificationLevel() {
        return Optional.ofNullable(verificationLevel);
    }

    public Optional<ExplicitContentFilterLevel> getExplicitContentFilterLevel() {
        return Optional.ofNullable(explicitContentFilterLevel);
    }
}
