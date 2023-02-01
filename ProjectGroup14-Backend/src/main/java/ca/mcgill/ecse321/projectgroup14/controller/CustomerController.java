package ca.mcgill.ecse321.projectgroup14.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup14.dto.CustomerDto;
import ca.mcgill.ecse321.projectgroup14.dto.LoginCredentialDto;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.services.CustomerService;
import ca.mcgill.ecse321.projectgroup14.services.LoginCredentialService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    LoginCredentialService loginCredentialService;


    @PostMapping(value = { "/customers/{firstname}", "/customers/{firstname}/" })
    public CustomerDto createCustomer(@PathVariable(name = "firstname", required = false) String firstName, @RequestParam(name = "lastname", required = false) String lastName, @RequestParam(name = "address", required = false) String address, @RequestParam(name = "is_local") boolean isLocal){

        Customer customer = customerService.createCustomer(firstName, lastName, address, isLocal);

        return convertToDto(customer);

    }


    @GetMapping(value = { "/customers", "/customers/" })
    public List<CustomerDto> getCustomers(){

        return customerService.getAllCustomers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/customers/last", "/customers/last/" })
    public CustomerDto getLastCustomer() throws IllegalArgumentException{
        return convertToDto(customerService.getLastCustomer());
    }


    @GetMapping(value = { "/customers/{id}", "/customers/{id}/" })
    public CustomerDto getCustomerById(@PathVariable("id") Long id){

        return convertToDto(customerService.getCustomerById(id));

    }

    @DeleteMapping(value = { "customers/delete/{id}", "customers/delete/{id}/" })
    public void deleteCustomer(@PathVariable("id") Long id){

        Customer customer = customerService.getCustomerById(id);

        customerService.deleteCustomer(customer);
    }

    @PatchMapping(value = { "customers/edit_firstname/{id}", "customers/edit_firstname/{id}/" })
    public void updateCustomerFirstname(@PathVariable("id") Long id, @RequestParam(name = "firstname") String newFirstname){

        Customer customer = customerService.getCustomerById(id);

        customerService.updateCustomer(customer, newFirstname, customer.getLastName(), customer.getAddress(), customer.getIsLocal());
    }


    @PatchMapping(value = { "customers/edit_lastname/{id}", "customers/edit_lastname/{id}/" })
    public void updateCustomerLastname(@PathVariable("id") Long id, @RequestParam(name = "lastname") String newLastname){

        Customer customer = customerService.getCustomerById(id);

        customerService.updateCustomer(customer, customer.getFirstName(), newLastname, customer.getAddress(), customer.getIsLocal());
    }

    @PatchMapping(value = { "customers/edit_address/{id}", "customers/edit_address/{id}/" })
    public void updateCustomerAddress(@PathVariable("id") Long id, @RequestParam(name = "address") String newAddress){

        Customer customer = customerService.getCustomerById(id);

        customerService.updateCustomer(customer, customer.getFirstName(), customer.getLastName(), newAddress, customer.getIsLocal());
    }


    public LoginCredentialDto convertToDto(LoginCredential loginCredential){
        if (loginCredential == null) {
            throw new IllegalArgumentException("Login is null");
        }

        LoginCredentialDto loginCredentialDto = new LoginCredentialDto(loginCredential.getId(),
                                                    loginCredential.getUsername(),
                                                    loginCredential.getPassword(),
                                                    loginCredential.getEmail());

        return loginCredentialDto;
    }


    


    @PatchMapping(value = { "edit_local/customers/{id}", "edit_local/customers/{id}/" })
    public void updateCustomerIsLocal(@PathVariable("id") Long id, @RequestParam(name = "is_local") boolean newLocal){

        Customer customer = customerService.getCustomerById(id);

        customerService.updateCustomer(customer, customer.getFirstName(), customer.getLastName(), customer.getAddress(), newLocal);
    }

    public static CustomerDto convertToDto (Customer customer) {

        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");

        CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getIsLocal());

        if (customer.getLoginCredential() != null){
            customerDto.setLoginCredential(LoginCredentialController.convertToDto(customer.getLoginCredential()));
        }

        return customerDto;

    }


}
