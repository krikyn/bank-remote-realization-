package ru.ifmo.rain.vakhrushev.bank;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class BankImpl implements Bank {

    private ConcurrentHashMap<String, RemotePerson> persons;
    private static Registry registry = null;

    public BankImpl() {
        persons = new ConcurrentHashMap<>();
        try {
            registry = LocateRegistry.createRegistry(8080);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Bank bank = new BankImpl();
        try {
            Bank stub = (Bank) UnicastRemoteObject.exportObject(bank, 8080);
            registry.bind("Bank", stub);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person getPerson(String passport, String type) {

        if (!("remote".equals(type) || "local".equals(type)) || !persons.containsKey(passport)) {
            return null;
        }

        if ("local".equals(type)) {
            return new LocalPersonImpl(persons.get(passport));
        }
        if ("remote".equals(type)) {
            return persons.get(passport);
        }
        return null;
    }

    @Override
    public boolean createPerson(String name, String surname, String passport) {
        try {
            if (!persons.containsKey(passport)) {
                persons.put(passport, new RemotePersonImpl(name, surname, passport));
                return true;
            } else {
                return false;
            }

        } catch (RemoteException e) {
            e.printStackTrace();

            return false;
        }
    }
}
