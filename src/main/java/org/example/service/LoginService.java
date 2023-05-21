package org.example.service;

import org.example.dao.Account;

public interface LoginService {
    Account getLoginDetailFromConsole();
    boolean isValidAccount(Account account);
}
