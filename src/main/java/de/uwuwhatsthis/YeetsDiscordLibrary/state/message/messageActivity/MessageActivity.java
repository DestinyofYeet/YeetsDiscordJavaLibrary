package de.uwuwhatsthis.YeetsDiscordLibrary.state.message.messageActivity;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.message.messageActivity.activityType.MessageActivityType;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

public class MessageActivity {
    private String partyId;
    private int activityTypeInt;

    private MessageActivityType activityType;

    public MessageActivity(JSONObject data){
        partyId = Helper.getValueString(data, "party_id");
        activityType = MessageActivityType.getFromValue(activityTypeInt);
    }
}
