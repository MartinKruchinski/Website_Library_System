package ca.mcgill.ecse321.projectgroup14.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LibrarianRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LoginCredentialRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogInOutService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LoginCredentialRepository loginCredentialRepository;

    @Autowired
    LibrarianRepository librarianRepository;

    private  Customer cus = null;
    private  Librarian lib = null;


    @Transactional
    public Customer loginCustomer (String username, String password){
        if (username == null || username.trim() == "") throw new IllegalArgumentException("The username is invalid");
        if (password == null || password.trim() == "") throw new IllegalArgumentException("The password is invalid");
        if(loginCredentialRepository.findLoginCredentialByUsername(username) == null){
            throw new IllegalArgumentException("We couldn't find your library account, please try a different username");
        }
        LoginCredential credential = loginCredentialRepository.findLoginCredentialByUsername(username);
        if(!credential.getPassword().equals(password)){
            System.out.println(password);
            System.out.println(credential.getPassword());
            throw new IllegalArgumentException("Incorrect password");
        }
        if(customerRepository.findByLoginCredential(credential) != null){
            this.cus = customerRepository.findByLoginCredential(credential);
            return customerRepository.findByLoginCredential(credential);
        }
        else throw new IllegalArgumentException("We couldn't find your library account, please try a different username");
    }


    @Transactional
    public Librarian loginLibrarian (String username, String password){
        if (username == null || username.trim() == "") throw new IllegalArgumentException("The username is invalid");
        if (password == null || password.trim() == "") throw new IllegalArgumentException("The password is invalid");
        if(librarianRepository.findLibrarianByUsername(username) == null){
            throw new IllegalArgumentException("We couldn't find your library account, please try a different username");
        }
        Librarian librarian = librarianRepository.findLibrarianByUsername(username);
        if(!librarian.getPassword().equals(password)){
            throw new IllegalArgumentException("Incorrect password");
        }
        this.lib = librarian;
        return librarian;
    }

    @Transactional
    public Customer logOutCustomer(){
        cus = null;
        return cus;
    }

    @Transactional
    public Librarian logOutLibrarian(){
        lib = null;
        return lib;
    }

    @Transactional
    public Librarian getLoggedLibrarian(){
        return lib;
    }

    @Transactional
    public Customer getLoggedCustomer(){
        return cus;
    }
 
}
