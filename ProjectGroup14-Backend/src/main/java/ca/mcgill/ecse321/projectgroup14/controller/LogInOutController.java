package ca.mcgill.ecse321.projectgroup14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup14.dto.CustomerDto;
import ca.mcgill.ecse321.projectgroup14.dto.LibrarianDto;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.services.LogInOutService;

@CrossOrigin(origins = "*")
@RestController
public class LogInOutController {
    @Autowired
    LogInOutService logInOutService;

    @PostMapping(value = { "/login/customer/{username}", "/login/customer/{username}/" })
    public ResponseEntity<?> loginCustomer (@PathVariable(name = "username") String username, @RequestParam(name = "password") String password) throws IllegalArgumentException{
        try{
            logInOutService.loginCustomer(username, password);
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = { "/login/librarian/{username}", "/Login/librarian/{username}/" })
    public ResponseEntity<?> loginLibrarian (@PathVariable(name = "username") String username, @RequestParam(name = "password") String password) throws IllegalArgumentException{
        try {
            convertToDto(logInOutService.loginLibrarian(username, password));
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = { "/logout/librarian", "/logout/librarian/" })
    public LibrarianDto logOutLibrarian () throws IllegalArgumentException{
        return convertToDto(logInOutService.logOutLibrarian());
    }

    @PostMapping(value = { "/logout/customer", "/logout/customer/" })
    public CustomerDto logOutCustomer () throws IllegalArgumentException{
        return convertToDto(logInOutService.logOutCustomer());
    }

    @GetMapping(value = {"/login/customer", "/login/customer/"})
    public CustomerDto getLoggedCustomer () throws IllegalArgumentException{
        return convertToDto(logInOutService.getLoggedCustomer());
    }

    @GetMapping(value = {"/login/customer/username", "/login/customer/username"})
    public String getLoggedCustomerName () throws IllegalArgumentException{
        return logInOutService.getLoggedCustomer().getLoginCredential().getUsername();
    }

    @GetMapping(value = {"/login/librarian", "/login/librarian/"})
    public LibrarianDto getLoggedLibrarian () throws IllegalArgumentException{
        return convertToDto(logInOutService.getLoggedLibrarian());
    }

    public static CustomerDto convertToDto (Customer customer) {
        if(customer == null){
            CustomerDto dto = new CustomerDto();
            return dto;
        }
        CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getIsLocal());

        if (customer.getLoginCredential() != null){
            customerDto.setLoginCredential(LoginCredentialController.convertToDto(customer.getLoginCredential()));
        }

        return customerDto;

    }

    public static LibrarianDto convertToDto(Librarian librarian){
        if(librarian == null){
            LibrarianDto dto = new LibrarianDto();
            return dto;
        }
        LibrarianDto librarianDto = new LibrarianDto(librarian.getId(),
                                                    librarian.getFirstName(),
                                                    librarian.getLastName(),
                                                    librarian.getEmail(),
                                                    librarian.getIsHead(),
                                                    librarian.getUsername(),
                                                    librarian.getPassword());

        return librarianDto;
    }

}
