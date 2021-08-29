package de.uwuwhatsthis.YeetsDiscordLibrary.events;

import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.objects.GatewayMessage;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import org.json.JSONObject;

public class RawEventManager {
    private final Debugger debugger;

    public RawEventManager(){
        debugger = new Debugger("RawEventManager");
    }

    public void triggerEventRaw(GatewayMessage message){
        debugger.debug("Recieved event: " + message.getEventName());

        switch (message.getEvent()){
            case READY:
                JSONObject data = (JSONObject) message.getMixedData();

                System.out.println(data.toString(2));
                break;

            case GUILD_CREATE:
                break;
        }
    }
}
