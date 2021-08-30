package de.uwuwhatsthis.YeetsDiscordLibrary.state.user;

import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

public class SelfUser extends User{
    private boolean isEmailVerified;
    private int flags;
    private boolean is2faEnabled;
    private String email;

    public SelfUser(JSONObject data) {
        super(data);

        isEmailVerified = data.getBoolean("verified");
        flags = data.getInt("flags");
        is2faEnabled = data.getBoolean("mfa_enabled");
        email = Helper.getValueString(data, "email");
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public int getFlags() {
        return flags;
    }

    public boolean isIs2faEnabled() {
        return is2faEnabled;
    }

    public String getEmail() {
        return email;
    }
}
