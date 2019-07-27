package com.fed.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(){
        return new JdbcUserDetailsManager(dataSource());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {

        authBuilder
                    .jdbcAuthentication()
                    .dataSource(dataSource())
                    .passwordEncoder(passwordEncoder());



//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)

//                .passwordEncoder(passwordEncoder());
//                .withUser(User.withDefaultPasswordEncoder().username("escobar").password(
//                        "EscosGoodPassword123!").roles("USER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

/*
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .permitAll();

*/

        http
                    .cors().disable()
                    .csrf().disable()
                    .headers().xssProtection().disable()
                ;
                /*
                .and()
                    .formLogin()
                    .loginPage("/signin")
                    .permitAll()
                .and()
                    .httpBasic()
                .and()
                    .authorizeRequests()
                    .anyRequest()
                .authenticated();

                 */

    }


//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .httpBasic();
//    }

    /*
    public void initializeGroups(){

        JdbcUserDetailsManager manager = userDetailsManager();
        List<String> groups = manager.findAllGroups();

        if (!groups.contains("players")){
            manager.createGroup("players", Roles.listOf(Roles.Value.PLAYER));
        }

        if (!groups.contains("admins")){
            manager.createGroup("admins", Roles.listOf(Roles.Value.ADMIN));
        }

    }
*/








}
