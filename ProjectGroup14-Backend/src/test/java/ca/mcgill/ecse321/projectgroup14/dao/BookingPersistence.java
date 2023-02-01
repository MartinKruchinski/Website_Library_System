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
import ca.mcgill.ecse321.projectgroup14.model.Booking.BookingType;
import ca.mcgill.ecse321.projectgroup14.model.Item.ItemType;
import ca.mcgill.ecse321.projectgroup14.repository.BookingRepository;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.ItemRepository;

import java.sql.*;

@ExtendWith (SpringExtension.class)
@SpringBootTest
public class BookingPersistence {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    public void clearDatabase() {
        bookingRepository.deleteAll();
        customerRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadBookingPersistence() {

        // Booking

        BookingType type = BookingType.Checkout;
        Date startDate = Date.valueOf("2021-11-11");
        Date endDate = Date.valueOf("2021-12-12");

        // Item

        Item aItem = new Item();
        ItemType itemType = ItemType.Book;
        aItem.setItemType(itemType);
        aItem.setIsAvailable(true);
        aItem.setAuthor("Martin");
        aItem.setTitle("A Book");

        // Customer

        Customer aCustomer = new Customer();
        aCustomer.setAddress("Place");
        aCustomer.setFirstName("Saba");
        aCustomer.setLastName("Fathi");
        aCustomer.setIsLocal(true);

        // Create Booking

        Booking aBooking = new Booking();
        aBooking.setBookingType(type);
        aBooking.setStartDate(startDate);
        aBooking.setEndDate(endDate);
        aBooking.setItem(aItem);
        aBooking.setCustomer(aCustomer);

        // Save

        itemRepository.save(aItem);
        customerRepository.save(aCustomer);
        bookingRepository.save(aBooking);

        // Reload

        Long Id = aBooking.getId();
        aBooking = null;
        aBooking = bookingRepository.findBookingById(Id);

        // Assertions

        assertNotNull(aBooking);
        assertEquals(startDate, aBooking.getStartDate());
        assertEquals(endDate, aBooking.getEndDate());
        assertEquals(type, aBooking.getBookingType());
        assertEquals(aCustomer, aBooking.getCustomer());
        assertEquals(aItem, aBooking.getItem());

    }

}
