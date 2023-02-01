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
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.EventRepository;

import java.sql.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EventPersistence {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        eventRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadEventPersistence() {

        // Event

        Date date = Date.valueOf("2021-11-11");
        Time startTime = Time.valueOf("11:00:00");
        Time endTime = Time.valueOf("17:00:00");

        // Customer

        Customer aCustomer = new Customer();
        aCustomer.setAddress("Place");
        aCustomer.setFirstName("Saba");
        aCustomer.setLastName("Fathi");
        aCustomer.setIsLocal(true);

        // Create Event

        Event aEvent = new Event();
        aEvent.setDate(date);
        aEvent.setStartTime(startTime);
        aEvent.setEndTime(endTime);
        aEvent.setCustomer(aCustomer);

        // Save

        customerRepository.save(aCustomer);
        eventRepository.save(aEvent);

        // Reload

        Long Id = aEvent.getId();
        aEvent = null;
        aEvent = eventRepository.findEventById(Id);

        //Assertions

        assertNotNull(aEvent);
        assertEquals(date, aEvent.getDate());
        assertEquals(startTime, aEvent.getStartTime());
        assertEquals(endTime, aEvent.getEndTime());
        assertEquals(aCustomer, aEvent.getCustomer());

    }

}
