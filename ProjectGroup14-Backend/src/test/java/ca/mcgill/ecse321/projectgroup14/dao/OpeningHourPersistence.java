package ca.mcgill.ecse321.projectgroup14.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.repository.OpeningHourRepository;

import java.sql.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OpeningHourPersistence {

    @Autowired
    private OpeningHourRepository openingHourRepository;


    @AfterEach
    public void clearDatabase() {
        openingHourRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadOpeningHourPersistence() {

        // Opening Hour

        DayOfWeek dayOfWeek = DayOfWeek.Monday;
        Time startTime = Time.valueOf("11:00:00");
        Time endTime = Time.valueOf("17:00:00");



        // Create Opening Hour

        OpeningHour aOpeningHour = new OpeningHour();
        aOpeningHour.setDay(dayOfWeek);
        aOpeningHour.setStartTime(startTime);
        aOpeningHour.setEndTime(endTime);


        // Save

        openingHourRepository.save(aOpeningHour);

        // Reload

        Long Id = aOpeningHour.getId();
        aOpeningHour = null;
        aOpeningHour = openingHourRepository.findOpeningHourById(Id);

        //Assertions

        assertNotNull(aOpeningHour);
        assertEquals(dayOfWeek, aOpeningHour.getDay());
        assertEquals(startTime, aOpeningHour.getStartTime());
        assertEquals(endTime, aOpeningHour.getEndTime());

    }
}
