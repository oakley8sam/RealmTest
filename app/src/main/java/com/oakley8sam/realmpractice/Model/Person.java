package com.oakley8sam.realmpractice.Model;

import io.realm.RealmObject;

/**
 * Created by oakle on 10/9/2017.
 */


public class Person extends RealmObject {
    String Name;
    int Age;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "Name='" + Name + '\'' +
                ", Age=" + Age +
                '}';
    }
}
