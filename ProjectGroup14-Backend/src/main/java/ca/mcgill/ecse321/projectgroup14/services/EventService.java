package ca.mcgill.ecse321.projectgroup14.services;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.Event;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.EventRepository;


@Service
public class EventService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EventRepository eventRepository;


    @Transactional
    public Event createEvent(Date date, Time startTime, Time endTime, Customer customer){
        Event event = new Event();

        //Checks
        if(date == null){
            throw new IllegalArgumentException("Date cannot be null");
        }
        else if(date.before(Date.valueOf(LocalDate.now()))){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Date cannot be before today's date");
        }
        if(startTime == null || endTime == null){
            throw new IllegalArgumentException("Time cannot be null");
        }
        else if(endTime.before(startTime)){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"The start time cannot be after the end time");
        }
        if(customer == null){
            throw new IllegalArgumentException("A customer can't be null");
        }
        if (!customerRepository.existsById(customer.getId()) && customer != null){
            throw new IllegalArgumentException("The customer does not exist");
        }
        if(eventRepository.findByDate(date) != null){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"The date is not available");
        }
        if(eventRepository.findByCustomer(customer) != null){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"A customer cannot have more than 1 event scheduled");
        }
        event.setDate(date);
        event.setEndTime(endTime);
        event.setStartTime(startTime);
        event.setCustomer(customer);
        eventRepository.save(event);
        return event;
    }

    @Transactional
    public Event getEventById(Long id){
        if(id == null){
            throw new IllegalArgumentException("No event with this ID");
        }
        else if(!eventRepository.existsById(id)){
            throw new IllegalArgumentException("The event does not exist");
        }
        Event event = eventRepository.findEventById(id);
        return event;
    }

    @Transactional
    public List<Event> getAllEvents(){
        return toList(eventRepository.findAll());
    }


    @Transactional
    public Event getEventByCustomer(Customer customer){
        if(customer == null){
            throw new IllegalArgumentException("Customer cannot be null");
        }
        else if(!customerRepository.existsById(customer.getId())){
            throw new IllegalArgumentException("Customer does not exist");
        }
        if(eventRepository.findByCustomer(customer) == null){
            throw new IllegalArgumentException("There are no events for this customer");
        }
        return eventRepository.findByCustomer(customer);
    }

    @Transactional
    public Event getEventByDate(Date date){
        if(date == null){
            throw new IllegalArgumentException("A date can't be null");
        }
        if(eventRepository.findByDate(date) == null){
            throw new IllegalArgumentException("There are no events for this date");
        }
        return eventRepository.findByDate(date);
    }


    @Transactional
    public Event deleteEvent(Event event){
        if(event==null){
            throw new IllegalArgumentException("Event cannot be null");
        }
        else if(eventRepository.findEventById(event.getId()) == null){
            throw new IllegalArgumentException("Event does not exist");
        }
        eventRepository.delete(event);
        event = null;
        return event;
    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
