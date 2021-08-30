package de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.permissions;

import de.uwuwhatsthis.YeetsDiscordLibrary.state.channel.ChannelType;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONObject;

import java.util.EnumSet;

public class PermissionOverwrite {
    private String id, allow, deny;
    private int typeInt;
    private EnumSet<Permission> allowedPermissions, deniedPermissions;
    private PermissionType permissionType;

    public PermissionOverwrite(JSONObject data){
        id = Helper.getValueString(data, "id");
        allow = Helper.getValueString(data, "allow");
        deny = Helper.getValueString(data, "deny");

        typeInt = Helper.getValueInt(data, "type");

        allowedPermissions = Permission.getPermissions(Helper.parseLong(allow));
        deniedPermissions = Permission.getPermissions(Helper.parseLong(deny));
        permissionType = PermissionType.getFromValue(typeInt);
    }

    public String getId() {
        return id;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public EnumSet<Permission> getAllowedPermissions() {
        return allowedPermissions;
    }

    public EnumSet<Permission> getDeniedPermissions() {
        return deniedPermissions;
    }
}
