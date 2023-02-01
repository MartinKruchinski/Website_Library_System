package ca.mcgill.ecse321.projectgroup14.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.projectgroup14.model.Booking;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Item;
import ca.mcgill.ecse321.projectgroup14.model.Booking.BookingType;
import ca.mcgill.ecse321.projectgroup14.model.Item.ItemType;
import ca.mcgill.ecse321.projectgroup14.repository.BookingRepository;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;



@ExtendWith(MockitoExtension.class)
public class TestBookingService {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BookingService service;

    private static final Long BOOKING_ID = 12345l;
    private static final String BOOKING_TYPE = "Reservation";
    private static final Date BOOKING_START_DATE = Date.valueOf("2021-10-10");
    private static final Date BOOKING_END_DATE = Date.valueOf("2021-11-11");
    private static final String CUSTOMER_NAME = "Martin";
    private static final String CUSTOMER_LAST_NAME = "Kruchinski";
    private static final String CUSTOMER_ADDRESS = "Stanley St";
    private static final String ITEM_AUTHOR = "Marwan";
    private static final String ITEM_TITLE = "Math";
    private static final ItemType ITEM_TYPE = ItemType.Book;
    private static final Long CUSTOMER_ID = 0123l;
    private static final Long ITEM_ID = 01234l;

    private static final String BOOKING_TYPE_2 = "Checkout";
    private static final Date BOOKING_START_DATE_2 = Date.valueOf("2021-10-15");
    private static final Date BOOKING_END_DATE_2 = Date.valueOf("2021-11-11");

    //Booking to renew
    private static final Long BOOKING_ID_RENEW = 1234567l;

    private Customer CUSTOMER_TEST = new Customer();


    //Booking to find by item
    private static Item ITEM_TO_FIND = new Item();
    private static Customer CUSTOMER_3 = new Customer();
    private static Item ITEM_TO_FIND_CHECKOUT = new Item();



