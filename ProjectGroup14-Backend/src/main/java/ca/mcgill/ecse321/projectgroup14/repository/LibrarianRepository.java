package ca.mcgill.ecse321.projectgroup14.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;

public interface LibrarianRepository extends CrudRepository <Librarian, Long>{

	Librarian findLibrarianById (Long id);

	Librarian findLibrarianByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
