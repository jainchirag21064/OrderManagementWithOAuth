package com.javainuse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * AuthorizationServer - Responsible to hold the InMemory details of ResourceClient and the users
 *
 * @author chiragjain
 */
@Configuration
public class OrderManagementAuthorizationServer extends AuthorizationServerConfigurerAdapter {

    //Resource Service Details
    @Value("${security.oauth2.client.client-id}")
    public String resourceClientId;
    @Value("${security.oauth2.client.client-secret}")
    public String resourceSecret;
    @Value("${security.oauth2.client.authorities}")
    public String resourceRole;

    //Admin User to be added in memory for OAuth2
    @Value("${security.oauth2.adminuser.client-id}")
    public String adminUserClientId;
    @Value("${security.oauth2.adminuser.client-secret}")
    public String adminUserSecret;
    @Value("${security.oauth2.adminuser.authorities}")
    public String adminUserRole;

    //Normal User to be added in memory for OAuth2
    @Value("${security.oauth2.user.client-id}")
    public String userClientId;
    @Value("${security.oauth2.user.client-secret}")
    public String userSecret;
    @Value("${security.oauth2.user.authorities}")
    public String userRole;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        //Added Resource Client,Admin User and Normal user to inmemory
        clients
                .inMemory()
                .withClient(resourceClientId)
                .secret(passwordEncoder().encode(resourceSecret))
                .authorities(resourceRole)
                .scopes("all")
                .authorizedGrantTypes("client_credentials")
                .and()
                .withClient(adminUserClientId)
                .secret(passwordEncoder().encode(adminUserSecret))
                .authorities(adminUserRole)
                .scopes("all")
                .authorizedGrantTypes("client_credentials")
                .and()
                .withClient(userClientId)
                .secret(passwordEncoder().encode(userSecret))
                .authorities(userRole)
                .scopes("all")
                .authorizedGrantTypes("client_credentials");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //To secure  the oauth/check_token endpoint.
        // Used for verifying the Token that can only be done by Resource
        security.checkTokenAccess("hasAuthority('ROLE_RESOURCE')");
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

}
