package ca.mcgill.ecse321.projectgroup14.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.repository.LibrarianRepository;
import ca.mcgill.ecse321.projectgroup14.repository.ShiftRepository;

import java.sql.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class ShiftPersistence {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private LibrarianRepository librarianRepository;


    @AfterEach
    public void clearDatabase() {
        shiftRepository.deleteAll();
        librarianRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadShiftPersistence() {

        // Shift

        DayOfWeek dayOfWeek = DayOfWeek.Monday;
        Time startTime = Time.valueOf("11:00:00");
        Time endTime = Time.valueOf("17:00:00");


        // Librarian

        Librarian aLibrarian = new Librarian();
        aLibrarian.setFirstName("William");
        aLibrarian.setLastName("Lumsden");
        aLibrarian.setIsHead(true);

        // Create Shift

        Shift aShift = new Shift();
        aShift.setDay(dayOfWeek);
        aShift.setStartTime(startTime);
        aShift.setEndTime(endTime);
        aShift.setLibrarian(aLibrarian);

        // Save

        librarianRepository.save(aLibrarian);
        shiftRepository.save(aShift);

        // Reload

        Long Id = aShift.getId();
        aShift = null;
        aShift = shiftRepository.findShiftById(Id);

        //Assertions

        assertNotNull(aShift);
        assertEquals(dayOfWeek, aShift.getDay());
        assertEquals(startTime, aShift.getStartTime());
        assertEquals(endTime, aShift.getEndTime());
        assertEquals(aLibrarian, aShift.getLibrarian());

    }

}
