package ru.ifmo.rain.vakhrushev.bank;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public interface Person {

    String getName() throws Exception;

    String getSurname() throws Exception;

    String getPassport() throws Exception;

    ConcurrentHashMap<String, Account> getAccounts() throws Exception;

    int setToAccount(String accountId, int amount) throws Exception;

    int getAmount(String accountId) throws Exception;
}
