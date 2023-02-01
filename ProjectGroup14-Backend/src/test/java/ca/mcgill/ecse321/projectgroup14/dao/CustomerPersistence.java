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
import ca.mcgill.ecse321.projectgroup14.repository.BookingRepository;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.EventRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LoginCredentialRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerPersistence {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoginCredentialRepository loginCredentialRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @AfterEach
    public void clearDatabase() {
        eventRepository.deleteAll();
        bookingRepository.deleteAll();
        customerRepository.deleteAll();
        loginCredentialRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadCustomerPersistence() {

        // Customer
        String address = "Place";
        String firstName = "Martin";
        String lastName = "Kruchinski";
        Boolean isLocal = true;

        // Login Credential

        LoginCredential aLoginCredential = new LoginCredential();
        aLoginCredential.setEmail("Mail");
        aLoginCredential.setUsername("MartinU");
        aLoginCredential.setPassword("MartinP");

        // Create Customer

        Customer aCustomer = new Customer();
        aCustomer.setAddress(address);
        aCustomer.setFirstName(firstName);
        aCustomer.setLastName(lastName);
        aCustomer.setIsLocal(isLocal);
        aCustomer.setLoginCredential(aLoginCredential);

        // Save

        loginCredentialRepository.save(aLoginCredential);
        customerRepository.save(aCustomer);

        // Reload

        Long Id = aCustomer.getId();
        aCustomer = null;
        aCustomer = customerRepository.findCustomerById(Id);

        // //Assertions

        assertNotNull(aCustomer);
        assertEquals(address, aCustomer.getAddress());
        assertEquals(firstName, aCustomer.getFirstName());
        assertEquals(lastName, aCustomer.getLastName());
        assertEquals(isLocal, aCustomer.getIsLocal());
        assertEquals(aLoginCredential, aCustomer.getLoginCredential());

    }

}
