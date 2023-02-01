package ca.mcgill.ecse321.projectgroup14.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LoginCredentialRepository;

@Service
public class LoginCredentialService {

    public final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    public final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");

    @Autowired
	LoginCredentialRepository loginCredentialRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public LoginCredential createLoginCredential (String username, String password, String email, Customer customer) {

        // Parameter Verificaion

        if (username == null || username.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Username cannot be null");
        if (password == null || password.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Password cannot be null");
        if (!passwordPattern.matcher(password).matches() || password.length() <= 8) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Password needs to have at least 8 characters and include 1 lowercase letter, 1 uppercase letter and one number");
        if (email == null || email.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email cannot be null");
        if (!emailPattern.matcher(email).matches()) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email is invalid");
        if (customer == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer cannot be null");
        if (!customerRepository.existsById(customer.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer does not exist");
        if (loginCredentialRepository.existsByUsername(username)) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Username is taken");

        // Create Login

        LoginCredential loginCredential = new LoginCredential();
        loginCredential.setPassword(password);
        loginCredential.setUsername(username);
        loginCredential.setEmail(email);
        loginCredentialRepository.save(loginCredential);
        customer.setLoginCredential(loginCredential);
        customerRepository.save(customer);
        loginCredentialRepository.save(loginCredential);

        return loginCredential;

    }


    @Transactional
    public LoginCredential updateLoginCredential(String username, String password, String email, Customer customer, LoginCredential loginCredential) {

        // Parameter Verificaion

        if (username == null || username.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Username cannot be null");
        if (password == null || password.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Password cannot be null");
        if (!passwordPattern.matcher(password).matches() || password.length() <= 8) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Password is invalid");
        if (email == null || email.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email cannot be null");
        if (!emailPattern.matcher(email).matches()) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email is invalid");
        if (customer == null)throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer cannot be null");
        if (!customerRepository.existsById(customer.getId()))throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer does not exist");

        if (loginCredentialRepository.existsByUsername(username)){
            if (loginCredential.getUsername().equals(username)){

                loginCredential.setPassword(password);
                loginCredential.setUsername(username);
                loginCredential.setEmail(email);
                loginCredentialRepository.save(loginCredential);
                customer.setLoginCredential(loginCredential);

                customerRepository.save(customer);

                return loginCredential;

            }

            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Username is taken");
        }

        loginCredential.setPassword(password);
        loginCredential.setUsername(username);
        loginCredential.setEmail(email);
        loginCredentialRepository.save(loginCredential);
        customer.setLoginCredential(loginCredential);

        customerRepository.save(customer);

        return loginCredential;

    }

    @Transactional
    public LoginCredential deleteLoginCredential (LoginCredential login) {

        // Parameter Verification

        if (login == null) throw new IllegalArgumentException("Login cannot be null");
        if (!loginCredentialRepository.existsById(login.getId())) throw new IllegalArgumentException("Login does not exist");

        Customer customer = customerRepository.findByLoginCredential(login);

        customer.setLoginCredential(null);
        customerRepository.save(customer);

        // Delete Login
        loginCredentialRepository.delete(login);
        login = null;

        return login;
    }

    @Transactional
    public List<LoginCredential> getAllLoginCredentials () {

        return toList(loginCredentialRepository.findAll());

    }

    @Transactional
    public LoginCredential getLoginCredentialById (Long id) {

        // Parameter Verification

        if (!loginCredentialRepository.existsById(id)) throw new IllegalArgumentException("LoginCredential does not exist");

        // Find Login

        return loginCredentialRepository.findLoginCredentialById(id);

    }

    @Transactional
    public LoginCredential getLoginCredentialByUsername (String username) {

        // Parameter Verification

        if (username == null | username == "") throw new IllegalArgumentException("Username cannot be null");
        if (!loginCredentialRepository.existsByUsername(username)) throw new IllegalArgumentException("LoginCredential does not Exist");

        return loginCredentialRepository.findLoginCredentialByUsername(username);

    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
