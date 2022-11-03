package com.example.test.security;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class BasicSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(final WebSecurity web) {
    web.ignoring().antMatchers("/**");
  }

}
