package ca.mcgill.ecse321.projectgroup14.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.projectgroup14.dto.BookingDto;
import ca.mcgill.ecse321.projectgroup14.dto.BookingDto.BookingType;
import ca.mcgill.ecse321.projectgroup14.model.Booking;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Item;
import ca.mcgill.ecse321.projectgroup14.services.BookingService;
import ca.mcgill.ecse321.projectgroup14.services.CustomerService;
import ca.mcgill.ecse321.projectgroup14.services.ItemService;

@CrossOrigin(origins = "*")
@RestController
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    @GetMapping(value = { "/bookings", "/bookings/" })
    public List<BookingDto> getAllBookings(){
        return service.getAllBookings().stream().map(booking -> convertToDto(booking)).collect(Collectors.toList());
    }

    @PostMapping(value = { "/booking/{customerId}", "/booking/{customerId}"})
    public BookingDto createBooking(
    @PathVariable(name = "customerId") Long customerId,
    @RequestParam(name = "bookingType") String bookingType,
    @RequestParam(name = "item") Long itemId)
    throws IllegalArgumentException{

        Customer customer = customerService.getCustomerById(customerId);
        Item item = itemService.getItemById(itemId);
        Booking booking = service.createBooking(bookingType, customer, item);
        return convertToDto(booking);

    }

    @DeleteMapping(value = { "/bookings/delete/{bookingId}", "/bookings/delete/{bookingId}/" })
    public void deleteBooking(@PathVariable ("bookingId") Long bookingId) throws IllegalArgumentException {

        service.deleteBooking(service.getBookingById(bookingId));

    }

    @PatchMapping(value = { "/bookings/renew/{bookingId}", "/bookings/renew/{bookingId}/" })
	public void renewCheckout(@PathVariable("bookingId") Long bookingId) throws IllegalArgumentException {

        service.renewCheckout(service.getBookingById(bookingId));

	}

    @GetMapping(value = { "/booking/{bookingId}", "/booking/{bookingId}/" })
    public BookingDto getBookingById(@PathVariable("bookingId") Long bookingId) throws IllegalArgumentException{

        return convertToDto(service.getBookingById(bookingId));

    }

    @GetMapping(value = { "/bookings/customer/{customerId}", "/bookings/customer/{customerId}/" })
    public List<BookingDto> getBookingsByCustomer(@PathVariable("customerId") Long customerId) throws IllegalArgumentException{

        Customer customer = customerService.getCustomerById(customerId);
        List<Booking> bookings = service.getBookingsByCustomer(customer);
        List<BookingDto> bookingDtos = new ArrayList<BookingDto> ();
        for (Booking b : bookings) bookingDtos.add(convertToDto(b));
        return bookingDtos;

    }

    @GetMapping(value = { "/bookings/item/{itemId}", "/bookings/item/{itemId}/" })
    public BookingDto getBookingsByItem(@PathVariable("itemId") Long itemId) throws IllegalArgumentException{

        Item item = itemService.getItemById(itemId);
        return convertToDto(service.getBookingsByItem(item));

    }

    @GetMapping(value = { "/bookings/{bookingType}", "/bookings/{bookingType}/" })
    public List<BookingDto> getBookingsByType(@PathVariable("bookingType") String bookingType) throws IllegalArgumentException{

        List<Booking> bookings = service.getBookingsByType(bookingType);
        List<BookingDto> bookingDtos = new ArrayList<BookingDto>();
        for (Booking b : bookings) bookingDtos.add(convertToDto(b));
        return bookingDtos;

    }

    @PatchMapping(value = {"bookings/checkout/{bookingId}", "bookings/checkout/{bookingId}/" })
    public BookingDto reservationToCheckout(
    @PathVariable("bookingId") Long bookingId) throws IllegalArgumentException{
        Booking booking = service.getBookingById(bookingId);
        return convertToDto(service.reservationToCheckout(booking));
    }

    @GetMapping(value = { "/bookings/date/{startDate}", "/bookings/date/{startDate}/" })
    public List<BookingDto> getBookingsByDate(
    @PathVariable("startDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate startDate)
    throws IllegalArgumentException{


        List<Booking> bookings = service.getBookingsByStartDate(Date.valueOf(startDate));
        List<BookingDto> bookingDtos = new ArrayList<BookingDto>();
        for (Booking b : bookings) bookingDtos.add(convertToDto(b));
        return bookingDtos;

    }

    public static BookingDto convertToDto (Booking booking) {

        if (booking == null) throw new IllegalArgumentException("Booking cannot be null");

        BookingDto bookingDto = new BookingDto(booking.getId(), BookingType.valueOf(booking.getBookingType().toString()),
                                booking.getStartDate(), booking.getEndDate(), ItemController.convertToDto(booking.getItem()),
                                CustomerController.convertToDto(booking.getCustomer()));

        return bookingDto;

    }

}
