package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.Channel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.GenericChannel;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions.Permission;

import java.util.EnumSet;
import java.util.Optional;

public class VoiceChannel extends Channel {
    private String id, name, rtcRegion;
    private int position, bitrate, userLimit;
    private long idLong;

    private EnumSet<Permission> botPermission;

    public VoiceChannel(GenericChannel data) {
        id = data.getId().orElse(null);
        name = data.getName().orElse(null);
        rtcRegion = data.getRtcRegion().orElse(null);

        position = data.getPosition().orElse(-1);
        bitrate = data.getBitrate().orElse(-1);
        userLimit = data.getUserLimit().orElse(-1);

        idLong = data.getIdLong().orElse(-1L);
    }

    public Optional<String> getId() {
        return Optional.of(id);
    }

    public Optional<String> getName() {
        return Optional.of(name);
    }

    public Optional<String> getRtcRegion() {
        return Optional.of(rtcRegion);
    }

    public Optional<Integer> getPosition() {
        return Optional.of(position);
    }

    public Optional<Integer> getBitrate() {
        return Optional.of(bitrate);
    }

    public Optional<Integer> getUserLimit() {
        return Optional.of(userLimit);
    }

    public Optional<Long> getIdLong() {
        return Optional.of(idLong);
    }

    public Optional<EnumSet<Permission>> getBotPermission() {
        return Optional.of(botPermission);
    }
}
