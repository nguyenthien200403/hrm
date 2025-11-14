package com.example.hrm.security;

import com.example.hrm.model.Account;
import com.example.hrm.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(),
                Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    RegisteredClientRepository registeredClientRepository(){
//        RegisteredClient client1 = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("client-1")
//                .clientName("Thien")
//                .clientSecret("{noop}password1")
//                .scope("read")
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
//                .build();
//        return new InMemoryRegisteredClientRepository(client1);
//    }


    @Bean
    RegisteredClientRepository registeredClientRepository(AccountRepository accountRepository){
        return new RegisteredClientRepository() {
            @Override
            public void save(RegisteredClient registeredClient) {

            }

            @Override
            public RegisteredClient findById(String id) {
                return null;
            }

            @Override
            public RegisteredClient findByClientId(String nameAccount) {
                Optional<Account> findResult = accountRepository.findByNameAccount(nameAccount);
                if(findResult.isEmpty()) return null;

                Account account = findResult.get();
                return RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId(account.getNameAccount())
                        .clientSecret(account.getPassword())
                        .scope(account.getRole())
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                        .build();
            }
        };
    }

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return (context) ->{
            if(OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())){
                RegisteredClient client = context.getRegisteredClient();
                JwtClaimsSet.Builder builder = context.getClaims();
                builder.issuer("My Great Company");
                builder.expiresAt(Instant.now().plus(10, ChronoUnit.MINUTES));

                builder.claims((claims) ->{
                    claims.put("scope", client.getScopes());
                    claims.put("name", client.getClientName());
                    claims.remove("aud");
                });
            }
        } ;
    }

}
