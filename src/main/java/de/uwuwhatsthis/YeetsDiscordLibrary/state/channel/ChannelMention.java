package de.uwuwhatsthis.YeetsDiscordLibrary.state.channel;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class ChannelMention {
    private String id, guildId, name;
    private int typeInt;
    private Long idLong, guildIdLong;

    private ChannelType type;
    private Channel channel;
    private Guild guild;

    public ChannelMention(JSONObject data){
        id = Helper.getValueString(data, "id");
        guildId = Helper.getValueString(data, "guild_id");
        name = Helper.getValueString(data, "name");

        typeInt = Helper.getValueInt(data, "type");

        idLong = Helper.parseLong(id);
        guildIdLong = Helper.parseLong(guildId);

        type = ChannelType.getFromValue(typeInt);
        guild = StateManager.getInstance().getGuildById(guildId).orElse(null);

        if (guild != null){
            guild.getChannelById(id).map(channel1 -> channel);
        }
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getGuildId() {
        return Optional.ofNullable(guildId);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> getTypeInt() {
        return Optional.ofNullable(typeInt);
    }

    public Optional<Long> getIdLong() {
        return Optional.ofNullable(idLong);
    }

    public Optional<Long> getGuildIdLong() {
        return Optional.ofNullable(guildIdLong);
    }

    public Optional<ChannelType> getType() {
        return Optional.ofNullable(type);
    }

    public Optional<Channel> getChannel() {
        return Optional.ofNullable(channel);
    }

    public Optional<Guild> getGuild() {
        return Optional.ofNullable(guild);
    }
}
