package ca.ulaval.ima.tp1;

import java.util.Date;


public class Profile implements java.io.Serializable {
    public String firstName, lastName, IDUL;
    public Date birthDate;

    public Profile(String firstName, String lastName, Date birthDate, String IDUL) {
        // This constructor has one parameter, name.
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.IDUL = IDUL;
    }
}
