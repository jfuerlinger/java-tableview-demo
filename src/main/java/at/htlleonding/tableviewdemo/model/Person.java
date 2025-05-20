package at.htlleonding.tableviewdemo.model;

public class Person {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final boolean active;

    public Person(String firstName, String lastName, int age, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
    
    public boolean isActive() {
        return active;
    }
}

