
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    	//NOTE ALL OF THE AUTHORITY CHECKING IS DONE INSIDE THE REQUEST METHOD
    	
    	
		//the check if the user is allowed to view the project is inside the project
        http.csrf().disable().httpBasic().and() //needed for postman
        	.authorizeRequests()
        		//owner NOTE owner is only given to the devs for testing purpuses
            	//everyone is allowed to create new user        	
        		.antMatchers(HttpMethod.GET, "/users/test/test").permitAll()
				.antMatchers(HttpMethod.POST, "/users").permitAll()
				.antMatchers(HttpMethod.GET, "/users").authenticated()
				.antMatchers(HttpMethod.GET, "/users/**").authenticated()
    			
    			//anyone who belongs to the project is allowed to get the project data       
    				
    			.antMatchers(HttpMethod.GET, "/project/**").authenticated()
    			.antMatchers(HttpMethod.GET, "/btj/**").authenticated()
    			.antMatchers(HttpMethod.GET, "/boards/**").authenticated()
    			.antMatchers(HttpMethod.GET, "/tasks/**").authenticated()
    			.antMatchers(HttpMethod.GET, "/roadmaps/**").authenticated()
    			.antMatchers(HttpMethod.GET, "/roles/**").authenticated()
    			
                .antMatchers(HttpMethod.POST, "/project/**").authenticated()
    			.antMatchers(HttpMethod.POST, "/btj/**").authenticated()
            	.antMatchers(HttpMethod.POST, "/boards/**").authenticated()
            	.antMatchers(HttpMethod.POST, "/tasks/**").authenticated()
            	.antMatchers(HttpMethod.POST, "/roadmaps/**").authenticated()
    			.antMatchers(HttpMethod.POST, "/roles/**").authenticated()

                .antMatchers(HttpMethod.PUT, "/project/**").authenticated()
            	.antMatchers(HttpMethod.PUT, "/btj/**").authenticated()
            	.antMatchers(HttpMethod.PUT, "/boards/**").authenticated()
            	.antMatchers(HttpMethod.PUT, "/tasks/**").authenticated()
            	.antMatchers(HttpMethod.PUT, "/roadmaps/**").authenticated()
    			.antMatchers(HttpMethod.PUT, "/roles/**").authenticated()

                .antMatchers(HttpMethod.DELETE, "/project/**").authenticated()
            	.antMatchers(HttpMethod.DELETE, "/btj/**").authenticated()
            	.antMatchers(HttpMethod.DELETE, "/boards/**").authenticated()
            	.antMatchers(HttpMethod.DELETE, "/tasks/**").authenticated()
            	.antMatchers(HttpMethod.DELETE, "/roadmaps/**").authenticated()
    			.antMatchers(HttpMethod.DELETE, "/roles/**").authenticated()
            	
				.antMatchers(HttpMethod.GET, "/**").hasAuthority(Roles_Global.r_owner)
    			.antMatchers(HttpMethod.DELETE, "/**").hasAuthority(Roles_Global.r_owner)
    			.antMatchers(HttpMethod.PUT, "/**").hasAuthority(Roles_Global.r_owner)
    			.antMatchers(HttpMethod.POST, "/**").hasAuthority(Roles_Global.r_owner)
    			
        		.antMatchers(HttpMethod.GET, "/users/test/test/**").permitAll()
				.antMatchers(HttpMethod.POST, "/users").permitAll()
            .and()
            .formLogin().permitAll()
            .and()
            .logout().logoutSuccessHandler(logoutSuccessHandler)
            .permitAll();
        
        http.csrf().disable(); //welp sadly this had to be disabled, NOTE DON'T MOVE THIS UP OR EVERYTHING WILL BREAK

    }

    @SuppressWarnings("deprecation")
	@Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
}

