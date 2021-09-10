package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.Channel;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;

import java.util.Optional;

public class GuildChannel extends Channel {
    protected String guildId;
    protected long guildIdLong;

    public GuildChannel(String id, String name, String guildId){
        super(id, name);
        this.guildId = guildId;
        this.guildIdLong = Helper.parseLong(guildId);
    }

    public Optional<String> getGuildId() {
        return Optional.ofNullable(guildId);
    }

    public Optional<Long> getGuildIdLong() {
        return Optional.of(guildIdLong);
    }
}
