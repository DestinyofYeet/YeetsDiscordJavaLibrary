package de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.Message;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.Optional;

public class OnGuildMessageEvent extends GenericGuildEvent {
    private Message message;

    public OnGuildMessageEvent(JSONObject data) {
        super(StateManager.getInstance().getGuildById(Helper.getValueString(data, "guild_id")).orElse(null));
        message = new Message(data);
    }

    public Optional<Message> getMessage() {
        return Optional.ofNullable(message);
    }
}
