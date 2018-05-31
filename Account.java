package ru.ifmo.rain.vakhrushev.bank;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Account extends Serializable {

    String getId();

    int getAmount();

    void setAmount(int amount);
}