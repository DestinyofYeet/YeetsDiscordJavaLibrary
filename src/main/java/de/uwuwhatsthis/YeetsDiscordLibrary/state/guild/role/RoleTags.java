package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.role;

import org.json.JSONObject;

public class RoleTags {
    /*
    botId = the id of the bot this role belongs to
    integrationId = the id of the integration this role belongs to
    premiumSubscriber = whether this is the guild's premium subscriber role
     */
    private String botId, integrationId, premiumSubscriber;

    public RoleTags(JSONObject data){
        if (data.has("bot_id"))
            botId = data.getString("bot_id");

        if (data.has("integration_id")){
            integrationId = data.getString("integration_id");
        }

        if (data.has("premium_subscriber")){
            premiumSubscriber = data.getString("premium_subscriber");
        }
    }
}
