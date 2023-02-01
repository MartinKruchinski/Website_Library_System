package ca.mcgill.ecse321.projectgroup14.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.dto.*;
import ca.mcgill.ecse321.projectgroup14.services.*;


@CrossOrigin(origins = "*")
@RestController
public class LibraryController {


    @Autowired
    private LibraryService libraryService;



    @PostMapping (value = {"/library/{name}", "/library/{name}/"})
    public LibraryDto createLibrary (@PathVariable("name") String name,
                                     @RequestParam(name = "address") String address,
                                     @RequestParam(name = "phone_number") String phoneNumber,
                                     @RequestParam(name = "email") String email) {

        return convertToDto(libraryService.createLibrary(name, address, phoneNumber, email));
    }

    @GetMapping (value = {"/library", "/library/"})
    public LibraryDto getLibrary () {


        LibraryDto libraryDto = LibraryController.convertToDto(libraryService.getLibrary());
        return libraryDto;

    }

    @PatchMapping (value = {"library/edit/{name}", "library/edit/{name}/"})
    public void updateLibrary (@PathVariable("name") String name,
                               @RequestParam(name = "address") String address,
                               @RequestParam(name = "phone_number") String phoneNumber,
                               @RequestParam(name = "email") String email) {

        libraryService.updateLibrary(name, address, phoneNumber, email);

    }

    @DeleteMapping (value = {"library/delete/{name}", "library/delete/{name}/"})
    public void deleteLibrary (@PathVariable("name") String name) {

        libraryService.deleteLibrary();

    }

    private static OpeningHourDto convertToDto (OpeningHour openingHour) {

        if (openingHour == null) throw new IllegalArgumentException("Opening Hour cannot be null");

        OpeningHourDto openingHourDto = new OpeningHourDto(openingHour.getId(),
                                            OpeningHourDto.DayOfWeek.valueOf(openingHour.getDay().toString()),
                                            openingHour.getStartTime(), openingHour.getEndTime());
        return openingHourDto;
    }

    private static List<OpeningHourDto> convertHoursToDto (List<OpeningHour> openingHours) {
        List<OpeningHourDto> hoursDto = new ArrayList<OpeningHourDto>();
        for (OpeningHour openingHour : openingHours) {
            hoursDto.add(convertToDto(openingHour));
        }
        return hoursDto;
    }

    private static LibraryDto convertToDto (Library library) {

        if (library == null) throw new IllegalArgumentException("Library cannot be null");

        LibraryDto libraryDto = new LibraryDto(library.getId(), library.getName(), library.getAddress(),
                                library.getPhoneNumber(), library.getEmail(), convertHoursToDto(library.getOpeningHours()));
        return libraryDto;

    }
}
