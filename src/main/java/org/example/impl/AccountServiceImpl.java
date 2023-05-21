package org.example.impl;

import org.example.dao.Account;
import org.example.repository.AccountRepo;
import org.example.service.AccountService;

import java.util.Scanner;

public class AccountServiceImpl implements AccountService {
    private AccountRepo accountRepo;
    public AccountServiceImpl(){
        this.accountRepo = new AccountRepo();
    }
    @Override
    public Account getAccountDetailFromConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account number: ");
        long accountNumber = sc.nextLong();
        System.out.print("\nEnter pin: ");
        int pin = sc.nextInt();
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setPin(pin);
        return account;
    }

    @Override
    public Account save(Account account) {
        return accountRepo.saveAccount(account);
    }
}
