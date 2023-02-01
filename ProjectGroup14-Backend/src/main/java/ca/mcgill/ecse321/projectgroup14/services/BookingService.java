package ca.mcgill.ecse321.projectgroup14.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Booking;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Item;
import ca.mcgill.ecse321.projectgroup14.model.Booking.BookingType;
import ca.mcgill.ecse321.projectgroup14.repository.BookingRepository;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.ItemRepository;

@Service
public class BookingService {

    @Autowired
	private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Booking createBooking (String bookingTypeString, Customer customer, Item item) {

        // Parameter Verification

        if (bookingTypeString == null || bookingTypeString == "") {
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"BookingType cannot be null");
        }
        else if(!bookingTypeString.equals("Reservation") && !bookingTypeString.equals("Checkout")){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"BookingType does not exist");
        }
        if (customer == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer cannot be null");
        if (!customerRepository.existsById(customer.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer does not exist");
        if (item == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Item cannot be null");
        if (!itemRepository.existsById(item.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Item does not exist");
        if (!item.getIsAvailable()) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Item cannot be checked out");
        if (bookingRepository.findByItem(item) != null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Item is already booked");

        BookingType bookingType = BookingType.valueOf(bookingTypeString);

        // Create Date

        Date startDate = Date.valueOf(LocalDate.now());
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        if (bookingType == BookingType.Reservation)
            cal.add(Calendar.DATE, 3);
        else
            cal.add(Calendar.DATE, 14);
        Date endDate = new java.sql.Date(cal.getTimeInMillis());

        // Create Booking

        Booking booking = new Booking();
        booking.setBookingType(bookingType);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setCustomer(customer);
        item.setIsAvailable(false);
        itemRepository.save(item);
        booking.setItem(item);
        bookingRepository.save(booking);

        return booking;

    }

    @Transactional
    public Booking reservationToCheckout (Booking booking) {

        // Parameter Verification

        if (booking == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking cannot be null");
        if (!bookingRepository.existsById(booking.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking does not exist");

        // Create Date

        Date startDate = Date.valueOf(LocalDate.now());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);
        Date endDate = new java.sql.Date(cal.getTimeInMillis());

        // Update Booking

        booking.setBookingType(BookingType.Checkout);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        bookingRepository.save(booking);

        return booking;

    }

    @Transactional
    public Booking renewCheckout (Booking booking) {

        // Parameter Verification

        if (booking == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking cannot be null");
        if (!bookingRepository.existsById(booking.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking does not exist");
        if (booking.getBookingType() == BookingType.Reservation) throw new WrongInputException(HttpStatus.BAD_REQUEST,"A reservation cannot be renewed");

        // Create Date

        Calendar cal = Calendar.getInstance();
        cal.setTime(booking.getEndDate());
        cal.add(Calendar.DATE, 14);
        Date endDate = new java.sql.Date(cal.getTimeInMillis());

        // Update Booking

        booking.setEndDate(endDate);
        bookingRepository.save(booking);

        return booking;

    }

    @Transactional
    public Booking deleteBooking (Booking booking) {

        // Parameter Verification

        if (booking == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking cannot be null");
        if (!bookingRepository.existsById(booking.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking does not exist");

        // Delete Booking
        itemRepository.findItemById(booking.getItem().getId()).setIsAvailable(true);
        bookingRepository.delete(booking);
        booking = null;

        return booking;

    }

    @Transactional
    public List<Booking> getAllBookings () {

        return toList(bookingRepository.findAll());

    }

    @Transactional
    public Booking getBookingById (Long id) {

        // Parameter Verification

        if (id == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Id cannot be null");
        if (!bookingRepository.existsById(id)) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking does not exist");

        // Find Booking

        return bookingRepository.findBookingById(id);

    }

    @Transactional
    public List<Booking> getBookingsByCustomer (Customer customer) {

        // Parameter Verification

        if (customer == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer cannot be null");
        if (!customerRepository.existsById(customer.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Customer does not exist");

        // Find Bookings

        return bookingRepository.findByCustomer(customer);

    }

    @Transactional
    public Booking getBookingsByItem(Item item){

        // Parameter Verification

        if (item == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Item cannot be null");
        if (!itemRepository.existsById(item.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Item does not exist");

        // Find Bookings

        return bookingRepository.findByItem(item);

    }


    @Transactional
    public List<Booking> getBookingsByType (String bookingTypeString) {

        // Parameter Verification

        if (bookingTypeString == null || bookingTypeString == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Booking Type cannot be null");

        BookingType bookingType = BookingType.valueOf(bookingTypeString);

        // Find Bookings

        return bookingRepository.findByBookingType(bookingType);

    }

    @Transactional
    public List<Booking> getBookingsByStartDate (Date startDate) {

        // Parameter Verification

        if (startDate == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Date cannot be null");

        // Find Bookings

        return bookingRepository.findByStartDate(startDate);

    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
