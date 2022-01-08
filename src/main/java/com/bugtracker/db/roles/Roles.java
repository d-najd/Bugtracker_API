package com.bugtracker.db.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bugtracker.db.btj.BtjIdentity;

@Entity
@Table(name = "roles")
public class Roles {
    @EmbeddedId
    private RolesIdentity rolesIdentity;
    @Column(name = "ROLE_manage_project")
    private Boolean manageProject;
    @Column(name = "ROLE_manage_users")
    private Boolean manageUsers;
    @Column(name = "ROLE_create")
    private Boolean create;
    @Column(name = "ROLE_edit")
    private Boolean edit;
    @Column(name = "ROLE_delete")
    private Boolean delete;
    
	public Roles() {
		super();
	}
	
	public RolesIdentity getRolesIdentity() {
		return rolesIdentity;
	}

	public Boolean getManageProject() {
		return manageProject;
	}
	public Boolean getManageUsers() {
		return manageUsers;
	}
	public Boolean getCreate() {
		return create;
	}
	public Boolean getEdit() {
		return edit;
	}
	public Boolean getDelete() {
		return delete;
	}
	
	
	/**
	 * @apiNote use the getAuthorities from the User object since using it from here won't check if there have been added any extra authorities to the database
	 * @return list of granted authorities
	 */
	public List<GrantedAuthority> _getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		Map<String, Boolean> authoritiesMap = mapAuthorities();
		
	    for (String i : authoritiesMap.keySet()) {
	    	if (authoritiesMap.get(i))
	    	{
	    		authorities.add(new SimpleGrantedAuthority(i));
		    	System.out.print("has authority " + i + authoritiesMap.get(i) + "\n\n");
	    	}
	    }   
	    return authorities;
	}
	
	private Map<String, Boolean> mapAuthorities() {
		Map<String, Boolean> map = new HashMap<>();
		map.put("manage_project_AUTHORITY", manageProject);
		map.put("manage_users_AUTHORITY", manageUsers);
		map.put("create_AUTHORITY", create);
		map.put("edit_AUTHORITY", edit);
		map.put("delete_AUTHORITY", delete);
		return map;
	}
}
