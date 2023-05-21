package org.example.service;

import org.example.dao.Account;

public interface AccountService {
    Account getAccountDetailFromConsole();
    Account save(Account account);
}
