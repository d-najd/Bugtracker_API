package com.bugtracker.db.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bugtracker.db.roles.Roles_Global;

public class MyUserDetails implements UserDetails {

	private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.getActive();
        //this.authorities = user._getAuthorities();
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        
        if (user.getUsername().equals("admin")) {
        	auth.add(new SimpleGrantedAuthority(Roles_Global.r_owner));
        } else
        {
        	auth.add(new SimpleGrantedAuthority(Roles_Global.r_user));
        }
        this.authorities = auth; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    /**
     * @apiNote a role is assigned to the user as well
     * @param auths authorities that you want set to certain user
     */
    public void setAuthorities(List<GrantedAuthority> auths) {
    	authorities.clear();
    	for (GrantedAuthority authority : auths) {
    		authorities.add(authority);
    	}
    	//add the role
        if (getUsername().equals("admin")) {
        	authorities.add(new SimpleGrantedAuthority(Roles_Global.r_owner));
        } else
        {
        	authorities.add(new SimpleGrantedAuthority(Roles_Global.r_user));
        }
    }
    
    public void addAuthority(SimpleGrantedAuthority authority) {
    	authorities.add(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
