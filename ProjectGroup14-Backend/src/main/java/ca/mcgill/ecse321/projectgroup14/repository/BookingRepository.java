package ca.mcgill.ecse321.projectgroup14.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.model.Booking.BookingType;

public interface BookingRepository extends CrudRepository <Booking, Long>{

	Booking findBookingById (Long id);
	Booking findByItem(Item item);
	List<Booking> findByCustomer(Customer customer);
	List<Booking> findByBookingType(BookingType bookingType);
	List<Booking> findByStartDate(Date startDate);

}
