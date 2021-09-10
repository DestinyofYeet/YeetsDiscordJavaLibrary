package de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint;

import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.genericRequest.GenericHttpRequest;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class HttpEndpointManager {

    private boolean running, shouldShutdown;
    private final Queue<GenericHttpRequest> requestQueue;
    private final static Debugger debugger = new Debugger("HttpEndpointManager");

    public HttpEndpointManager(){
        running = true;
        shouldShutdown = false;
        requestQueue = new LinkedBlockingQueue<>();
    }

    public void start(){
        StateManager.getInstance().getExecutorService().submit(() -> {
           while (running){
                if (shouldShutdown){
                    // shutdown signal received
                    if (!requestQueue.isEmpty()){
                        debugger.debug(requestQueue.size() + " items in queue left... not shutting down");
                    } else {
                        // no items left
                        return;
                    }
                }

                GenericHttpRequest request = requestQueue.poll();

                if (request == null) continue;

                request.doRequest();
           }
        });
    }

    public void startShutdown(){
        shouldShutdown = true;
    }
}
