package com.fed.configurations;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DataSource dataSource;

    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public JdbcUserDetailsManager getJdbcUserDetailsManager(){
        return jdbcUserDetailsManager;
    }

//    @Bean
//    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
//        return jdbcUserDetailsManager;
//    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        jdbcUserDetailsManager =
                auth
                        .jdbcAuthentication()
                        .dataSource(dataSource)
                        .passwordEncoder(passwordEncoder())
                        .getUserDetailsService();
    }


}
