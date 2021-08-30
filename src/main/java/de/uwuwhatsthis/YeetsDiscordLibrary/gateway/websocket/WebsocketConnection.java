package de.uwuwhatsthis.YeetsDiscordLibrary.gateway.websocket;

import com.neovisionaries.ws.client.*;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.GatewayManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas.OnMessageReceived;
import de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas.OnWebsocketError;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class WebsocketConnection{
    private WebSocket webSocket;
    private final URI uriToConnect;
    private final OnMessageReceived onMessageReceived;
    private final OnWebsocketError onWebsocketError;

    private final Debugger debugger;

    private boolean hasConnected;

    private GatewayManager gatewayManager;

    public WebsocketConnection(URI uriToConnect, GatewayManager gatewayManager, OnMessageReceived onMessageReceived, OnWebsocketError onWebsocketError){
        this.uriToConnect = uriToConnect;
        this.onMessageReceived = onMessageReceived;
        this.onWebsocketError = onWebsocketError;
        this.gatewayManager = gatewayManager;

        debugger = new Debugger("WebsocketConnection");
        hasConnected = false;

        run();
    }

    public void run() {
        try {

            webSocket = new WebSocketFactory()
                    .createSocket(uriToConnect)
                    .addListener(new WebSocketAdapter(){
                        @Override
                        public void onTextMessage(WebSocket ws, String message){
                            onMessageReceived.execute(ws, message);
                        }

                        @Override
                        public void onConnected(WebSocket websocket, Map<String, List<String>> headers){
                            debugger.info("Connected to gateway");
                            hasConnected = true;
                        }

                        @Override
                        public void onError(WebSocket websocket, WebSocketException cause){
                            if (onWebsocketError != null){
                                StateManager.getInstance().shutdown();
                                onWebsocketError.onWebsocketError(cause);
                                return;
                            }

                            debugger.error("An error occurred: " + cause.getError());
                            cause.printStackTrace();
                        }

                        @Override
                        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer){
                            debugger.debug("Connection closed: By server? " + (closedByServer ? "yes": "no") +
                                    "\nServer close code: " + serverCloseFrame.getCloseCode() +
                                    "\nServer close reason: " + serverCloseFrame.getCloseReason());

                            if (closedByServer){
                                debugger.info("Lost connection -> Will attempt to resume");
                                gatewayManager.resume();
                            }

                        }
                    })
                    .connectAsynchronously();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitTillConnected(){
        while(!webSocket.isOpen()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendText(String message){
        webSocket.sendText(message);
    }

    public void disconnect(){
        webSocket.disconnect();
    }

    public void disconnect(int code){
        webSocket.disconnect(code);
    }
    public void disconnect(String reason){
        webSocket.disconnect(reason);
    }
    public void disconnect(int code, String reason){
        webSocket.disconnect(code, reason);
    }


    public boolean hasConnected() {
        return hasConnected;
    }
}
