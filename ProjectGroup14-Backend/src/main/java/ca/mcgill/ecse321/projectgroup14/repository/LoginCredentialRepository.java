package ca.mcgill.ecse321.projectgroup14.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;

public interface LoginCredentialRepository extends CrudRepository <LoginCredential, Long>{

	LoginCredential findLoginCredentialById (Long id);
    Iterable<LoginCredential> findAll();
    boolean existsByUsername(String username);
    LoginCredential findLoginCredentialByUsername (String username);

    boolean existsByEmail(String email);

}
