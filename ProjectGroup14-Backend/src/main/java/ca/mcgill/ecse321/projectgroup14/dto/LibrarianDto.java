package ca.mcgill.ecse321.projectgroup14.dto;

public class LibrarianDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private Boolean isHead;
    private String username;
    private String password;

    //Empty Constructor
    public LibrarianDto(){

    }

    public LibrarianDto (Long id, String firstName, String lastName, String email, boolean isHead, String username, String password){
        this.id = id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.isHead=isHead;
        this.username=username;
        this.password=password;
    }

    public Long getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getHead() {
        return isHead;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
