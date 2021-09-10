package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.Channel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.GenericChannel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.Permission;

import java.util.EnumSet;
import java.util.Optional;

public class VoiceChannel extends GuildChannel {
    private String rtcRegion;
    private int position, bitrate, userLimit;

    private EnumSet<Permission> botPermission;

    public VoiceChannel(GenericChannel data) {
        super(data.getId().orElse(null),
                data.getName().orElse(null),
                data.getGuildId().orElse(null));
        rtcRegion = data.getRtcRegion().orElse(null);

        position = data.getPosition().orElse(-1);
        bitrate = data.getBitrate().orElse(-1);
        userLimit = data.getUserLimit().orElse(-1);

        idLong = data.getIdLong().orElse(-1L);
    }

    public Optional<String> getRtcRegion() {
        return Optional.ofNullable(rtcRegion);
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

    public Optional<EnumSet<Permission>> getBotPermission() {
        return Optional.ofNullable(botPermission);
    }
}
