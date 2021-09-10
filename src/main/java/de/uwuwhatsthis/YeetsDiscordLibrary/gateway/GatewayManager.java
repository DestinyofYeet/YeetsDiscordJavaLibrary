package de.uwuwhatsthis.YeetsDiscordLibrary.gateway;

import de.uwuwhatsthis.YeetsDiscordLibrary.events.RawEventManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.hearbeater.Heartbeater;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.intents.Intent;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas.OnWebsocketError;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.objects.GatewayMessage;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.websocket.WebsocketConnection;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GatewayManager {

    private final static String GATEWAY = "wss://gateway.discord.gg/?v=9&encoding=json";
    private final String token;

    private String sessionID;

    private final Debugger debugger;

    private WebsocketConnection websocketConnection;

    private final RawEventManager rawEventManager;

    private boolean couldConnect, couldAuthenticate, running;

    private boolean receivedHeartBeatAck;

    private int HEARTBEAT_INTERVAL, sequence;

    private List<Intent> intentList;
    private List<Long> intentListLong;

    private Heartbeater heartbeater;

    public GatewayManager(String token){
        this.token = token;
        this.debugger = new Debugger("GatewayConnection");
        this.rawEventManager = new RawEventManager();

        receivedHeartBeatAck = true;
        running = true;

        sequence = 0;

        intentList = new ArrayList<>();
        intentListLong = new ArrayList<>();

        couldConnect = false;

        heartbeater = null;
    }

    private void onGatewayMessage(GatewayMessage message){
        debugger.debug("Received op code: " + message.getOpCode());

        switch (message.getOpCode()){
            case 0:
                couldAuthenticate = true;
                sequence = message.getSequence();
                rawEventManager.triggerEventRaw(message);
                // some event
                break;

            case 1:
                /*
                the gateway may request a heartbeat from the client in some situations by sending an Opcode 1 Heartbeat.
                When this occurs, the client should immediately send an Opcode 1 Heartbeat without waiting the remainder of the current interval.
                 */
                heartbeater.sendHeartbeat();
                break;

            case 7:
                // reconnect & resume request
                websocketConnection.disconnect();
                debugger.info("Received code 7 -> Will attempt to resume");
                resume();
                break;

            case 9:
                // invalid session
                websocketConnection.disconnect();
                reconnect();
                break;

            case 10:
                // we receive some data after connecting to the gateway

                JSONObject mixedData = (JSONObject) message.getMixedData();
                HEARTBEAT_INTERVAL = mixedData.getInt("heartbeat_interval");

                websocketConnection.waitTillConnected();

                if (heartbeater == null){
                    heartbeater = new Heartbeater(this);
                    heartbeater.setInterval(HEARTBEAT_INTERVAL);
                    heartbeater.start();
                }

                authenticate();
                break;

            case 11:
                /*
                Confirmation of sending the heartbeat
                */
                receivedHeartBeatAck = true;
                break;

            default:
                debugger.debug("No opcode found for: " + message.getOpCode());
        }
    }

    public void connect(OnWebsocketError websocketError) {
        couldConnect = connectToGatewayCaught(websocketError);
    }

    private boolean connectToGatewayCaught(OnWebsocketError websocketError){
        if (websocketConnection != null){
            websocketConnection.disconnect();
        }

        try {
            websocketConnection = new WebsocketConnection(new URI(GATEWAY), this, (((ws, message) -> {
                GatewayMessage gatewayMessage = new GatewayMessage(message, ws, this);
                onGatewayMessage(gatewayMessage);
            })), websocketError);
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            debugger.error("Could not connect to gateway!");
        }
        return false;
    }

    private void authenticate(){
        int intents = 0;

        if (!intentListLong.isEmpty()){
            for (Long aLong : intentListLong) {
                intents += aLong;
            }
        } else {
            for (Intent intentIterator : intentList){
                intents += intentIterator.getRaw();
            }
        }

        debugger.debug("Authenticating with intents " + intents);

        JSONObject json = new JSONObject()
                .put("op", 2)
                .put("d", new JSONObject()
                        .put("token", token)
                        .put("intents", intents)
                        .put("properties", new JSONObject()
                                .put("$os", "linux")
                                .put("$browser", "YeetsDiscordLibrary")
                                .put("$device", "YeetsDiscordLibrary")));

        websocketConnection.sendText(json.toString());
    }

    /**
     * Will attempt to reconnect and resume
     */
    public void resume(){
        debugger.info("Attempting to resume...");
        if (sessionID == null){
            // we have not connected successfully yet, reconnecting instead
            reconnect();
            return;
        }

        couldConnect = connectToGatewayCaught(null);

        if (!couldConnect){
            return;
        }

        JSONObject json = new JSONObject()
                .put("op", 6)
                .put("d", new JSONObject()
                        .put("token", token)
                        .put("session_id", sessionID)
                        .put("seq", sequence));

        websocketConnection.sendText(json.toString());
    }

    public void reconnect(){
        debugger.debug("Attempting to reconnect...");
        couldConnect = connectToGatewayCaught(null);

        if (!couldConnect) return;

        authenticate();
    }

    public void stop(){
        websocketConnection.disconnect(1000);
        running = false;
    }

    public void addIntent(Intent intent) {
        if (intentList.contains(intent)){
            debugger.error("The intent " + intent + " has already been added!");
            return;
        }

        intentList.add(intent);
    }

    public void addIntent(long intent){
        intentListLong.add(intent);
    }

    public void addIntents(Intent... intents){
        intentList.addAll(Arrays.asList(intents));
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public boolean receivedHeartBeatAck() {
        return receivedHeartBeatAck;
    }

    public void setReceivedHeartBeatAck(boolean receivedHeartBeatAck) {
        this.receivedHeartBeatAck = receivedHeartBeatAck;
    }

    public int getSequence() {
        return sequence;
    }

    public WebsocketConnection getWebsocketConnection() {
        return websocketConnection;
    }

    public Debugger getDebugger() {
        return debugger;
    }

    public RawEventManager getRawEventManager() {
        return rawEventManager;
    }

    public boolean couldConnect() {
        return couldConnect;
    }

    public boolean couldAuthenticate() {
        return couldAuthenticate;
    }

    public List<Intent> getIntentList() {
        return intentList;
    }

    public List<Long> getIntentListLong() {
        return intentListLong;
    }

    public Heartbeater getHeartbeater() {
        return heartbeater;
    }
}
