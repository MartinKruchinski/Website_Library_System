package ca.mcgill.ecse321.projectgroup14.dto;

import java.util.List;

public class LibraryDto {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    private List<OpeningHourDto> openingHours;
    
    public LibraryDto () {}

    public LibraryDto (Long id, String name, String address, String phoneNumber, String email, List<OpeningHourDto> openingHours) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.openingHours = openingHours;

    }

    public Long getId () {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public String getAddress () {
        return this.address;
    }

    public String getPhoneNumber () {
        return this.phoneNumber;
    }

    public String getEmail () {
        return this.email;
    }

    public List<OpeningHourDto> getOpeningHours () {
        return this.openingHours;
    }

    public void setOpeningHours (List<OpeningHourDto> openingHours) {
        this.openingHours = openingHours;
    }

}
