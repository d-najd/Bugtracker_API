
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
        http.httpBasic().and() //needed for postman
        	.authorizeRequests()
        		//owner NOTE owner is only given to the devs for testing purpuses
            	//everyone is allowed to create new user
        	
    			.antMatchers(HttpMethod.POST, "/users/**").permitAll()
    			
    			//anyone who belongs to the project is allowed to get the project data
    			.antMatchers(HttpMethod.GET, "/**")
    				.hasAnyAuthority("ROLE_user", "ROLE_owner")
    			//anyone who has account can create project
            	.antMatchers(HttpMethod.POST, "/project/**")
            		.hasAnyAuthority("ROLE_user", "ROLE_owner")
            	
            	
            	.antMatchers(HttpMethod.DELETE, "/project/**")
            		.hasAnyAuthority("manage_project_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.PUT, "/project/**")
        			.hasAnyAuthority("manage_project_AUTH", "ROLE_owner")
            	
            	.antMatchers(HttpMethod.POST, "/btj/**")
    				.hasAnyAuthority("create_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.POST, "/boards/**")
            		.hasAnyAuthority("create_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.POST, "/tasks/**")
            		.hasAnyAuthority("create_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.POST, "/roadmaps/**")
            		.hasAnyAuthority("create_AUTH", "ROLE_owner")
            	
            	.antMatchers(HttpMethod.PUT, "/btj/**")
            		.hasAnyAuthority("edit_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.PUT, "/boards/**")
            		.hasAnyAuthority("edit_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.PUT, "/tasks/**")
            		.hasAnyAuthority("edit_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.PUT, "/roadmaps/**")
            		.hasAnyAuthority("edit_AUTH", "ROLE_owner")
            	
            	.antMatchers(HttpMethod.DELETE, "/btj/**")
            		.hasAnyAuthority("delete_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.DELETE, "/boards/**")            	
            		.hasAnyAuthority("delete_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.DELETE, "/tasks/**")
            		.hasAnyAuthority("delete_AUTH", "ROLE_owner")
            	.antMatchers(HttpMethod.DELETE, "/roadmaps/**")
            		.hasAnyAuthority("delete_AUTH", "ROLE_owner")	
            	
				.antMatchers("/**").hasAuthority("ROLE_owner")
				.antMatchers(HttpMethod.DELETE, "/**").hasAuthority("ROLE_owner")
				.antMatchers(HttpMethod.PUT, "/**").hasAuthority("ROLE_owner")
				.antMatchers(HttpMethod.POST, "/**").hasAuthority("ROLE_owner")
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

