package ca.mcgill.ecse321.projectgroup14.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.projectgroup14.dto.EventDto;
import ca.mcgill.ecse321.projectgroup14.dto.LoginCredentialDto;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Event;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.services.CustomerService;
import ca.mcgill.ecse321.projectgroup14.services.EventService;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "*")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = {"/event/{customerId}", "/event/{customerId}" })
    public EventDto createEvent(@PathVariable (name = "customerId") Long customerId,
                                @RequestParam (name = "startTime") String startTime,
                                @RequestParam (name = "endTime") String endTime,
                                @RequestParam (name = "date") String date){

        Customer customer = customerService.getCustomerById(customerId);
        Event event = eventService.createEvent(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime), customer);

        return convertToDto(event);
    }
    
    @GetMapping(value = {"/events/{id}", "/events/{id}/"})
    public EventDto getEventbyId(@PathVariable("id") Long id) {

        Event event = eventService.getEventById(id);

        return convertToDto(event);
    }

    @GetMapping(value = {"/events/customer/{customerId}", "/events/customer/{customerId}/"})
    public EventDto getEventbyCustomer(@PathVariable("customerId") Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        Event event = eventService.getEventByCustomer(customer);

        return convertToDto(event);
    }

    @GetMapping(value = {"/events/date/{eventdate}", "/events/date/{eventdate}/"})
    public EventDto getEventbyDate(@PathVariable("eventdate") String eventDate) {
        Event event = eventService.getEventByDate(Date.valueOf(eventDate));
        return convertToDto(event);
    }




    @GetMapping(value = {"/events", "/events/"})
    public List<EventDto> getAllEvents() throws IllegalArgumentException{

        return eventService.getAllEvents().stream().map(p -> convertToDto(p)).collect(Collectors.toList());

    }

    @DeleteMapping(value = {"events/delete/{id}", "events/delete/{id}/"})
    public void deleteEvent(@PathVariable("id") Long id){

        Event event = eventService.getEventById(id);

        eventService.deleteEvent(event);
    }


    private EventDto convertToDto(Event e) {
        System.out.println(e.getEndTime().toString());
        EventDto eventDto = new EventDto(e.getId(), e.getDate(), e.getStartTime(), e.getEndTime(), CustomerController.convertToDto(e.getCustomer()));

        return eventDto;
    }
    
    public LoginCredentialDto convertToDto(LoginCredential loginCredential){
        if (loginCredential == null) {
            throw new IllegalArgumentException("Login is null");
        }

        LoginCredentialDto loginCredentialDto = new LoginCredentialDto(loginCredential.getId(),
                                                    loginCredential.getUsername(),
                                                    loginCredential.getPassword(),
                                                    loginCredential.getEmail());

        return loginCredentialDto;
    }

}
