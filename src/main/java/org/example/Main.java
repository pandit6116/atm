package org.example;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.example.dao.Account;
import org.example.impl.AccountServiceImpl;
import org.example.impl.LoginServiceImpl;
import org.example.impl.TransactionDetailServiceImpl;
import org.example.service.AccountService;
import org.example.service.LoginService;
import org.example.service.TransactionDetailService;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static AccountService accountService = new AccountServiceImpl();
    private static LoginService loginService = new LoginServiceImpl();
    private static TransactionDetailService transactionDetailService;

    public static void main(String[] args) {
        initConfiguration();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Create account\n2. Login\n3. Exit");
            System.out.print("Enter Choice : ");
            int ch = in.nextInt();
            switch (ch) {
                case 1:
                    Account account = accountService.getAccountDetailFromConsole();
                    account = accountService.save(account);
                    if (Objects.isNull(account)) {
                        System.out.println("Could not create account !!");
                    } else {
                        System.out.println("Account crated successfully !!");
                    }
                    break;
                case 2:
                    Account account1 = loginService.getLoginDetailFromConsole();
                    if (loginService.isValidAccount(account1)) {
                        System.out.println("Successfully logged in !!");
                        transactionDetailService = new TransactionDetailServiceImpl(account1);
                        transactionDetailService.showTransactionScreen();
                    } else {
                        System.out.println("Invalid credential !!");
                    }
                    break;
                case 3:
                    System.out.println("Logging out...");
                    System.exit(0);
                default:
                    System.out.println("\n\nInvalid choice \n\n\n");
            }

        }
    }
    private static void initConfiguration(){
        Logger root = Logger.getRootLogger();
        root.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %m%n")));
    }
}