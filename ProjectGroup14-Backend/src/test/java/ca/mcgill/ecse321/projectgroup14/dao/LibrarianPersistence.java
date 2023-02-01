package ca.mcgill.ecse321.projectgroup14.dao;

import static org.junit.jupiter.api.Assertions.*;

// import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.repository.LibrarianRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LibrarianPersistence {

    @Autowired
    private LibrarianRepository librarianRepository;


    @AfterEach
    public void clearDatabase() {
        librarianRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadLibrarianPersistence() {

        // Librarian

        String firstName = "William";
        String lastName = "Lumsden";
        Boolean isHead = true;
        String username = "WillU";
        String password = "WillP";
        String email = "WillE";


        // Create Librarian

        Librarian aLibrarian = new Librarian();
        aLibrarian.setFirstName(firstName);
        aLibrarian.setLastName(lastName);
        aLibrarian.setIsHead(isHead);
        aLibrarian.setUsername(username);
        aLibrarian.setPassword(password);
        aLibrarian.setEmail(email);

        // Save
        librarianRepository.save(aLibrarian);

        // Reload

        Long Id = aLibrarian.getId();
        aLibrarian = null;
        aLibrarian = librarianRepository.findLibrarianById(Id);

        //Assertions

        assertNotNull(aLibrarian);
        assertEquals(firstName, aLibrarian.getFirstName());
        assertEquals(lastName, aLibrarian.getLastName());
        assertEquals(isHead, aLibrarian.getIsHead());

    }

}
