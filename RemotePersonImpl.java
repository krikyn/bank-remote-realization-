package ru.ifmo.rain.vakhrushev.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class RemotePersonImpl implements RemotePerson {

    protected String name;
    protected String surname;
    protected String passport;
    protected ConcurrentHashMap<String, Account> accounts;

    RemotePersonImpl(String name, String Surname, String passport) throws RemoteException {
        this.name = name;
        this.surname = Surname;
        this.passport = passport;
        accounts = new ConcurrentHashMap<>();
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public String getSurname() throws RemoteException {
        return surname;
    }

    @Override
    public String getPassport() throws RemoteException {
        return passport;
    }

    @Override
    public ConcurrentHashMap<String, Account> getAccounts() throws RemoteException {
        return accounts;
    }

    private Account getAccount(String accountId) {
        if (!accounts.containsKey(accountId)){
            String bankAccountId = passport + ":" + accountId;
            accounts.put(accountId, new AccountImpl(bankAccountId));
        }
        return accounts.get(accountId);
    }

    @Override
    public synchronized int setToAccount(String accountId, int amount) throws RemoteException {
        Account tempAccount = this.getAccount(accountId);
        tempAccount.setAmount(amount);
        accounts.put(accountId, tempAccount);
        return tempAccount.getAmount();
    }

    @Override
    public int getAmount(String accountId) throws RemoteException {
        Account tempAccount = this.getAccount(accountId);
        return tempAccount.getAmount();
    }
}
