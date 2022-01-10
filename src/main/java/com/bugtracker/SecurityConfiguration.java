
package com.bugtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bugtracker.db.roles.Roles_Global;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    UserDetailsService userDetailsService;
    
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;    
    
    @Autowired

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("admin").password("password");
    	auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
		//the check if the user is allowed to view the project is inside the project
        http.csrf().disable().httpBasic().and() //needed for postman
        	.authorizeRequests()
        		//owner NOTE owner is only given to the devs for testing purpuses
            	//everyone is allowed to create new user
        		
        	
    			.antMatchers(HttpMethod.GET, "/**").hasAuthority(Roles_Global.r_owner)
    			.antMatchers(HttpMethod.DELETE, "/**").hasAuthority(Roles_Global.r_owner)
    			.antMatchers(HttpMethod.PUT, "/**").hasAuthority(Roles_Global.r_owner)
    			.antMatchers(HttpMethod.POST, "/**").hasAuthority(Roles_Global.r_owner)
        	
        	
    			.antMatchers(HttpMethod.POST, "/users/**").permitAll()
    			
    			//anyone who belongs to the project is allowed to get the project data
    			.antMatchers(HttpMethod.GET, "/**")
    				.hasAnyAuthority(Roles_Global.r_user, Roles_Global.r_owner)
       
    				
    			.antMatchers(HttpMethod.GET, "/project/**")
    				.hasAnyAuthority(Roles_Global.r_user, Roles_Global.r_owner)
    				
    				
                .antMatchers(HttpMethod.POST, "/project/**")
                	.hasAnyAuthority(Roles_Global.r_user, Roles_Global.r_owner)
    			.antMatchers(HttpMethod.POST, "/btj/**")
    				.hasAnyAuthority(Roles_Global.a_create, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.POST, "/boards/**")
            		.hasAnyAuthority(Roles_Global.a_create, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.POST, "/tasks/**")
            		.hasAnyAuthority(Roles_Global.a_create, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.POST, "/roadmaps/**")
            		.hasAnyAuthority(Roles_Global.a_create, Roles_Global.r_owner)


                .antMatchers(HttpMethod.PUT, "/project/**")
                	.hasAnyAuthority(Roles_Global.a_manage_project, Roles_Global.r_owner, Roles_Global.r_user)
            	.antMatchers(HttpMethod.PUT, "/btj/**")
            		.hasAnyAuthority(Roles_Global.a_edit, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.PUT, "/boards/**")
            		.hasAnyAuthority(Roles_Global.a_edit, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.PUT, "/tasks/**")
            		.hasAnyAuthority(Roles_Global.a_edit, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.PUT, "/roadmaps/**")
            		.hasAnyAuthority(Roles_Global.a_edit, Roles_Global.r_owner)

                .antMatchers(HttpMethod.DELETE, "/project/**")
                	.hasAnyAuthority(Roles_Global.r_user, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.DELETE, "/btj/**")
            		.hasAnyAuthority(Roles_Global.a_delete, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.DELETE, "/boards/**")            	
            		.hasAnyAuthority(Roles_Global.a_delete, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.DELETE, "/tasks/**")
            		.hasAnyAuthority(Roles_Global.a_delete, Roles_Global.r_owner)
            	.antMatchers(HttpMethod.DELETE, "/roadmaps/**")
            		.hasAnyAuthority(Roles_Global.a_delete, Roles_Global.r_owner)	
            .and()
            .formLogin().permitAll()
            .and()
            .logout().logoutSuccessHandler(logoutSuccessHandler)
            .permitAll();
        
        http.csrf().disable(); //welp sadly this had to be disabled, NOTE DON'T MOVE THIS UP OR EVERYTHING WILL BREAK

    }

    @SuppressWarnings("deprecation")
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

