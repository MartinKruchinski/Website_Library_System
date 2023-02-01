package ca.mcgill.ecse321.projectgroup14.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;

public interface ShiftRepository extends CrudRepository <Shift, Long>{

	Shift findShiftById (Long id);

	List<Shift> findAll();

	List<Shift> findShiftsByLibrarianId(Long id);

}
