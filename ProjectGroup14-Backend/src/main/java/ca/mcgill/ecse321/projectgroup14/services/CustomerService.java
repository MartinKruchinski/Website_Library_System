package ca.mcgill.ecse321.projectgroup14.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LoginCredentialRepository;

@Service
public class CustomerService {

    @Autowired
	CustomerRepository customerRepository;

    @Autowired
    LoginCredentialRepository loginCredentialRepository;

    @Transactional
    public Customer createCustomer (String firstname, String lastname, String address, boolean isLocal) {

        // Parameter Verification

        if (firstname == null || firstname.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Firstname cannot be null");
        if (lastname == null || lastname.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Lastname cannot be null");
        if (address == null || address.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Address cannot be null");

        // Create Customer

        Customer customer = new Customer();
        customer.setFirstName(firstname);
        customer.setLastName(lastname);
        customer.setAddress(address);
        customer.setIsLocal(isLocal);
        customerRepository.save(customer);

        return customer;

    }

    @Transactional
    public Customer updateCustomer (Customer customer, String firstname, String lastname, String address, Boolean isLocal) {

        // Parameter Verification
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");
        if (firstname == null || firstname.trim() == "") throw new IllegalArgumentException("Firstname cannot be null");
        if (lastname == null || lastname.trim() == "") throw new IllegalArgumentException("Lastname cannot be null");
        if (address == null || address.trim() == "") throw new IllegalArgumentException("Address cannot be null");
        if (isLocal == null) throw new IllegalArgumentException("Is local cannot be null");

        // Update Library

        customer.setFirstName(firstname);
        customer.setLastName(lastname);
        customer.setAddress(address);
        customer.setIsLocal(isLocal);
        customerRepository.save(customer);

        return customer;

    }

    @Transactional
    public Customer deleteCustomer (Customer customer) {

        // Parameter Verification

        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");
        if (!customerRepository.existsById(customer.getId())) throw new IllegalArgumentException("Customer does not exist");

        // Delete Customer and Login

        if (customer.getLoginCredential() != null){

            LoginCredential login = customer.getLoginCredential();

            customerRepository.delete(customer);
            loginCredentialRepository.delete(login);
            customer = null;
            login = null;
            return customer;
        }

        customerRepository.delete(customer);

        customer = null;

        return customer;

    }

    @Transactional
    public List<Customer> getAllCustomers () {

        return toList(customerRepository.findAll());

    }

    @Transactional
    public Customer getLastCustomer(){
        if(toList(customerRepository.findAll()).size() > 0){
            List<Customer> customers = toList(customerRepository.findAll());
            return customers.get(customers.size() -1);
        }
        else throw new IllegalArgumentException("The library has no customers");
    }

    @Transactional
    public Customer getCustomerById (Long id) {

        // Parameter Verification

        if (!customerRepository.existsById(id)) throw new IllegalArgumentException("Customer does not exist");

        // Find Customer

        return customerRepository.findCustomerById(id);

    }

    @Transactional
    public Customer getCustomerByLoginCredentialUsername (String username) {

        // Parameter Verification

        if (username == null || username.trim() == "") throw new IllegalArgumentException("Username cannot be null");

        // Find Customer

        return customerRepository.findByLoginCredentialUsername(username);

    }

    @Transactional
    public Customer getCustomerByLoginCredential (LoginCredential login) {

        // Parameter Verification

        if (login == null) throw new IllegalArgumentException("Login cannot be null");
        if (!loginCredentialRepository.existsById(login.getId())) throw new IllegalArgumentException("Customer does not exist");

        return customerRepository.findByLoginCredential(login);

    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
