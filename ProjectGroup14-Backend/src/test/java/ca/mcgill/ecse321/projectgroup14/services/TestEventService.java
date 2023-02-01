package ca.mcgill.ecse321.projectgroup14.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Event;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
public class TestEventService {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private EventService eventService;


    private static final Long EVENT_ID = 654789l ;

    //PARAMETERS FOR FIND ALL
    Customer customer_1 = new Customer();
    private static final Date date_1 = Date.valueOf("2022-11-11");
    private static final Time startTime_1 = Time.valueOf("10:00:00");
    private static final Time endTime_1 = Time.valueOf("17:00:00");

     Customer customer_2 = new Customer();
    private static final Date date_2 = Date.valueOf("2022-12-11");
    private static final Time startTime_2 = Time.valueOf("10:00:00");
    private static final Time endTime_2 = Time.valueOf("17:00:00");
    private static final Long event2_ID = 69l ;


    @BeforeEach
    public void setMockOutput(){
        //find by id mocking
        lenient().when(eventRepository.findEventById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(EVENT_ID)){
                Event event = new Event();
                event.setId(EVENT_ID);
                event.setCustomer(new Customer());
                event.setDate(Date.valueOf("2022-12-12"));
                event.setStartTime(Time.valueOf("10:00:00"));
                event.setEndTime(Time.valueOf("17:00:00"));
                return event;

            }
            else {
                return null;
            }
        });
        //Find all events mocking
        lenient().when(eventRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Event> events = new ArrayList<Event>();
            Event event_1 = new Event();
            event_1.setCustomer(customer_1);
            event_1.setDate(date_1);
            event_1.setStartTime(startTime_1);
            event_1.setEndTime(endTime_1);
            event_1.setId(EVENT_ID);

            Event event_2 = new Event();
            event_2.setCustomer(customer_2);
            event_2.setDate(date_2);
            event_2.setStartTime(startTime_2);
            event_2.setEndTime(endTime_2);
            event_2.setId(event2_ID);

            events.add(event_1);
            events.add(event_2);
            return events;
        });

        //Find by date mocking
        lenient().when(eventRepository.findByDate(any())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(date_1)){
                Event event_1 = new Event();
                event_1.setCustomer(customer_1);
                event_1.setDate(date_1);
                event_1.setStartTime(startTime_1);
                event_1.setEndTime(endTime_1);
                event_1.setId(EVENT_ID);
                return event_1;
            }
            else return null;
        });

        //find by customer
        lenient().when(eventRepository.findByCustomer(any())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(customer_1)){
                Event event_1 = new Event();
                event_1.setCustomer(customer_1);
                event_1.setDate(date_1);
                event_1.setStartTime(startTime_1);
                event_1.setEndTime(endTime_1);
                event_1.setId(EVENT_ID);
                return event_1;
            }
            else return null;
        });



    }

    @Test
    public void testCreateEvent(){

        Date date = Date.valueOf("2022-11-14");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Event event = null;

        try {
        event = eventService.createEvent(date, startTime, endTime, customer);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            fail();

        }
        assertNotNull(event);
        assertEquals(customer, event.getCustomer());
        assertEquals(date, event.getDate());
        assertEquals(startTime, event.getStartTime());
        assertEquals(endTime, event.getEndTime());
    }


    @Test
    public void testCreateEventFailDateNull(){

        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Event event = null;

        String error = null;

        try {

            event = eventService.createEvent(null, startTime, endTime, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(event);
        assertEquals(error, "Date cannot be null");
    }

    @Test
    public void testCreateEventFailDateInPast(){

        Date date = Date.valueOf("2020-09-11");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Event event = null;

        String error = null;

        try {

            event = eventService.createEvent(date, startTime, endTime, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(event);
        assertEquals(error, "Date cannot be before today's date");
    }

    @Test
    public void testCreateEventFailStartTimeNull(){

        Date date = Date.valueOf("2022-11-11");
        Time endTime = Time.valueOf("18:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Event event = null;

        String error = null;

        try {

            event = eventService.createEvent(date, null, endTime, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(event);
        assertEquals("Time cannot be null", error);
    }

    @Test
    public void testCreateEventFailEndTimeAfterStartTime(){

        Date date = Date.valueOf("2022-11-15");
        Time startTime = Time.valueOf("18:00:00");
        Time endTime = Time.valueOf("11:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Event event = null;

        String error = null;

        try {

            event = eventService.createEvent(date, startTime, endTime, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(event);
        assertEquals("The start time cannot be after the end time", error);
    }

    @Test
    public void testCreateEventFailEndTimeNull(){

        Date date = Date.valueOf("2022-11-11");
        Time startTime = Time.valueOf("10:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Event event = null;

        String error = null;

        try {

            event = eventService.createEvent(date, startTime, null, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(event);
        assertEquals("Time cannot be null", error);
    }

    @Test
    public void testCreateEventFailCustomerNull(){

        Date date = Date.valueOf("2022-11-11");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        Event event = null;

        String error = null;

        try {

            event = eventService.createEvent(date, startTime, endTime, null);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(event);
        assertEquals("A customer can't be null", error);
    }

    @Test
    public void testCreateEventFailCustomerNotExist(){

        Date date = Date.valueOf("2022-12-11");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(false);
        Event event = null;

        String error = null;

        try {

            event = eventService.createEvent(date, startTime, endTime, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(event);
        assertEquals("The customer does not exist", error);
    }

    @Test
    public void testCreateEventEventExistsOnDate(){


        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Long id = 12l;
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        Event testEvent = null;

        String error = null;

        try {

            testEvent = eventService.createEvent(date_1, startTime, endTime, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(testEvent);
        assertEquals("The date is not available", error);
    }

    @Test
    public void testCreateEventCustomerHas1Event(){

        Date date = Date.valueOf("2022-12-12");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Long id = 12l;
        Customer customer = customer_1;
        customer.setAddress("address");
        customer.setFirstName("firstName");
        customer.setId(id);
        customer.setIsLocal(true);
        customer.setLastName("lastName");
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        Event testEvent = null;

        String error = null;

        try {

            testEvent = eventService.createEvent(date, startTime, endTime, customer);

        } catch(IllegalArgumentException e){

            error = e.getMessage();

        }

        assertNull(testEvent);
        assertEquals("A customer cannot have more than 1 event scheduled", error);
    }


    @Test
    public void testGetExistingEvent(){
        Event event = null;
        lenient().when(eventRepository.existsById(any())).thenReturn(true);

        try{
            event = eventService.getEventById(EVENT_ID);
        }
        catch(Exception e){
            fail();
        }
        assertNotNull(event);
        assertEquals(EVENT_ID, event.getId());
        assertEquals(Date.valueOf("2022-12-12"), event.getDate());
        assertEquals(Time.valueOf("10:00:00"), event.getStartTime());
        assertEquals(Time.valueOf("17:00:00"), event.getEndTime());
    }


    @Test
    public void testGetNonExistingEvent(){
        String error = null;
        Event event = null;
        try{
            event = eventService.getEventById(76543l);
        } catch(Exception e){
            error = e.getMessage();
        }
        assertNull(event);
        assertEquals("The event does not exist", error);
    }


    @Test
    public void testGetAllEvents(){
        List<Event> events = new ArrayList<Event>();
        try{
            events = eventService.getAllEvents();
        }
        catch(Exception e){
            fail();
        }
        assertNotNull(events);
        assertEquals(EVENT_ID, events.get(0).getId());
        assertEquals(customer_1, events.get(0).getCustomer());
        assertEquals(date_1, events.get(0).getDate());
        assertEquals(startTime_1, events.get(0).getStartTime());
        assertEquals(endTime_1, events.get(0).getEndTime());

        assertEquals(event2_ID, events.get(1).getId());
        assertEquals(customer_2, events.get(1).getCustomer());
        assertEquals(date_2, events.get(1).getDate());
        assertEquals(startTime_2, events.get(1).getStartTime());
        assertEquals(endTime_2, events.get(1).getEndTime());

    }


    @Test
    public void testGetEventByDate(){
        Event event = null;
        lenient().when(eventRepository.existsById(any())).thenReturn(true);

        try{
            event = eventService.getEventByDate(date_1);
        }
        catch(Exception e){
            fail();
        }
        assertNotNull(event);
        assertEquals(EVENT_ID, event.getId());
        assertEquals(customer_1, event.getCustomer());
        assertEquals(date_1, event.getDate());
        assertEquals(startTime_1, event.getStartTime());
        assertEquals(endTime_1, event.getEndTime());
    }

    @Test
    public void testGetEventByCustomer(){
        Event event = null;
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        try{
            event = eventService.getEventByCustomer(customer_1);
        }
        catch(Exception e){
            fail();
        }
        assertNotNull(event);
        assertEquals(EVENT_ID, event.getId());
        assertEquals(customer_1, event.getCustomer());
        assertEquals(date_1, event.getDate());
        assertEquals(startTime_1, event.getStartTime());
        assertEquals(endTime_1, event.getEndTime());
    }







}
