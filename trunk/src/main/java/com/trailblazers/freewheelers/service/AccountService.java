package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.model.Account;

import java.util.List;

public interface AccountService {

    void save(Account account);

    List<Account> findAll();

    Account getAccountIdByName(String userName);

    Account get(Long account_id);
}
