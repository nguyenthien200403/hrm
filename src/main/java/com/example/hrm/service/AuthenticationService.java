package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.AccountDTO;
import com.example.hrm.model.Employee;
import com.example.hrm.model.Role;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.RoleRepository;
import com.example.hrm.request.AuthenticationRequest;
import com.example.hrm.model.Account;
import com.example.hrm.repository.AccountRepository;
import com.example.hrm.request.RegisterRequest;
import com.example.hrm.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

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
               return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),"Not Found Name Account", null);
           }
           Account account = findResult.get();
           String jwt = jwtService.generateToken(account);
           return new GeneralResponse<>(HttpStatus.OK.value(),"Access-Token", jwt);
       }catch (BadCredentialsException e){
           return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),"Either Name Account or Password is Error", null);
       }catch (DisabledException | LockedException e){
           return new GeneralResponse<>(HttpStatus.FORBIDDEN.value(),"Account is Blocked", null);
       }
    }

    public GeneralResponse<?> register(RegisterRequest request){
       try{
           Optional<Employee> findEmp = employeeRepository.findById(request.getIdEmployee());
           Optional<Role> findRole = roleRepository.findByNameRole(request.getNameRole());
           if(findEmp.isEmpty() || findRole.isEmpty()){
               return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Not Found: {Role or Employee}", null);
           }
           Employee employee = findEmp.get();
           Role role = findRole.get();
           String email = employee.getEmail();

           Account account = new Account();
           account.setNameAccount(email);
           account.setPassword(passwordEncoder.encode(email));
           account.setEmployee(employee);
           account.setRole(role);

           accountRepository.save(account);
           AccountDTO dto = new AccountDTO(account);

           return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success", dto);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
}
