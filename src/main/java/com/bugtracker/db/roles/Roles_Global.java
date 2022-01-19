package com.bugtracker.db.roles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import com.bugtracker.db.project.Project;
import com.bugtracker.db.project.ProjectRepository;
import com.bugtracker.db.user.MyUserDetails;

public class Roles_Global {
	public static String r_owner = "ROLE_owner";
	public static String r_user = "ROLE_user";
	
	public static String a_manage_project = "manage_project_AUTHORITY";
	public static String a_manage_users = "manage_users_AUTHORITY";
	public static String a_create = "create_AUTHORITY";
	public static String a_edit = "edit_AUTHORITY";
	public static String a_delete = "delete_AUTHORITY";

	/**
	 * @apiNote checks if the user has authority to access an url
	 * @param userDetails the user details
	 * @param projectId projectId that you want to check if the user has the authority to access
	 * @param authoritiy if the user has the authority he will get access, if the authority is null it won't be checked against and return true
	 * @return true if the user is allowed access false if not 
	 */
	
    public static Boolean hasAuthorities(
    		@NonNull MyUserDetails userDetails,
    		@NonNull Integer projectId,
    		GrantedAuthority authority,
    		@NonNull RolesRepository rolesRepository,
    		@NonNull ProjectRepository projectRepository)
    {
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	if (authority != null)
    		authorities.add(authority);
    	if (hasAuthorities(userDetails, projectId, authorities, rolesRepository, projectRepository))
    		return true;
    	else {
			return false;
		}
    }
    

	/**
	 * @apiNote checks if the user has authorities to access an url
	 * @param userDetails the user details
	 * @param projectId projectId that you want to check if the user has the authorities to access
	 * @param authorities if user has any of the authorities it will allow access, if authorities are null or the size is 0 it won't check for authorities
	 * @return true if the user is allowed access false if not 
	 */
    public static Boolean hasAuthorities(@NonNull MyUserDetails userDetails,
    									@NonNull Integer projectId,
    									List<GrantedAuthority> authorities,
    									@NonNull RolesRepository rolesRepository,
    									@NonNull ProjectRepository projectRepository)
    
    { 
    	if (isOwner(userDetails, projectId, projectRepository))
    		return true;
    	
    	Roles roles = rolesRepository.findByRolesIdentity(new RolesIdentity(userDetails.getUsername(), projectId));
    	if (roles == null) {
    		return false;
    	} 
    	else {
    		userDetails.setAuthorities(roles._getAuthorities());
    		if (authorities == null || authorities.size() == 0)	
    			return true;
    		
    		for (GrantedAuthority authority : authorities) {
    			if (userDetails.getAuthorities().contains(authority))
    				return true;
    		}
    		
    		return false;
    	}   
    }
    
    
    public static Boolean isOwner(
    		@NonNull MyUserDetails userDetails,
    		@NonNull Integer projectId,
    		@NonNull ProjectRepository projectRepository) {
    	Project project = projectRepository.getById(projectId);
    	if (project == null) {
    		return false;
    	}
    	else if (project.getOwnerId().equals(userDetails.getUsername())) {
    		return true;
    	}
    	return false;
    }

}
