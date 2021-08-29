package de.uwuwhatsthis.YeetsDiscordLibrary.gateway;

import de.uwuwhatsthis.YeetsDiscordLibrary.events.RawEventManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas.OnWebsocketError;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.objects.GatewayMessage;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.websocket.WebsocketConnection;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

public class GatewayManager implements Runnable {

    private final static String GATEWAY = "wss://gateway.discord.gg/?v=9&encoding=json";
    private final String token;

    private String sessionID;

    private final Debugger debugger;

    private WebsocketConnection websocketConnection;

    private final RawEventManager rawEventManager;

    private boolean couldConnect, couldAuthenticate, running;

    private boolean receivedHeartBeatAck;

    private int HEARTBEAT_INTERVAL, sequence;

    private Thread thread;

    public GatewayManager(String token, RawEventManager rawEventManager){
        this.token = token;
        this.debugger = new Debugger("GatewayConnection");
        this.rawEventManager = rawEventManager;

        receivedHeartBeatAck = true;
        running = true;

        sequence = 0;

        couldConnect = false;

        thread = new Thread(this);
    }

    @Override
    public void run(){
        // thread to send heartbeats
        Random random = new Random();

        while (running){
            sendHeartbeat();

            long calculatedSleep = HEARTBEAT_INTERVAL;
            if (sequence == 0){
                // Used for the first connection
                calculatedSleep = (long) (HEARTBEAT_INTERVAL * random.nextFloat());
            }

//            debugger.debug("Sleeping for " + calculatedSleep);

            try{
                Thread.sleep(calculatedSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        debugger.debug("Heartbeat thread is exiting!");
    }

    private void onGatewayMessage(GatewayMessage message){
        debugger.debug("Received op code: " + message.getOpCode());

        switch (message.getOpCode()){
            case 0:
                sequence = message.getSequence();
                rawEventManager.triggerEventRaw(message);
                // some event
                break;

            case 1:
                /*
                the gateway may request a heartbeat from the client in some situations by sending an Opcode 1 Heartbeat.
                When this occurs, the client should immediately send an Opcode 1 Heartbeat without waiting the remainder of the current interval.
                 */
                sendHeartbeat();
                break;

            case 7:
                // reconnect & resume request
                websocketConnection.disconnect();
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

                if (!thread.isAlive()){
                    thread.start();
                    debugger.debug("Started heartbeat thread");
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
                onGatewayMessage(new GatewayMessage(message, ws, this));
            })), websocketError);
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            debugger.error("Could not connect to gateway!");
        }
        return false;
    }

    private void authenticate(){
        JSONObject json = new JSONObject()
                .put("op", 2)
                .put("d", new JSONObject()
                        .put("token", token)
                        .put("intents", 513)
                        .put("properties", new JSONObject()
                                .put("$os", "linux")
                                .put("$browser", "YeetsDiscordLibrary")
                                .put("$device", "YeetsDiscordLibrary")));

        websocketConnection.sendText(json.toString());
    }

    public void sendHeartbeat(){
        if (!receivedHeartBeatAck){
            // we didn't receive a confirmation ack of our heartbeat -> attempt to resume
            resume();
            return;
        }

        receivedHeartBeatAck = false;
        JSONObject json = new JSONObject();
        json.put("op", 1);
        json.put("d", sequence != 0 ? sequence: "null");

//        debugger.debug("Sent heartbeat");
        System.out.println(json.toString(2));
        websocketConnection.sendText(json.toString(2));
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

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
