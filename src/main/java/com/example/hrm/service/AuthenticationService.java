package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.AccountDTO;
import com.example.hrm.model.Employee;
import com.example.hrm.model.OTP;
import com.example.hrm.model.Role;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.OTPRepository;
import com.example.hrm.repository.RoleRepository;
import com.example.hrm.request.AuthenticationRequest;
import com.example.hrm.model.Account;
import com.example.hrm.repository.AccountRepository;
import com.example.hrm.request.RegisterRequest;
import com.example.hrm.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final OTPRepository otpRepository;

    private final AuthenticationManager authenticationManager;

    public GeneralResponse<?> authenticate(AuthenticationRequest request){
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           request.getNameAccount(),
                           request.getPassword()
                   )
           );
           Optional<Account> findResult = accountRepository.findByNameAccount(request.getNameAccount());
           if(findResult.isEmpty()){
               return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found Name Account", null);
           }
           Account account = findResult.get();
           String jwt = jwtService.generateToken(account);
           return new GeneralResponse<>(HttpStatus.OK.value(),"Access-Token", jwt);
       }catch (BadCredentialsException e){
           return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),"Wrong: {Account Name or Password}", null);
       }catch (DisabledException | LockedException e){
           return new GeneralResponse<>(HttpStatus.FORBIDDEN.value(),"Account is Blocked", null);
       }
    }

    public GeneralResponse<?> register(RegisterRequest request){
       try{
           Optional<Employee> findEmp = employeeRepository.findById(request.getIdEmployee());
           Optional<Role> findRole = roleRepository.findByNameRole(request.getNameRole());
           if(findEmp.isEmpty() || findRole.isEmpty()){
               return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found: {Role or Employee}", null);
           }
           Employee employee = findEmp.get();
           Role role = findRole.get();
           String email = employee.getEmail();

           var account = Account.builder()
                   .nameAccount(email)
                   .password(passwordEncoder.encode(email))
                   .employee(employee)
                   .role(role)
                   .build();

           accountRepository.save(account);
           AccountDTO dto = new AccountDTO(account);

           return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success", dto);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    public GeneralResponse<?> changeInfo(String token, String newName, String newPassword){

        String oldName = jwtService.extractAccountName(token);

        Optional<Account> findResult = accountRepository.findByNameAccount(oldName);

        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Wrong Account Name ", null);
        }

        Account account = findResult.get();

        if(accountRepository.existsByNameAccountAndIdNot(newName, account.getId())){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Exists Account Name ", null);
        }

        account.setNameAccount(newName);
        account.setPassword(passwordEncoder.encode(newPassword));

        accountRepository.save(account);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Account changed Successfully" , null);
    }


    public GeneralResponse<?> resetPassword(String email, String newPassword){
        Optional<Account> findResult = accountRepository.findByEmployeeEmail(email);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Email", null);

        }

        Account account = findResult.get();
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Reset Password Success", null);
    }


}
