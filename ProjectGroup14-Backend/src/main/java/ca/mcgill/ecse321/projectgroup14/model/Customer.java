package ca.mcgill.ecse321.projectgroup14.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Customer {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String address;
    private Boolean isLocal;

    @OneToOne (optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
    private LoginCredential loginCredential;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public Boolean getIsLocal () {
        return isLocal;
    }

    public void setIsLocal (Boolean isLocal) {
        this.isLocal = isLocal;
    }
    
    public LoginCredential getLoginCredential () {
        return loginCredential;
    }

    public void setLoginCredential (LoginCredential loginCredential) {
        this.loginCredential = loginCredential;
    }

}