    @BeforeEach
    public void setMockOutput() {

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(itemRepository.save(any(Item.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(bookingRepository.save(any(Booking.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(bookingRepository.findBookingById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOKING_ID)) {

                Booking booking = new Booking();
                booking.setBookingType(getBookingType(BOOKING_TYPE));
                booking.setStartDate(BOOKING_START_DATE);
                booking.setEndDate(BOOKING_END_DATE);
                lenient().when(customerRepository.existsById(any())).thenReturn(true);
                Customer customer = new Customer();
                customer.setFirstName(CUSTOMER_NAME);
                customer.setLastName(CUSTOMER_LAST_NAME);
                customer.setAddress(CUSTOMER_ADDRESS);
                customer.setIsLocal(true);
                customer.setId(CUSTOMER_ID);
                booking.setCustomer(customer);

                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(ITEM_TYPE);
                item.setId(ITEM_ID);
                booking.setItem(item);

				return booking;
			} else if(invocation.getArgument(0).equals(BOOKING_ID_RENEW)){
				Booking booking = new Booking();
                booking.setBookingType(getBookingType("Checkout"));
                booking.setStartDate(Date.valueOf("2021-11-01"));
                booking.setEndDate(Date.valueOf("2021-11-29"));
                lenient().when(customerRepository.existsById(any())).thenReturn(true);
                Customer customer = new Customer();
                customer.setFirstName(CUSTOMER_NAME);
                customer.setLastName(CUSTOMER_LAST_NAME);
                customer.setAddress(CUSTOMER_ADDRESS);
                customer.setIsLocal(true);
                customer.setId(CUSTOMER_ID);
                booking.setCustomer(customer);

                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(ITEM_TYPE);
                item.setId(ITEM_ID);
                booking.setItem(item);

                return booking;
			 }
            else {
                Booking booking = null;
                return booking;
            }
		});

        //MOCK FIND BY ITEM
        lenient().when(bookingRepository.findByItem(any())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ITEM_TO_FIND)) {
                Booking booking = new Booking();
                String bookingType = "Reservation";
                CUSTOMER_3.setAddress("address");
                CUSTOMER_3.setFirstName("firstName");
                CUSTOMER_3.setIsLocal(true);
                CUSTOMER_3.setLastName("lastName");
                ITEM_TO_FIND.setAuthor("author");
                ITEM_TO_FIND.setIsAvailable(false); //to indicate there is already a reservation in the item
                ITEM_TO_FIND.setItemType(ItemType.Book);
                ITEM_TO_FIND.setTitle("title");
                booking.setBookingType(getBookingType(bookingType));
                booking.setCustomer(CUSTOMER_3);
                booking.setItem(ITEM_TO_FIND);
                return booking;

            }
            else if(invocation.getArgument(0).equals(ITEM_TO_FIND_CHECKOUT)){
                Booking booking4 = new Booking();
                String bookingType4 = "Checkout";
                CUSTOMER_3.setAddress("address");
                CUSTOMER_3.setFirstName("firstName");
                CUSTOMER_3.setIsLocal(true);
                CUSTOMER_3.setLastName("lastName");
                ITEM_TO_FIND_CHECKOUT.setAuthor("author");
                ITEM_TO_FIND_CHECKOUT.setIsAvailable(false); //to indicate there is already a reservation in the item
                ITEM_TO_FIND_CHECKOUT.setItemType(ItemType.Book);
                ITEM_TO_FIND_CHECKOUT.setTitle("title");
                booking4.setBookingType(getBookingType(bookingType4));
                booking4.setCustomer(CUSTOMER_3);
                booking4.setItem(ITEM_TO_FIND_CHECKOUT);
                return booking4;
            }
            else return null;
        });

        //MOCK FIND BY CUSTOMER
        lenient().when(bookingRepository.findByCustomer(CUSTOMER_TEST)).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CUSTOMER_TEST)) {
				List<Booking> bookings = new ArrayList<Booking>();

                Booking booking = new Booking();
                booking.setBookingType(getBookingType(BOOKING_TYPE));
                booking.setStartDate(BOOKING_START_DATE);
                booking.setEndDate(BOOKING_END_DATE);
                Customer customer = CUSTOMER_TEST;
                customer.setFirstName(CUSTOMER_NAME);
                customer.setLastName(CUSTOMER_LAST_NAME);
                customer.setAddress(CUSTOMER_ADDRESS);
                customer.setIsLocal(true);
                customer.setId(CUSTOMER_ID);
                booking.setCustomer(customer);
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(ITEM_TYPE);
                item.setId(ITEM_ID);
                booking.setItem(item);
                bookings.add(booking);
				return bookings;
			} else {
				return null;
			}
		});
        lenient().when(bookingRepository.findByBookingType(getBookingType(BOOKING_TYPE))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(getBookingType(BOOKING_TYPE))) {
				List<Booking> bookings = new ArrayList<Booking>();

                Booking booking = new Booking();
                booking.setBookingType(getBookingType(BOOKING_TYPE));
                booking.setStartDate(BOOKING_START_DATE);
                booking.setEndDate(BOOKING_END_DATE);
                Customer customer = CUSTOMER_TEST;
                customer.setFirstName(CUSTOMER_NAME);
                customer.setLastName(CUSTOMER_LAST_NAME);
                customer.setAddress(CUSTOMER_ADDRESS);
                customer.setIsLocal(true);
                customer.setId(CUSTOMER_ID);
                booking.setCustomer(customer);
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(ITEM_TYPE);
                item.setId(ITEM_ID);
                booking.setItem(item);
                bookings.add(booking);
				return bookings;
			} else {
				return null;
			}
		});

        lenient().when(bookingRepository.findByStartDate(BOOKING_START_DATE)).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOKING_START_DATE)) {
				List<Booking> bookings = new ArrayList<Booking>();

                Booking booking = new Booking();
                booking.setBookingType(getBookingType(BOOKING_TYPE));
                booking.setStartDate(BOOKING_START_DATE);
                booking.setEndDate(BOOKING_END_DATE);
                Customer customer = CUSTOMER_TEST;
                customer.setFirstName(CUSTOMER_NAME);
                customer.setLastName(CUSTOMER_LAST_NAME);
                customer.setAddress(CUSTOMER_ADDRESS);
                customer.setIsLocal(true);
                customer.setId(CUSTOMER_ID);
                booking.setCustomer(customer);
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(ITEM_TYPE);
                item.setId(ITEM_ID);
                booking.setItem(item);
                bookings.add(booking);
				return bookings;
			} else {
				return null;
			}
		});

        lenient().when(bookingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
				List<Booking> bookings = new ArrayList<Booking>();
                //booking 1
                Booking booking = new Booking();
                booking.setBookingType(getBookingType(BOOKING_TYPE));
                booking.setStartDate(BOOKING_START_DATE);
                booking.setEndDate(BOOKING_END_DATE);
                lenient().when(customerRepository.existsById(any())).thenReturn(true);
                Customer customer = CUSTOMER_TEST;
                customer.setFirstName(CUSTOMER_NAME);
                customer.setLastName(CUSTOMER_LAST_NAME);
                customer.setAddress(CUSTOMER_ADDRESS);
                customer.setIsLocal(true);
                customer.setId(CUSTOMER_ID);
                booking.setCustomer(customer);
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(ITEM_TYPE);
                item.setId(ITEM_ID);
                booking.setItem(item);
                bookings.add(booking);
                //booking 2
                Booking booking2 = new Booking();
                booking2.setBookingType(getBookingType(BOOKING_TYPE_2));
                booking2.setStartDate(BOOKING_START_DATE_2);
                booking2.setEndDate(BOOKING_END_DATE_2);
                booking2.setCustomer(customer);
                booking2.setItem(item);
                bookings.add(booking2);

                Booking Booking3 = new Booking();
                String bookingType3 = "Reservation";
                CUSTOMER_3.setAddress("address");
                CUSTOMER_3.setFirstName("firstName");
                CUSTOMER_3.setIsLocal(true);
                CUSTOMER_3.setLastName("lastName");
                CUSTOMER_3.setId(565l);
                ITEM_TO_FIND.setAuthor("author");
                ITEM_TO_FIND.setIsAvailable(false); //to indicate there is already a reservation in the item
                ITEM_TO_FIND.setItemType(ItemType.Book);
                ITEM_TO_FIND.setTitle("title");
                ITEM_TO_FIND.setId(787l);
                Booking3.setBookingType(getBookingType(bookingType3));
                Booking3.setCustomer(CUSTOMER_3);
                Booking3.setItem(ITEM_TO_FIND);
                bookings.add(Booking3);

                Booking booking4 = new Booking();
                String bookingType4 = "Checkout";
                CUSTOMER_3.setAddress("address");
                CUSTOMER_3.setFirstName("firstName");
                CUSTOMER_3.setIsLocal(true);
                CUSTOMER_3.setLastName("lastName");
                ITEM_TO_FIND_CHECKOUT.setAuthor("author");
                ITEM_TO_FIND_CHECKOUT.setIsAvailable(false); //to indicate there is already a reservation in the item
                ITEM_TO_FIND_CHECKOUT.setItemType(ItemType.Book);
                ITEM_TO_FIND_CHECKOUT.setTitle("title");
                ITEM_TO_FIND_CHECKOUT.setId(333l);
                booking4.setBookingType(getBookingType(bookingType4));
                booking4.setCustomer(CUSTOMER_3);
                booking4.setItem(ITEM_TO_FIND_CHECKOUT);
                bookings.add(booking4);


				return bookings;

		});
    }


    @Test
    public void testCreateBooking(){
        //Fields to create booking
        String bookingType = "Reservation";
        Customer customer = createCustomer("adress", "firstName", "lastName", true);
        Item item = new Item();
        item.setAuthor("author");
        item.setIsAvailable(true);
        item.setItemType(ItemType.Book);
        item.setTitle("title");

        itemRepository.save(item);
        Date startDate = Date.valueOf(LocalDate.now());
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, 3);
        Date endDate = new java.sql.Date(cal.getTimeInMillis());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, customer, item);
        } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(booking);
        assertEquals(getBookingType(bookingType), booking.getBookingType());
        assertEquals(customer, booking.getCustomer());
        assertEquals(item, booking.getItem());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(endDate, booking.getEndDate());
    }


