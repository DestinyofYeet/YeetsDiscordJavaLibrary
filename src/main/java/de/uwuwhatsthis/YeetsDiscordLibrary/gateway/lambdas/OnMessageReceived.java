package de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas;

import com.neovisionaries.ws.client.WebSocket;

public interface OnMessageReceived {
    void execute(WebSocket ws, String message);
}
