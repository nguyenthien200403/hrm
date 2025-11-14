package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Account;
import com.example.hrm.repository.AccountRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public GeneralResponse<List<Account>> getAllAccount(){
        List<Account> list = accountRepository.findAll();
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST, "Accounts is Empty", "Account", null);
        }
        return new GeneralResponse<>(HttpStatus.OK, "List", "Account", list);
    }

//    public GeneralResponse<Account> getAccountByName(String nameAccount){
//        if(nameAccount.isEmpty()){
//            return new GeneralResponse<>(HttpStatus.BAD_REQUEST,"Invalid Param Name Account","Account", null);
//        }
//        Optional<Account> findResult = accountRepository.findByNameAccount(nameAccount);
//        if(findResult.isPresent()){
//            Account account = findResult.get();
//            return new GeneralResponse<>(HttpStatus.OK, "Success", "Account", account);
//        }
//        return new GeneralResponse<>(HttpStatus.BAD_REQUEST,"No Account","Account", null);
//    }
}
