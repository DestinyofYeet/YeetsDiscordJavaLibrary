package de.uwuwhatsthis.YeetsDiscordLibrary.events;

import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.objects.GatewayMessage;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.application.Application;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.user.SelfUser;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import org.json.JSONObject;

public class RawEventManager {
    private final Debugger debugger;

    public RawEventManager(){
        debugger = new Debugger("RawEventManager");
    }

    public void triggerEventRaw(GatewayMessage message){
        debugger.debug("Received event: " + message.getEventName());

        switch (message.getEvent()){
            case READY:
                onReadyRaw(message);
                break;

            case GUILD_CREATE:
                onGuildCreate(message);
                break;
        }
    }

    private void onReadyRaw(GatewayMessage message){
        JSONObject data = (JSONObject) message.getMixedData();

        message.getManager().setSessionID(data.getString("session_id"));
        StateManager stateManager = StateManager.getInstance();

        stateManager.setApplication(new Application(data.getJSONObject("application"), data.getJSONObject("user")));
    }

    private void onGuildCreate(GatewayMessage message){
        JSONObject data = (JSONObject) message.getMixedData();

        StateManager.getInstance().addGuild(new Guild(data));
    }
}
