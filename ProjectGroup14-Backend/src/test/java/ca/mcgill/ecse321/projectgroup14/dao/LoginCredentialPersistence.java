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
import ca.mcgill.ecse321.projectgroup14.repository.LoginCredentialRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginCredentialPersistence {

    @Autowired
    private LoginCredentialRepository loginCredentialRepository;


    @AfterEach
    public void clearDatabase() {
        loginCredentialRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadLoginPersistence() {

        // Login Credential

        String username = "UName";
        String password = "PWord";
        String email = "Email";

        // Create Login

        LoginCredential aLoginCredential = new LoginCredential();
        aLoginCredential.setUsername(username);
        aLoginCredential.setPassword(password);
        aLoginCredential.setEmail(email);

        // Save

        loginCredentialRepository.save(aLoginCredential);

        // Reload

        Long loginCredentialId = aLoginCredential.getId();
        aLoginCredential = null;
        aLoginCredential = loginCredentialRepository.findLoginCredentialById(loginCredentialId);

        // Assertions
        System.out.println(loginCredentialRepository.findAll());
        assertNotNull(aLoginCredential);
        assertNotNull(aLoginCredential.getId());
        assertEquals(username, aLoginCredential.getUsername());
        assertEquals(password, aLoginCredential.getPassword());
        assertEquals(email, aLoginCredential.getEmail());

    }

}
