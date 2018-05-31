package ru.ifmo.rain.vakhrushev.bank;

import java.util.concurrent.ConcurrentHashMap;

public class LocalPersonImpl implements LocalPerson {

    private ConcurrentHashMap<String, Account> accounts;
    private String name;
    private String surname;
    private String passport;

    public LocalPersonImpl(String name, String surname, String passport) {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        accounts = new ConcurrentHashMap<>();
    }

    public LocalPersonImpl(Person p) {
        try {
            // actually no exception can be thrown
            this.name = p.getName();
            this.surname = p.getSurname();
            this.passport = p.getPassport();
            this.accounts = p.getAccounts();
        } catch (Exception e) {
            this.name = "";
            this.surname = "";
            this.passport = "";
            this.accounts = new ConcurrentHashMap<>();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPassport() {
        return passport;
    }

    @Override
    public ConcurrentHashMap<String, Account> getAccounts() throws Exception {
        return accounts;
    }

    private Account getAccount(String accountId) {
        if (!accounts.containsKey(accountId)) {
            String bankAccountId = passport + ":" + accountId;
            accounts.put(accountId, new AccountImpl(bankAccountId));
        }
        return accounts.get(accountId);
    }

    @Override
    public int setToAccount(String accountId, int amount) {
        Account tempAccount = this.getAccount(accountId);
        tempAccount.setAmount(amount);
        accounts.put(accountId, tempAccount);
        return tempAccount.getAmount();
    }

    @Override
    public int getAmount(String accountId) {
        Account account = getAccount(accountId);
        return account.getAmount();
    }
}
