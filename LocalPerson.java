package ru.ifmo.rain.vakhrushev.bank;

import java.io.Serializable;

public interface LocalPerson extends Serializable, Person{
    String getName();

    String getSurname();

    String getPassport();

    int setToAccount(String accountId, int amount);

    int getAmount(String accountId);
}
