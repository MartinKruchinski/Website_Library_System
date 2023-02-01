package ca.mcgill.ecse321.projectgroup14.services;

import java.util.List;
import java.util.ArrayList;

import java.sql.Time;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Library;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.repository.LibraryRepository;
import ca.mcgill.ecse321.projectgroup14.repository.OpeningHourRepository;

@Service
public class OpeningHourService {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    OpeningHourRepository openingHourRepository;

    @Transactional
    public OpeningHour createOpeningHour (DayOfWeek dayOfWeek, Time startTime, Time endTime) {

        // Parameter Verificaiton

        if (dayOfWeek == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Day of Week cannot be null");
        if (startTime == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Start Time cannot be null");
        if (endTime == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"End Time cannot be null");
        if (startTime.after(endTime)) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Start Time cannot be after End Time");

        for (OpeningHour t : getAllOpeningHours()){
            if (dayOfWeek == t.getDay()){
                throw new WrongInputException(HttpStatus.BAD_REQUEST,"Only one Opening Hour per day");
            }
        }
        // Create Opening Hour

        OpeningHour openingHour = new OpeningHour();
        openingHour.setDay(dayOfWeek);
        openingHour.setStartTime(startTime);
        openingHour.setEndTime(endTime);
        openingHourRepository.save(openingHour);
        Library library = toList(libraryRepository.findAll()).get(0);
        library.getOpeningHours().add(openingHour);
        return openingHour;

    }

    @Transactional
    public OpeningHour getOpeningHourById (Long id) {

        // Parameter Verification

        if (!openingHourRepository.existsById(id)) throw new WrongInputException(HttpStatus.BAD_REQUEST,"OpeningHour does not exist");

        // Find Opening Hour

        return openingHourRepository.findOpeningHourById(id);

    }

    @Transactional
    public List<OpeningHour> getAllOpeningHours () {

        return toList(openingHourRepository.findAll());

    }

    @Transactional
    public OpeningHour deleteOpeningHour (OpeningHour openingHour) {

        if (openingHour == null)  throw new IllegalArgumentException("OpeningHour cannot be null");
        if (!openingHourRepository.existsById(openingHour.getId())) throw new IllegalArgumentException("OpeningHour does not exist");

        openingHourRepository.delete(openingHour);
        openingHour = null;
        return openingHour;

    }

    @Transactional
    public OpeningHour editOpeningHour (OpeningHour openingHour, DayOfWeek day, Time startTime, Time endTime) {

        if (openingHour == null)  throw new WrongInputException(HttpStatus.BAD_REQUEST,"OpeningHour cannot be null");
        if (!openingHourRepository.existsById(openingHour.getId())) throw new WrongInputException(HttpStatus.BAD_REQUEST,"OpeningHour does not exist");
        if (startTime.after(endTime)) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Start Time cannot be after End Time");

        if(openingHour.getDay().equals(day)){
            System.out.println("fasfes");
            openingHour.setDay(day);
            openingHour.setStartTime(startTime);
            openingHour.setEndTime(endTime);
            openingHourRepository.save(openingHour);
            return openingHour;
        }
        else {
            System.out.println("else");
            for (OpeningHour t : getAllOpeningHours()){
                if (day == t.getDay()){
                    throw new WrongInputException(HttpStatus.BAD_REQUEST,"Only one Opening Hour per day");
                }
            }
            openingHour.setDay(day);
            openingHour.setStartTime(startTime);
            openingHour.setEndTime(endTime);
            openingHourRepository.save(openingHour);
            return openingHour;
        }
    }


    // To List method taken from tutorial code

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}



}
