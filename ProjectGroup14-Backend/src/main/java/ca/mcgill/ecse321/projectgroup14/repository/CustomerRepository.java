package ca.mcgill.ecse321.projectgroup14.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;

public interface CustomerRepository extends CrudRepository <Customer, Long>{

	Customer findCustomerById (Long id);
	Customer findByLoginCredential (LoginCredential login);
	Customer findByLoginCredentialUsername(String username);
	Boolean existsByLoginCredentialUsername (String username);

}
