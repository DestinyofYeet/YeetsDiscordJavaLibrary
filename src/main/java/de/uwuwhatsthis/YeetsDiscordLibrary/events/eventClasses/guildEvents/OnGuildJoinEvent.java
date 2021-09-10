package de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents;

import de.uwuwhatsthis.YeetsDiscordLibrary.main.Bot;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;

import java.util.Optional;

public class OnGuildJoinEvent extends GenericGuildEvent{

    public OnGuildJoinEvent(Guild guild){
        super(guild);
    }
}
