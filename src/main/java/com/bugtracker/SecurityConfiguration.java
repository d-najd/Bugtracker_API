
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
    			.antMatchers(HttpMethod.GET, "/**").authenticated()
    			//anyone who has account can create project
            	.antMatchers(HttpMethod.POST, "/project/**").authenticated()
            	
            	
            	.antMatchers(HttpMethod.DELETE, "/project/**").hasAuthority("ROLE_manageProject")
            	.antMatchers(HttpMethod.PUT, "/project/**").hasAuthority("ROLE_manageProject")  
            	
            	.antMatchers(HttpMethod.POST, "/btj/**").hasAuthority("ROLE_create")
            	.antMatchers(HttpMethod.POST, "/boards/**").hasAuthority("ROLE_create")
            	.antMatchers(HttpMethod.POST, "/tasks/**").hasAuthority("ROLE_create")
            	.antMatchers(HttpMethod.POST, "/roadmaps/**").hasAuthority("ROLE_create")
            	
            	.antMatchers(HttpMethod.PUT, "/btj/**").hasAuthority("ROLE_edit")
            	.antMatchers(HttpMethod.PUT, "/boards/**").hasAuthority("ROLE_edit")
            	.antMatchers(HttpMethod.PUT, "/tasks/**").hasAuthority("ROLE_edit")
            	.antMatchers(HttpMethod.PUT, "/roadmaps/**").hasAuthority("ROLE_edit")
            	
            	.antMatchers(HttpMethod.DELETE, "/btj/**").hasAuthority("ROLE_delete")
            	.antMatchers(HttpMethod.DELETE, "/boards/**").hasAuthority("ROLE_delete")
            	.antMatchers(HttpMethod.DELETE, "/tasks/**").hasAuthority("ROLE_delete")
            	.antMatchers(HttpMethod.DELETE, "/roadmaps/**").hasAuthority("ROLE_delete") 	
            	
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

