package de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.requests;

import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.genericRequest.GenericHttpRequest;
import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.requestCallback.RequestCallback;
import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.requestType.RequestType;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import org.json.JSONObject;

public class RegisterSlashCommandRequest extends GenericHttpRequest {

    public RegisterSlashCommandRequest(String guildId, JSONObject data, RequestCallback callback) {
        super("/applications/" + StateManager.getInstance().getApplication().getId() +
                (guildId != null ? "/guilds/" + guildId + "/commands" : "/commands") ,
                data, RequestType.POST, callback);
    }
}
