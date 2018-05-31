package ru.ifmo.rain.vakhrushev.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public interface RemotePerson extends Remote, Person {

    String getName() throws RemoteException;

    String getSurname() throws RemoteException;

    String getPassport() throws RemoteException;

    ConcurrentHashMap<String, Account> getAccounts() throws RemoteException;

    int setToAccount(String accountId, int amount) throws RemoteException;

    int getAmount(String accountId) throws RemoteException;
}
