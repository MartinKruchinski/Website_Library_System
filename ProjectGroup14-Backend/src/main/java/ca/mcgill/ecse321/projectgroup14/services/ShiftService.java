package ca.mcgill.ecse321.projectgroup14.services;

import java.util.List;
import java.util.ArrayList;

import java.sql.Time;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour;
import ca.mcgill.ecse321.projectgroup14.model.Shift;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.repository.LibrarianRepository;
import ca.mcgill.ecse321.projectgroup14.repository.OpeningHourRepository;
import ca.mcgill.ecse321.projectgroup14.repository.ShiftRepository;

@Service
public class ShiftService {

    @Autowired
    LibrarianRepository librarianRepository;

    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    OpeningHourRepository openingHourRepository;

    @Transactional
    public Shift createShift (Librarian librarian, DayOfWeek dayOfWeek, Time startTime, Time endTime) {

        if (librarian == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian cannot be null");

        if (dayOfWeek == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Day of Week cannot be null");

        if (startTime == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Start Time cannot be null");

        if (endTime == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"End Time cannot be null");

        if (startTime.after(endTime)) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Start Time cannot be after End Time");

        if (!librarianRepository.existsByUsername(librarian.getUsername())){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }

        boolean libraryOpen = false;

        for (OpeningHour openingHour : toList(openingHourRepository.findAll())) {
            if(openingHour.getDay().equals(dayOfWeek)){
                libraryOpen = true;
                if(!withinOpeningHours(startTime, endTime, openingHour)){
                    throw new WrongInputException(HttpStatus.BAD_REQUEST,"The shift must be within opening hours");
                }
            }
        }

        if (!libraryOpen){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"The shift must be within opening hours");
        }

        for (Shift s : shiftRepository.findShiftsByLibrarianId(librarian.getId())){
            if (dayOfWeek == s.getDay()){
                throw new WrongInputException(HttpStatus.BAD_REQUEST,"Cannot have 2 seperate shifts on the same day");
            }
        }



        Shift shift = new Shift();
        shift.setDay(dayOfWeek);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shift.setLibrarian(librarian);

        shiftRepository.save(shift);

        return shift;

    }

    @Transactional
    public Shift getShiftById (Long id) {


        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }

        if (!shiftRepository.existsById(id)){
            throw new IllegalArgumentException("Shift does not exist");
        }

        Shift openingHour = shiftRepository.findShiftById(id);
        return openingHour;

    }

    @Transactional
    public List<Shift> getShiftsByLibrarianId (Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }

        if (!librarianRepository.existsById(id)){
            throw new IllegalArgumentException("Librarian does not exist");
        }

        List<Shift> shifts = shiftRepository.findShiftsByLibrarianId(id);
        return shifts;
    }

    @Transactional
    public List<Shift> getAllShifts () {

        return toList(shiftRepository.findAll());

    }

    @Transactional
    public Shift deleteShift (Shift shift) {

        if (shift == null) {
            throw new IllegalArgumentException("This Shift does not exist");
        }

        shiftRepository.delete(shift);


        shift = null;

        return shift;
    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    private Boolean withinOpeningHours(Time startTime, Time endTime, OpeningHour openingHour){
        if(( openingHour.getStartTime().before(startTime) || openingHour.getStartTime().equals(startTime)) &&
           (openingHour.getEndTime().after(endTime) || openingHour.getEndTime().equals(endTime))){
               return true;
        }
        else return false;
    }

}
