package br.edu.ufcg.virtus.tracker.security.config;

import javax.persistence.EntityManager;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.edu.ufcg.virtus.common.config.JwtConfig;
import br.edu.ufcg.virtus.common.config.SecurityTokenConfig;
import br.edu.ufcg.virtus.common.token.JwtTokenAuthorizationFilter;
import br.edu.ufcg.virtus.common.token.TokenConverter;

@EnableWebSecurity
public class SecurityCredentialsConfig extends SecurityTokenConfig {

    private final EntityManager em;
    private final TokenConverter tokenConverter;

    public SecurityCredentialsConfig(JwtConfig jwtConfig, TokenConverter tokenConverter, EntityManager em) {
        super(jwtConfig);
        this.tokenConverter = tokenConverter;
        this.em = em;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/system/health").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterAfter(new JwtTokenAuthorizationFilter(this.jwtConfig, this.tokenConverter, this.em),
                UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

}
