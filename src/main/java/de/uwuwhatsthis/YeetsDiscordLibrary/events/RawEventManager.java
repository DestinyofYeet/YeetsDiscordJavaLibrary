package de.uwuwhatsthis.YeetsDiscordLibrary.events;

import de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.OnMessageEvent;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents.OnGuildJoinEvent;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.eventClasses.guildEvents.OnGuildMessageEvent;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.objects.GatewayMessage;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.application.SelfApplication;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

public class RawEventManager {
    private final Debugger debugger;
    private final StateManager stateManager;

    public RawEventManager(){
        debugger = new Debugger("RawEventManager");
        stateManager = StateManager.getInstance();
    }

    public void triggerEventRaw(GatewayMessage message){
        debugger.debug("Received event: " + message.getEventName());

        switch (message.getEvent()){
            case READY:
                onReadyRaw(message);
                break;

            case MESSAGE_CREATE:
                onMessage(message);
                break;

            case GUILD_CREATE:
                onGuildCreate(message);
                break;
        }
    }

    private void onReadyRaw(GatewayMessage message){
        JSONObject data = (JSONObject) message.getMixedData();

        message.getManager().setSessionID(Helper.getValueString(data, "session_id"));

        if (data.has("guilds")){
            data.getJSONArray("guilds").forEach(rawData -> {
                JSONObject guildData = (JSONObject) rawData;

                stateManager.addGuild(new Guild(guildData));
            });
        }

        stateManager.setApplication(new SelfApplication(data.getJSONObject("application"), data.getJSONObject("user")));

        StateManager.getInstance().registerSlashCommands();
    }

    private void onGuildCreate(GatewayMessage message){
        JSONObject data = (JSONObject) message.getMixedData();

        Guild newGuild = new Guild(data);

        newGuild.getGuildId().ifPresent(id -> {
            if (!stateManager.getGuildById(id).isPresent()){
                // guild is not in the cache, call the userModifiable onGuildJoin
                stateManager.getEventListeners().forEach(eventListener -> {
                    eventListener.onGuildJoin(new OnGuildJoinEvent(newGuild));
                });
            }
        });

        stateManager.addGuild(newGuild);
    }

    private void onGuildDelete(GatewayMessage message){

    }

    private void onMessage(GatewayMessage message){
        JSONObject data = (JSONObject) message.getMixedData();
        // System.out.println(data.toString(2));

        if (data.has("guild_id")){
            StateManager.getInstance().getEventListeners().forEach(eventListener -> {
                debugger.debug("triggered onGuildMessage");
                eventListener.onGuildMessage(new OnGuildMessageEvent(data));
            });
        } else {
            stateManager.getEventListeners().forEach(eventListener -> {
                debugger.debug("triggered onMessage");
                eventListener.onMessage(new OnMessageEvent(data));
            });
        }
    }
}
