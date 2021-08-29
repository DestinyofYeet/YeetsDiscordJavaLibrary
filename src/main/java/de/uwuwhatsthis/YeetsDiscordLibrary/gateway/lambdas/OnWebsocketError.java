package de.uwuwhatsthis.YeetsDiscordLibrary.gateway.lambdas;

import com.neovisionaries.ws.client.WebSocketException;

public interface OnWebsocketError {

    void onWebsocketError(WebSocketException cause);
}
