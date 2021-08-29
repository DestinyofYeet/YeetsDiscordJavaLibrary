package de.uwuwhatsthis.YeetsDiscordLibrary.gateway.objects;

import com.neovisionaries.ws.client.WebSocket;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.Event;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.GatewayManager;
import org.json.JSONObject;

public class GatewayMessage {
    private final JSONObject json;
    private final int opCode;
    private final int sequence;
    private final String eventName;
    private final Object mixedData;
    private final WebSocket webSocket;
    private final GatewayManager manager;
    private final Event event;


    public GatewayMessage(String message, WebSocket webSocket, GatewayManager manager) {
        this.webSocket = webSocket;
        this.manager = manager;

        json = new JSONObject(message);
        opCode = json.get("op").equals(JSONObject.NULL) ? -1: json.getInt("op");
        sequence = json.get("s").equals(JSONObject.NULL) ? -1 : json.getInt("s");
        eventName = json.get("t").equals(JSONObject.NULL) ? null : json.getString("t");

        if (eventName == null){
            event = null;
        } else {
            event = Event.valueOf(eventName);
        }

        if (json.get("d") instanceof Boolean) {
            mixedData = json.getBoolean("d");
        } else {
            mixedData = json.get("d").equals(JSONObject.NULL) ? new JSONObject() : json.getJSONObject("d");
        }

    }

    public JSONObject getJson() {
        return json;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getSequence() {
        return sequence;
    }

    public String getEventName() {
        return eventName;
    }

    public Object getMixedData() {
        return mixedData;
    }

    public GatewayManager getManager() {
        return manager;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public Event getEvent() {
        return event;
    }
}
