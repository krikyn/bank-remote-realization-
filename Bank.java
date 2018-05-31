package ru.ifmo.rain.vakhrushev.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {

    Person getPerson(String passNum, String type) throws RemoteException;

    boolean createPerson(String name, String surname, String passNum) throws RemoteException;
}
