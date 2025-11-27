package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.AuthRequest;
import com.example.hrm.model.Account;
import com.example.hrm.repository.AccountRepository;
import com.example.hrm.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public GeneralResponse<?> authenticate(AuthRequest request){
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           request.getNameAccount(),
                           request.getPassword()
                   )
           );
           Optional<Account> findResult = accountRepository.findByNameAccount(request.getNameAccount());
           if(findResult.isEmpty()){
               return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),"Not Found Name Account", null);
           }
           Account account = findResult.get();
           String jwt = jwtService.generateToken(account);
           return new GeneralResponse<>(HttpStatus.OK.value(),"Access-Token", jwt);
       }catch (BadCredentialsException e){
           return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),"Password is Error", null);
       }catch (DisabledException | LockedException e){
           return new GeneralResponse<>(HttpStatus.FORBIDDEN.value(),"Account is locked", null);
       }
    }
}
