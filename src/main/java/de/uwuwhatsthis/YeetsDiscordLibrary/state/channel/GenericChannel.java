package de.uwuwhatsthis.YeetsDiscordLibrary.state.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.Permission;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.PermissionOverwrite;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.User;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class GenericChannel extends Channel {

    private String guildId, name, topic, lastMessageId, iconHash, ownerId, applicationId, parentId, rtcRegion,
            permissions, lastPinnedMessageTimestamp;
    private Long ownerIdLong, lastMessageIdLong, applicationIdLong, parentIdLong;
    private int typeInt, position, bitrate, userLimit, rateLimitPerUser, videoQualityModeInt, approxMessageCount, approxMemberCount;
    private boolean isNSFW;
    private ChannelType type;

    private List<PermissionOverwrite> permissionOverwrites;
    private List<User> dmRecipients;
    private EnumSet<Permission> permissionsTheBotHas;

    public GenericChannel(JSONObject data){
        super(Helper.getValueString(data, "id"),
                Helper.getValueString(data, "name"));
        // TODO: Thread member data and Thread metadata
        guildId = Helper.getValueString(data, "guildId");
        name = Helper.getValueString(data, "name");
        topic = Helper.getValueString(data, "topic");
        lastMessageId = Helper.getValueString(data, "last_message_id");
        iconHash = Helper.getValueString(data, "icon");
        ownerId = Helper.getValueString(data, "owner_id");
        applicationId = Helper.getValueString(data, "application_id");
        parentId = Helper.getValueString(data, "parent_id");
        lastPinnedMessageTimestamp = Helper.getValueString(data, "last_pin_timestamp");
        rtcRegion = Helper.getValueString(data, "rtc_region");
        permissions = Helper.getValueString(data, "permissions");

        typeInt = Helper.getValueInt(data, "type");
        position = Helper.getValueInt(data, "position");
        bitrate = Helper.getValueInt(data, "bitrate");
        userLimit = Helper.getValueInt(data, "user_limit");
        rateLimitPerUser = Helper.getValueInt(data, "rate_limit_per_user");
        videoQualityModeInt = Helper.getValueInt(data, "video_quality_mode");
        approxMessageCount = Helper.getValueInt(data, "message_count");
        approxMemberCount = Helper.getValueInt(data, "member_count");

        isNSFW = Helper.getValueBool(data, "nsfw");

        ownerIdLong = Helper.parseLong(ownerId);
        applicationIdLong = Helper.parseLong(applicationId);
        parentIdLong = Helper.parseLong(parentId);
        lastMessageIdLong = Helper.parseLong(lastMessageId);

        type = ChannelType.getFromValue(typeInt);

        if (data.has("permission_overwrites")){
            permissionOverwrites = new ArrayList<>();
            data.getJSONArray("permission_overwrites").forEach(rawData -> {
                JSONObject permissionOverwritesData = (JSONObject) rawData;

                permissionOverwrites.add(new PermissionOverwrite(permissionOverwritesData));
            });
        }

        if (data.has("recipients")){
            dmRecipients = new ArrayList<>();
            data.getJSONArray("recipients").forEach(rawData -> {
                JSONObject recipient = (JSONObject) rawData;

                dmRecipients.add(new User(recipient));
            });
        }

        permissionsTheBotHas = null;
        if (data.has("permissions")){
            permissionsTheBotHas = Permission.getPermissions(Helper.parseLong(permissions));
        }
    }

    public Optional<String> getGuildId() {
        return Optional.ofNullable(guildId);
    }

    @Override
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getTopic() {
        return Optional.ofNullable(topic);
    }

    public Optional<String> getLastMessageId() {
        return Optional.ofNullable(lastMessageId);
    }

    public Optional<String> getIconHash() {
        return Optional.ofNullable(iconHash);
    }

    public Optional<String> getOwnerId() {
        return Optional.ofNullable(ownerId);
    }

    public Optional<String> getApplicationId() {
        return Optional.ofNullable(applicationId);
    }

    public Optional<String> getParentId() {
        return Optional.ofNullable(parentId);
    }

    public Optional<String> getRtcRegion() {
        return Optional.ofNullable(rtcRegion);
    }

    public Optional<String> getPermissions() {
        return Optional.ofNullable(permissions);
    }

    public Optional<String> getLastPinnedMessageTimestamp() {
        return Optional.ofNullable(lastPinnedMessageTimestamp);
    }

    public Optional<Long> getOwnerIdLong() {
        return Optional.ofNullable(ownerIdLong);
    }

    public Optional<Long> getLastMessageIdLong() {
        return Optional.ofNullable(lastMessageIdLong);
    }

    public Optional<Long> getApplicationIdLong() {
        return Optional.ofNullable(applicationIdLong);
    }

    public Optional<Long> getParentIdLong() {
        return Optional.ofNullable(parentIdLong);
    }

    public Optional<Integer> getTypeInt() {
        return Optional.ofNullable(typeInt);
    }

    public Optional<Integer> getPosition() {
        return Optional.ofNullable(position);
    }

    public Optional<Integer> getBitrate() {
        return Optional.ofNullable(bitrate);
    }

    public Optional<Integer> getUserLimit() {
        return Optional.ofNullable(userLimit);
    }

    public Optional<Integer> getRateLimitPerUser() {
        return Optional.ofNullable(rateLimitPerUser);
    }

    public Optional<Integer> getVideoQualityModeInt() {
        return Optional.ofNullable(videoQualityModeInt);
    }

    public Optional<Integer> getApproxMessageCount() {
        return Optional.ofNullable(approxMessageCount);
    }

    public Optional<Integer> getApproxMemberCount() {
        return Optional.ofNullable(approxMemberCount);
    }

    public Optional<Boolean> isNSFW() {
        return Optional.ofNullable(isNSFW);
    }

    public Optional<ChannelType> getType() {
        return Optional.ofNullable(type);
    }

    public Optional<List<PermissionOverwrite>> getPermissionOverwrites() {
        return Optional.ofNullable(permissionOverwrites);
    }

    public Optional<List<User>> getDmRecipients() {
        return Optional.ofNullable(dmRecipients);
    }

    public Optional<EnumSet<Permission>> getPermissionsTheBotHas() {
        return Optional.ofNullable(permissionsTheBotHas);
    }
}
