package ca.mcgill.ecse321.projectgroup14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup14.dto.LoginCredentialDto;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.services.CustomerService;
import ca.mcgill.ecse321.projectgroup14.services.LoginCredentialService;

@CrossOrigin(origins = "*")
@RestController
public class LoginCredentialController {

    @Autowired
    LoginCredentialService loginCredentialService;

    @Autowired
    CustomerService customerService;

    @PostMapping(value = { "/logincredentials/{username}", "/logincredentials/{username}/" })
    public LoginCredentialDto createLoginCredential (@PathVariable(name = "username") String username, @RequestParam(name = "password") String password, @RequestParam(name = "email") String email, @RequestParam(name = "customer_id") String customer_id) throws IllegalArgumentException{
        Long theID = Long.parseLong(customer_id);
        Customer customer = customerService.getCustomerById(theID);
        LoginCredential loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);

        return convertToDto(loginCredential);
    }

    @GetMapping(value = { "/logincredentials/{id}", "/logincredentials/{id}/" })
    public LoginCredentialDto getLoginCredentialById(@PathVariable("id") Long id) throws IllegalArgumentException{

        return convertToDto(loginCredentialService.getLoginCredentialById(id));

    }

    @DeleteMapping(value = { "delete/logincredentials/{username}", "delete/logincredentials/{username}/" })
    public void deleteLoginCredential(@PathVariable("username") String username) throws IllegalArgumentException{

        LoginCredential loginCredential = loginCredentialService.getLoginCredentialByUsername(username);

        if (loginCredential == null) throw new IllegalArgumentException("Login does not exist");

        loginCredentialService.deleteLoginCredential(loginCredential);
    }

    @PatchMapping(value = { "edit_username/logincredentials/{id}", "edit_username/logincredentials/{id}/" })
    public void updateLoginCredentialUsername(@PathVariable("id") Long id, @RequestParam(name = "username") String newUsername) throws IllegalArgumentException{

        LoginCredential loginCredential = loginCredentialService.getLoginCredentialById(id);
        Customer customer = customerService.getCustomerByLoginCredential(loginCredential);

        loginCredentialService.updateLoginCredential(newUsername, loginCredential.getPassword(), loginCredential.getEmail(), customer, loginCredential);
    }

    @PatchMapping(value = { "edit_email/logincredentials/{id}", "edit_email/logincredentials/{id}/" })
    public void updateLoginCredentialEmail(@PathVariable("id") Long id, @RequestParam(name = "email") String newEmail) throws IllegalArgumentException{

        LoginCredential loginCredential = loginCredentialService.getLoginCredentialById(id);
        Customer customer = customerService.getCustomerByLoginCredential(loginCredential);

        loginCredentialService.updateLoginCredential(loginCredential.getUsername(), loginCredential.getPassword(), newEmail, customer, loginCredential);
    }

    @PatchMapping(value = { "edit_password/logincredentials/{id}", "edit_password/logincredentials/{id}/" })
    public void updateLoginCredentialPassword(@PathVariable("id") Long id, @RequestParam(name = "password") String newPassword) throws IllegalArgumentException{

        LoginCredential loginCredential = loginCredentialService.getLoginCredentialById(id);
        Customer customer = customerService.getCustomerByLoginCredential(loginCredential);

        loginCredentialService.updateLoginCredential(loginCredential.getUsername(), newPassword, loginCredential.getEmail(), customer, loginCredential);
    }

    public static LoginCredentialDto convertToDto (LoginCredential loginCredential) {

        if (loginCredential == null) throw new IllegalArgumentException("LoginCredential cannot be null");

        LoginCredentialDto loginCredentialDto = new LoginCredentialDto(loginCredential.getId(), loginCredential.getUsername(),
                                                    loginCredential.getPassword(), loginCredential.getEmail());

        return loginCredentialDto;

    }

}
