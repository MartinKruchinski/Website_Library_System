package ca.mcgill.ecse321.projectgroup14.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.repository.LibraryRepository;
import ca.mcgill.ecse321.projectgroup14.repository.OpeningHourRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LibraryPersistence {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private OpeningHourRepository openingHourRepository;

    @AfterEach
    public void clearDatabase() {
        libraryRepository.deleteAll();
        openingHourRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadLibraryPersistence() {

        // Library

        String name = "ALib";
        String address = "AAdd";
        String email = "AEmail";
        String phoneNumber = "ANum";

        // Create Library

        Library aLibrary = new Library();
        aLibrary.setName(name);
        aLibrary.setAddress(address);
        aLibrary.setEmail(email);
        aLibrary.setPhoneNumber(phoneNumber);

        //Create opening hours
        List<OpeningHour> hours = new ArrayList<OpeningHour>();
        OpeningHour openingHour_1 = new OpeningHour();
        openingHour_1.setDay(DayOfWeek.Monday);
        openingHour_1.setStartTime(Time.valueOf("10:00:00"));
        openingHour_1.setEndTime(Time.valueOf("17:00:00"));

        OpeningHour openingHour_2 = new OpeningHour();
        openingHour_2.setDay(DayOfWeek.Tuesday);
        openingHour_2.setStartTime(Time.valueOf("10:00:00"));
        openingHour_2.setEndTime(Time.valueOf("17:00:00"));

        hours.add(openingHour_1);
        hours.add(openingHour_2);
        openingHourRepository.save(openingHour_1);
        openingHourRepository.save(openingHour_2);

        aLibrary.setOpeningHours(hours);


        // Save

        libraryRepository.save(aLibrary);

        // Reload

        Long Id = aLibrary.getId();
        Library aLibraryTest = null;
        aLibraryTest = libraryRepository.findLibraryById(Id);

        // Assertions

        assertNotNull(aLibraryTest);
        assertEquals(name, aLibraryTest.getName());
        assertEquals(address, aLibraryTest.getAddress());
        assertEquals(email, aLibraryTest.getEmail());
        assertEquals(phoneNumber, aLibraryTest.getPhoneNumber());
        assertEquals(hours, aLibraryTest.getOpeningHours());

    }

}