@Test
public void testReservationToCheckout(){
    //Fields to create booking
    String bookingType = "Reservation";
    CUSTOMER_3.setAddress("address");
    CUSTOMER_3.setFirstName("firstName");
    CUSTOMER_3.setIsLocal(true);
    CUSTOMER_3.setLastName("lastName");
    ITEM_TO_FIND.setAuthor("author");
    ITEM_TO_FIND.setIsAvailable(false); //to indicate there is already a reservation in the item
    ITEM_TO_FIND.setItemType(ItemType.Book);
    ITEM_TO_FIND.setTitle("title");
    lenient().when(customerRepository.existsById(any())).thenReturn(true);
    lenient().when(itemRepository.existsById(any())).thenReturn(true);
    lenient().when(bookingRepository.existsById(any())).thenReturn(true);

    Booking booking = new Booking();
    booking.setBookingType(BookingType.valueOf(bookingType));
    booking.setCustomer(CUSTOMER_3);
    booking.setItem(ITEM_TO_FIND);
    try{
        booking = service.reservationToCheckout(booking);
    } catch (IllegalArgumentException e){ //check that no error occurred
        fail();
    }
        assertNotNull(booking);
        assertEquals(BookingType.valueOf("Checkout"), booking.getBookingType());
        assertEquals(CUSTOMER_3, booking.getCustomer());
        assertEquals(ITEM_TO_FIND, booking.getItem());

}


    @Test
    public void testCreateReservationItemIsNotAvailable(){
        //Fields to create booking
        String error = null;
        String bookingType = "Reservation";
        CUSTOMER_3.setAddress("address");
        CUSTOMER_3.setFirstName("firstName");
        CUSTOMER_3.setIsLocal(true);
        CUSTOMER_3.setLastName("lastName");
        ITEM_TO_FIND.setAuthor("author");
        ITEM_TO_FIND.setIsAvailable(false); //to indicate there is already a reservation in the item
        ITEM_TO_FIND.setItemType(ItemType.Book);
        ITEM_TO_FIND.setTitle("title");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, CUSTOMER_3, ITEM_TO_FIND);
        } catch (IllegalArgumentException e){ //check that no error occurred
           error = e.getMessage();
        }
        assertNull(booking);
        assertEquals("Item cannot be checked out", error);

    }

    @Test
    public void testCreateBookingCheckoutExists(){
        //Fields to create booking
        String error = null;
        String bookingType = "Checkout";
        CUSTOMER_3.setAddress("address");
        CUSTOMER_3.setFirstName("firstName");
        CUSTOMER_3.setIsLocal(true);
        CUSTOMER_3.setLastName("lastName");
        ITEM_TO_FIND_CHECKOUT.setAuthor("author");
        ITEM_TO_FIND_CHECKOUT.setIsAvailable(false); //to indicate there is already a reservation in the item
        ITEM_TO_FIND_CHECKOUT.setItemType(ItemType.Book);
        ITEM_TO_FIND_CHECKOUT.setTitle("title");
        itemRepository.save(ITEM_TO_FIND_CHECKOUT);
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, CUSTOMER_3, ITEM_TO_FIND_CHECKOUT);
        } catch (IllegalArgumentException e){ //check that no error occurred
           error = e.getMessage();
        }
        assertNull(booking);
        assertEquals(error, "Item cannot be checked out");
    }




    @Test
    public void testCreateBookingCustomerNull(){
        String error = null;
        String bookingType = "Reservation";
        Customer customer = null;
        Item item = new Item();
        item.setAuthor("author");
        item.setIsAvailable(true);
        item.setItemType(ItemType.Book);
        item.setTitle("title");
        itemRepository.save(item);
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, customer, item);
        } catch (IllegalArgumentException e){ //check that no error occurred
            error = e.getMessage();
        }

        assertNull(booking);
        //check error
        assertEquals("Customer cannot be null", error);
    }

    @Test
    public void testCreateBookingBookingTypeNull(){
        String error = null;
        String bookingType = null;
        Customer customer = createCustomer("adress", "firstName", "lastName", true);
        Item item = new Item();
        item.setAuthor("author");
        item.setIsAvailable(true);
        item.setItemType(ItemType.Book);
        item.setTitle("title");
        itemRepository.save(item);
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, customer, item);
        } catch (IllegalArgumentException e){ //check that no error occurred
            error = e.getMessage();
        }

        assertNull(booking);
        //check error
        assertEquals("BookingType cannot be null", error);
    }

    // @Test
    // public void testCreateBookingBookingTypeNotAType(){
    //     String error = null;
    //     String bookingType = "football";
    //     Customer customer = createCustomer("adress", "firstName", "lastName", true);
    //     Item item = new Item();
    //     item.setAuthor("author");
    //     item.setIsAvailable(true);
    //     item.setItemType(ItemType.Book);
    //     item.setTitle("title");
    //     itemRepository.save(item);
    //     lenient().when(customerRepository.existsById(any())).thenReturn(true);
    //     lenient().when(itemRepository.existsById(any())).thenReturn(true);
    //     Booking booking = null;
    //     try{
    //         booking = service.createBooking(bookingType, customer, item);
    //     } catch (IllegalArgumentException e){ //check that no error occurred
    //         error = e.getMessage();
    //     }

    //     assertNull(booking);
    //     //check error
    //     assertEquals("BookingType does not exist", error);
    // }

    @Test
    public void testCreateBookingItemNull(){
        String error = null;
        String bookingType = "Reservation";
        Customer customer = createCustomer("adress", "firstName", "lastName", true);
        Item item = null;
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        //lenient().when(itemRepository.existsItemById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, customer, item);
        } catch (IllegalArgumentException e){ //check that no error occurred
            error = e.getMessage();
        }

        assertNull(booking);
        //check error
        assertEquals("Item cannot be null", error);
    }



    @Test
    public void testCreateBookingCustomerDoesNotExist(){
        String error = null;
        String bookingType = "Reservation";
        Customer customer = createCustomer("adress", "firstName", "lastName", true);
        Item item = new Item();
        item.setAuthor("author");
        item.setIsAvailable(true);
        item.setItemType(ItemType.Book);
        item.setTitle("title");
        itemRepository.save(item);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, customer, item);
        } catch (IllegalArgumentException e){ //check that no error occurred
            error = e.getMessage();
        }
        assertNull(booking);
        //check error
        assertEquals("Customer does not exist", error);
    }

    @Test
    public void testCreateBookingItemDoesNotExist(){
        String error = null;
        String bookingType = "Reservation";
        Customer customer = createCustomer("adress", "firstName", "lastName", true);
        Item item = new Item();
        item.setAuthor("author");
        item.setIsAvailable(true);
        item.setItemType(ItemType.Book);
        item.setTitle("title");
        itemRepository.save(item);
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Booking booking = null;
        try{
            booking = service.createBooking(bookingType, customer, item);
        } catch (IllegalArgumentException e){ //check that no error occurred
            error = e.getMessage();
        }
        assertNull(booking);
        //check error
        assertEquals("Item does not exist", error);
    }


    @Test
    public void testGetExistingBooking(){
       Booking booking = null;
       lenient().when(bookingRepository.existsById(any())).thenReturn(true);
       try{
           booking = service.getBookingById(BOOKING_ID);
       } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();

       }
       assertNotNull(booking);
       assertEquals(getBookingType(BOOKING_TYPE), booking.getBookingType());
       assertEquals(CUSTOMER_ID, booking.getCustomer().getId());
       assertEquals(BOOKING_START_DATE, booking.getStartDate());
       assertEquals(BOOKING_END_DATE, booking.getEndDate());
       assertEquals(ITEM_ID, booking.getItem().getId());

    }

    @Test
    public void getNonExistingBooking(){
        Booking booking = null;
        String message = null;
        try{
            booking = service.getBookingById(1l);
        } catch (Exception e){
            message = e.getMessage();
        }
        assertNull(booking);
        assertEquals(message, "Booking does not exist");
    }



    @Test
    public void testGetExistingBookingByCustomer(){
       List<Booking> bookings = null;
       lenient().when(customerRepository.existsById(any())).thenReturn(true);
       try{
           bookings = service.getBookingsByCustomer(CUSTOMER_TEST);
       } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();

       }
       assertNotNull(bookings);
       assertEquals(1, bookings.size());
       for (Booking bk : bookings) {
        assertEquals(getBookingType(BOOKING_TYPE), bk.getBookingType());
        assertEquals(CUSTOMER_ID, bk.getCustomer().getId());
        assertEquals(BOOKING_START_DATE, bk.getStartDate());
        assertEquals(BOOKING_END_DATE, bk.getEndDate());
        assertEquals(ITEM_ID, bk.getItem().getId());
       }

    }

    @Test
    public void testGetBookingsByType(){
       List<Booking> bookings = null;
       try{
           bookings = service.getBookingsByType(BOOKING_TYPE);
       } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();

       }
       assertNotNull(bookings);
       assertEquals(1, bookings.size());
       for (Booking bk : bookings) {
        assertEquals(getBookingType(BOOKING_TYPE), bk.getBookingType());
        assertEquals(CUSTOMER_ID, bk.getCustomer().getId());
        assertEquals(BOOKING_START_DATE, bk.getStartDate());
        assertEquals(BOOKING_END_DATE, bk.getEndDate());
        assertEquals(ITEM_ID, bk.getItem().getId());
       }

    }

    @Test
    public void testGetBookingByItem(){
       Booking booking = null;
       lenient().when(itemRepository.existsById(any())).thenReturn(true);
       try{
           booking = service.getBookingsByItem(ITEM_TO_FIND);
       } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();

       }
       assertNotNull(booking);
        assertEquals(getBookingType("Reservation"), booking.getBookingType());
        assertEquals(CUSTOMER_3, booking.getCustomer());
        assertEquals(ITEM_TO_FIND, booking.getItem());


    }

    @Test
    public void testGetBookingsByDate(){
       List<Booking> bookings = null;
       try{
           bookings = service.getBookingsByStartDate(BOOKING_START_DATE);
       } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();

       }
       assertNotNull(bookings);
       assertEquals(1, bookings.size());
       for (Booking bk : bookings) {
        assertEquals(getBookingType(BOOKING_TYPE), bk.getBookingType());
        assertEquals(CUSTOMER_ID, bk.getCustomer().getId());
        assertEquals(BOOKING_START_DATE, bk.getStartDate());
        assertEquals(BOOKING_END_DATE, bk.getEndDate());
        assertEquals(ITEM_ID, bk.getItem().getId());
       }

    }


    @Test
    public void testGetAllBookings(){
       List<Booking> bookings = null;
       try{
           bookings = service.getAllBookings();
       } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();

       }
       assertNotNull(bookings);
       assertEquals(4, bookings.size());

        assertEquals(getBookingType(BOOKING_TYPE), bookings.get(0).getBookingType());
        assertEquals(CUSTOMER_ID, bookings.get(0).getCustomer().getId());
        assertEquals(BOOKING_START_DATE, bookings.get(0).getStartDate());
        assertEquals(BOOKING_END_DATE, bookings.get(0).getEndDate());
        assertEquals(ITEM_ID, bookings.get(0).getItem().getId());

        assertEquals(getBookingType(BOOKING_TYPE_2), bookings.get(1).getBookingType());
        assertEquals(CUSTOMER_ID, bookings.get(1).getCustomer().getId());
        assertEquals(BOOKING_START_DATE_2, bookings.get(1).getStartDate());
        assertEquals(BOOKING_END_DATE_2, bookings.get(1).getEndDate());
        assertEquals(ITEM_ID, bookings.get(1).getItem().getId());

        assertEquals(getBookingType("Reservation"), bookings.get(2).getBookingType());
        assertEquals(565l, bookings.get(2).getCustomer().getId());
        assertEquals(787l, bookings.get(2).getItem().getId());

        assertEquals(getBookingType(BOOKING_TYPE_2), bookings.get(3).getBookingType());
        assertEquals(565l, bookings.get(3).getCustomer().getId());
        assertEquals(333l, bookings.get(3).getItem().getId());

    }

    @Test
    public void testRenewCheckout(){
        //Fields to create booking
        String bookingType = "Checkout";
        Customer customer = createCustomer("adress", "firstName", "lastName", true);
        Item item = new Item();
        item.setAuthor("author");
        item.setIsAvailable(true);
        item.setItemType(ItemType.Book);
        item.setTitle("title");
        itemRepository.save(item);
        Date startDate = Date.valueOf("2021-11-01");
        Date endDate = Date.valueOf("2021-11-15");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = new Booking();
        booking.setBookingType(getBookingType(bookingType));
        booking.setCustomer(customer);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setItem(item);
        booking.setId(BOOKING_ID_RENEW);
        lenient().when(bookingRepository.existsById(any())).thenReturn(true);

        try{
            booking = service.renewCheckout(booking);
        } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(booking);
        assertEquals(getBookingType(bookingType), booking.getBookingType());
        assertEquals(customer, booking.getCustomer());
        assertEquals(item, booking.getItem());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(Date.valueOf("2021-11-29"), booking.getEndDate());
    }

    @Test
    public void testRenewReservation(){
        //Fields to create booking
        String error = null;
        String bookingType = "Reservation";
        Customer customer = createCustomer("adress", "firstName", "lastName", true);
        Item item = new Item();
        item.setAuthor("author");
        item.setIsAvailable(true);
        item.setItemType(ItemType.Book);
        item.setTitle("title");
        itemRepository.save(item);
        Date startDate = Date.valueOf("2021-11-01");
        Date endDate = Date.valueOf("2021-11-15");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);
        Booking booking = new Booking();
        booking.setBookingType(getBookingType(bookingType));
        booking.setCustomer(customer);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setItem(item);
        booking.setId(BOOKING_ID_RENEW);
        lenient().when(bookingRepository.existsById(any())).thenReturn(true);

        try{
            booking = service.renewCheckout(booking);
        } catch (IllegalArgumentException e){ //check that no error occurred
            booking = null;
            error = e.getMessage();
        }
        assertEquals(error, "A reservation cannot be renewed");

    }

    @Test
    public void testRenewNonExistingCheckout(){
        //Fields to create booking
        String error = null;
        Booking booking = new Booking();
        booking.setId(12l);
        try{
            booking = service.renewCheckout(booking);
        } catch (IllegalArgumentException e){ //check that no error occurred
           error = e.getMessage();
           booking = null;
        }
        assertNull(booking);
        assertEquals(error, "Booking does not exist");
    }

    @Test
    public void testRenewNullCheckout(){
        //Fields to create booking
        String error = null;
        Booking booking = null;
        try{
            booking = service.renewCheckout(booking);
        } catch (IllegalArgumentException e){ //check that no error occurred
           error = e.getMessage();
        }
        assertNull(booking);
        assertEquals("Booking cannot be null", error);
    }

    // @Test
    // public void deleteBooking(){
    //     //Fields to create booking
    //     //Fields to create booking
    //     String bookingType = "Reservation";
    //     Customer customer = createCustomer("adress", "firstName", "lastName", true);
    //     Item item = new Item();
    //     item.setAuthor("author");
    //     item.setIsAvailable(true);
    //     item.setItemType(ItemType.Book);
    //     item.setTitle("title");
    //     itemRepository.save(item);
    //     Date startDate = Date.valueOf("2021-11-01");
    //     Date endDate = Date.valueOf("2021-11-15");
    //     lenient().when(customerRepository.existsById(any())).thenReturn(true);
    //     lenient().when(itemRepository.existsById(any())).thenReturn(true);
    //     Booking booking = new Booking();
    //     booking.setBookingType(getBookingType(bookingType));
    //     booking.setCustomer(customer);
    //     booking.setStartDate(startDate);
    //     booking.setEndDate(endDate);
    //     booking.setItem(item);
    //     booking.setId(BOOKING_ID);
    //     lenient().when(bookingRepository.existsById(any())).thenReturn(true);

    //     try{
    //         booking = service.deleteBooking(booking);
    //     } catch (IllegalArgumentException e){ //check that no error occurred
    //        System.out.println(e.getMessage());
    //        fail();
    //     }
    //     assertNull(booking);
    // }

    @Test
    public void testDeleteNonExistingCheckout(){
        //Fields to create booking
        String error = null;
        Booking booking = new Booking();
        booking.setId(12l);
        try{
            booking = service.deleteBooking(booking);
        } catch (IllegalArgumentException e){ //check that no error occurred
           error = e.getMessage();
           booking = null;
        }
        assertNull(booking);
        assertEquals(error, "Booking does not exist");
    }

    @Test
    public void testDeleteNullCheckout(){
        //Fields to create booking
        String error = null;
        Booking booking = null;
        try{
            booking = service.deleteBooking(booking);
        } catch (IllegalArgumentException e){ //check that no error occurred
           error = e.getMessage();
        }
        assertNull(booking);
        assertEquals("Booking cannot be null", error);
    }


    private BookingType getBookingType(String aBookingType){
        if(aBookingType == null || aBookingType.trim().length() == 0){
            throw new IllegalArgumentException("There is no such booking type");
        }

        BookingType bookingType = null;
        if(aBookingType.equals("Reservation") || aBookingType.equals("reservation")){
            bookingType = BookingType.Reservation;
        }
        else if(aBookingType.equals("Checkout") || aBookingType.equals("checkout")){
            bookingType = BookingType.Checkout;
        }
        return bookingType;
    }

    public Customer createCustomer(String address, String firstName, String lastName, boolean isLocal){
        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setIsLocal(isLocal);
        return customer;
    }



}
