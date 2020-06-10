package ua.edu.lpnu.dsct.client.utilities;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class LogSecurityManager extends SecurityManager {
    private final Set<Permission> grantedPermissions;
    private final Logger logger;

    public LogSecurityManager() {
        this.grantedPermissions = new HashSet<Permission>();
        this.logger = Logger.getLogger(LogSecurityManager.class.getName());
    }

    @Override
    public void checkPermission(Permission perm) {
        if(!grantedPermissions.contains(perm)) {
            grantedPermissions.add(perm);
            logger.config("Granting permission: " + perm.toString());
        }
    }

    @Override
    public void checkPermission(Permission perm, Object obj) {
        if (!grantedPermissions.contains(perm)) {
            grantedPermissions.add(perm);
            logger.config("Granting permission: " + perm.toString() + " " + obj);
        }
    }
}
