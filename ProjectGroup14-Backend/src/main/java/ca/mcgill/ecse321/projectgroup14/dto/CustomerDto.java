package ca.mcgill.ecse321.projectgroup14.dto;


public class CustomerDto {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String address;
    private Boolean isLocal;
    private LoginCredentialDto loginCredential;

    public CustomerDto () {}

    public CustomerDto (Long id, String firstName, String lastName, String address, boolean isLocal) {

        this.customerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.isLocal = isLocal;

    }

    public Long getId () {
        return customerId;
    }

    public String getFirstName () {
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public String getAddress () {
        return address;
    }

    public Boolean getIsLocal () {
        return isLocal;
    }

    public LoginCredentialDto getLoginCredential () {
        return this.loginCredential;
    }

    public void setLoginCredential (LoginCredentialDto loginCredential) {
        this.loginCredential = loginCredential;
    }


}
