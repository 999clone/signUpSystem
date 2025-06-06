package aut.ap;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    private String firstName;

    @Basic(optional = false)
    private String lastName;

    @Basic(optional = false)
    private Integer age;

    @Basic(optional = false)
    @Column(unique = true)
    private String email;

    @Basic(optional = false)
    private String password;

    public User() {}

    public User(String firstName, String lastName, int age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
}
