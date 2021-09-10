package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.Channel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.GenericChannel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.Permission;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.PermissionOverwrite;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class TextChannel extends GuildChannel {
    private String  topic, lastMessageId, lastPinTimestamp;
    private int rateLimitPerUser;
    private long  lastMessageIdLong;
    private boolean isNSFW;

    private List<PermissionOverwrite> permissionOverwrites;
    private EnumSet<Permission> botPermissions;

    public TextChannel(GenericChannel data) {
        super(data.getId().orElse(null),
                data.getName().orElse(null),
                data.getGuildId().orElse(null));
        topic = data.getTopic().orElse(null);
        lastMessageId = data.getLastMessageId().orElse(null);
        lastPinTimestamp = data.getLastPinnedMessageTimestamp().orElse(null);

        rateLimitPerUser = data.getRateLimitPerUser().orElse(-1);

        idLong = data.getIdLong().orElse(-1L);
        guildIdLong = data.getIdLong().orElse(-1L);
        lastMessageIdLong = data.getIdLong().orElse(-1L);

        isNSFW = data.isNSFW().orElse(false);

        permissionOverwrites = data.getPermissionOverwrites().orElse(null);
        botPermissions = data.getPermissionsTheBotHas().orElse(null);
    }

    public Optional<String> getTopic() {
        return Optional.ofNullable(topic);
    }

    public Optional<String> getLastMessageId() {
        return Optional.ofNullable(lastMessageId);
    }

    public Optional<String> getLastPinTimestamp() {
        return Optional.ofNullable(lastPinTimestamp);
    }

    public Optional<Integer> getRateLimitPerUser() {
        return Optional.ofNullable(rateLimitPerUser);
    }

    public Optional<Long> getLastMessageIdLong() {
        return Optional.ofNullable(lastMessageIdLong);
    }

    public Optional<Boolean> isNSFW() {
        return Optional.ofNullable(isNSFW);
    }

    public Optional<List<PermissionOverwrite>> getPermissionOverwrites() {
        return Optional.ofNullable(permissionOverwrites);
    }

    public Optional<EnumSet<Permission>> getBotPermissions() {
        return Optional.ofNullable(botPermissions);
    }
}
