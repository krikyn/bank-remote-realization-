package ru.ifmo.rain.vakhrushev.bank;

import java.rmi.RemoteException;

public class AccountImpl implements Account {

    private String id;
    private int amount;

    private AccountImpl() {
    }

    AccountImpl(String id) {
        this.id = id;
        this.amount = 0;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
