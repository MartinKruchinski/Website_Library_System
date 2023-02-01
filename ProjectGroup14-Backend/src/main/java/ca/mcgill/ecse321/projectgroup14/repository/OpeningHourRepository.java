package ca.mcgill.ecse321.projectgroup14.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;

public interface OpeningHourRepository extends CrudRepository <OpeningHour, Long>{

	OpeningHour findOpeningHourById (Long id);

}
