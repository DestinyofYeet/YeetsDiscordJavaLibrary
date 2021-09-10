package de.uwuwhatsthis.YeetsDiscordLibrary.events;

import de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents.OnGuildJoinEvent;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents.OnGuildLeaveEvent;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents.OnGuildMessageEvent;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.OnMessageEvent;

public abstract class EventListener {

    public void onReady(){}

    public void onGuildMessage(OnGuildMessageEvent event){}

    public void onMessage(OnMessageEvent event){}

    public void onGuildJoin(OnGuildJoinEvent event){}

    public void onGuildLeave(OnGuildLeaveEvent event){}
}
