package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.Channel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.GenericChannel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.Permission;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.PermissionOverwrite;
import org.json.JSONObject;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class TextChannel extends Channel {
    private String id, guildId, name, topic, lastMessageId, lastPinTimestamp;
    private int rateLimitPerUser;
    private long idLong, guildIdLong, lastMessageIdLong;
    private boolean isNSFW;

    private List<PermissionOverwrite> permissionOverwrites;
    private EnumSet<Permission> botPermissions;

    public TextChannel(GenericChannel data) {
        id = data.getId().orElse(null);
        guildId = data.getGuildId().orElse(null);
        name = data.getName().orElse(null);
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

    public Optional<String> getId() {
        return Optional.of(id);
    }

    public Optional<String> getGuildId() {
        return Optional.of(guildId);
    }

    public Optional<String> getName() {
        return Optional.of(name);
    }

    public Optional<String> getTopic() {
        return Optional.of(topic);
    }

    public Optional<String> getLastMessageId() {
        return Optional.of(lastMessageId);
    }

    public Optional<String> getLastPinTimestamp() {
        return Optional.of(lastPinTimestamp);
    }

    public Optional<Long> getIdLong() {
        return Optional.of(idLong);
    }

    public Optional<Long> getGuildIdLong() {
        return Optional.of(guildIdLong);
    }

    public Optional<Long> getLastMessageIdLong() {
        return Optional.of(lastMessageIdLong);
    }

    public Optional<Boolean> isNSFW() {
        return Optional.of(isNSFW);
    }

    public Optional<List<PermissionOverwrite>> getPermissionOverwrites() {
        return Optional.of(permissionOverwrites);
    }

    public Optional<EnumSet<Permission>> getBotPermissions() {
        return Optional.of(botPermissions);
    }

    public Optional<Integer> getRateLimitPerUser() {
        return Optional.of(rateLimitPerUser);
    }
}
