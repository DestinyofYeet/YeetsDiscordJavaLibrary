package de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents;

import de.uwuwhatsthis.YeetsDiscordLibrary.main.Bot;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;

public class OnGuildLeaveEvent extends GenericGuildEvent{

    public OnGuildLeaveEvent(Guild guild){
        super(guild);
    }
}
