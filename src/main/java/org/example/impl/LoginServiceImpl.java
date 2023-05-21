package org.example.impl;

import org.example.dao.Account;
import org.example.repository.AccountRepo;
import org.example.service.LoginService;

import java.util.Scanner;

public class LoginServiceImpl implements LoginService {
    private AccountRepo accountRepo;
    public LoginServiceImpl(){
        this.accountRepo = new AccountRepo();
    }
    @Override
    public Account getLoginDetailFromConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter atm number: ");
        Long accountNumber = sc.nextLong();
        System.out.print("\nEnter atm pin: ");
        int pin = sc.nextInt();
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setPin(pin);
        return account;
    }

    @Override
    public boolean isValidAccount(Account account) {
        return accountRepo.isValidAccount(account);
    }
}
