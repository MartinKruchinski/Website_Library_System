package ca.mcgill.ecse321.projectgroup14.repository;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;

public interface EventRepository extends CrudRepository <Event, Long>{

	Event findEventById (Long id);

    Event findByCustomer(Customer customer);

    Event findByDate(Date date);

}
