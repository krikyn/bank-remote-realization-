package ru.ifmo.rain.vakhrushev.bank;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Objects;

public class Client {

    private static final String USAGE = "usage: Client <name> <surname> <passport number> <account's id> <change of account amount> <wayToShareObject [remote | local]>";
    private static final String PASS_ERROR = "this pass number is already registered for other person";
    private static final String CREATE_ERR = "error while creation";

    private static boolean checkPerson(Person p, String name, String surname) {
        try {
            if (p.getName().equals(name) && p.getSurname().equals(surname))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Bank bank = null;

        if (args == null || Arrays.stream(args).anyMatch(Objects::isNull) || args.length < 6 || !("remote".equals(args[5]) || "local".equals(args[5]))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String name = args[0];
        String surname = args[1];
        String passport = args[2];
        String accountId = args[3];
        Integer changeAmmount = 0;
        try {
            changeAmmount = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.out.println("Can't parse change of account amount");
            System.exit(1);
        }
        String type = args[5];

        System.out.println("Mod: " + type);
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            bank = (Bank) registry.lookup("Bank");
        } catch (NotBoundException e) {
            System.out.println("not bound");
            System.exit(1);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        if (type.equals("remote")) {
            try {
                RemotePerson person = (RemotePerson) bank.getPerson(passport, type);
                if (person == null) {
                    if (!bank.createPerson(name, surname, passport)) {
                        System.out.println(CREATE_ERR);
                        System.exit(1);
                    }
                    System.out.println("Registred new person: ");
                } else {
                    if (!checkPerson(person, name, surname)) {
                        System.out.println(PASS_ERROR);
                        System.exit(1);
                    }
                    System.out.print("Person: ");
                }


                person = (RemotePerson) bank.getPerson(passport, type);
                System.out.println(person.getName());

                System.out.println("On account \"" + accountId + "\": " + person.getAmount(accountId));
                person.setToAccount(accountId, changeAmmount + person.getAmount(accountId));
                System.out.println("Changed, new amount: " + person.getAmount(accountId));


                person = (RemotePerson) bank.getPerson(passport, type);
                System.out.println("Retrieved person: " + person.getName());
                System.out.println("On account \"" + accountId + "\": " + person.getAmount(accountId));
                Thread.sleep(1000);

            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                LocalPerson person = (LocalPerson) bank.getPerson(passport, type);
                if (person == null) {
                    if (!bank.createPerson(name, surname, passport)) {
                        System.out.println(CREATE_ERR);
                        System.exit(1);
                    }
                    System.out.println("Registred new person: ");
                } else {
                    if (!checkPerson(person, name, surname)) {
                        System.out.println(PASS_ERROR);
                        System.exit(1);
                    }
                    System.out.print("Person: ");
                }

                person = (LocalPerson) bank.getPerson(passport, type);
                System.out.println(person.getName());

                System.out.println("On account \"" + accountId + "\": " + person.getAmount(accountId));
                person.setToAccount(accountId, changeAmmount + person.getAmount(accountId));
                System.out.println("Changed, new amount: " + person.getAmount(accountId));


                person = (LocalPerson) bank.getPerson(passport, type);
                System.out.println("Retrieved person: " + person.getName());
                System.out.println("On account \"" + accountId + "\": " + person.getAmount(accountId));

            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }


    }

}