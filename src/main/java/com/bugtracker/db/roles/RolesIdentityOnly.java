package com.bugtracker.db.roles;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

public class RolesIdentityOnly extends Roles{
    @EmbeddedId
    private RolesIdentity rolesIdentity;
}
