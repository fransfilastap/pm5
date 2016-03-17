package id.franspratama.geol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.spring4.SpringTemplateEngine;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	private @Autowired UserDetailsService userDetailsService;
	private @Autowired SpringTemplateEngine templateEngine;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        		.anyRequest()
        		.authenticated();

        http
        	.formLogin()
        		.usernameParameter("username")
        		.passwordParameter("password")
        		.failureUrl("/login?error")
        		.defaultSuccessUrl("/")
        		.loginPage("/login")
        		.permitAll()
        	.and()
        		.logout()
        		.logoutRequestMatcher(
        				new AntPathRequestMatcher("/login?logout")
        		).logoutSuccessUrl("/login").permitAll()
        	.and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
				
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/**/*.css")
				.antMatchers("/**/*.js")
				.antMatchers("/**/*.jpg")
				.antMatchers("/**/*.png")
				.antMatchers("/**/*.gif")
				.antMatchers("/**/*.html");
	}
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	
	
}
