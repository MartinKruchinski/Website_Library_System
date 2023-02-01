package ca.mcgill.ecse321.projectgroup14.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Library;
import ca.mcgill.ecse321.projectgroup14.repository.LibraryRepository;
import ca.mcgill.ecse321.projectgroup14.repository.OpeningHourRepository;

@Service
public class LibraryService {

    public final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    public final String phonePattern = "^[0-9]*$";
    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    OpeningHourRepository openingHourRepository;

    @Transactional
    public Library createLibrary (String name, String address, String phoneNumber, String email) {

        // Parameter Verification

        if (getLibrary() != null) throw new IllegalArgumentException("Cannot have two libraries");
        if (name == null || name.trim() == "") throw new IllegalArgumentException("Name cannot be null");
        if (address == null || address.trim() == "") throw new IllegalArgumentException("Address cannot be null");
        if (phoneNumber == null || phoneNumber.trim() == "") throw new IllegalArgumentException("PhoneNumber cannot be null");
        else if(!phoneNumber.matches(phonePattern)){
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (email == null || email.trim() == "") throw new IllegalArgumentException("Email cannot be null");
        if (!emailPattern.matcher(email).matches()) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email is invalid");


        // Create Library
        Library library = new Library();
        library.setName(name);
        library.setAddress(address);
        library.setPhoneNumber(phoneNumber);
        library.setEmail(email);
        library.setOpeningHours(toList(openingHourRepository.findAll()));
        libraryRepository.save(library);

        return library;

    }

    @Transactional
    public Library getLibrary () {

        if (libraryRepository.count() > 0){
            return toList(libraryRepository.findAll()).get(0);
        }
        else {
            return null;
        }

    }

    @Transactional
    public Library updateLibrary (String name, String address, String phoneNumber, String email) {

        // Parameter Verification

        if (getLibrary() == null) throw new IllegalStateException("No Library exists");
        if (name == null || name.trim() == "")  throw new WrongInputException(HttpStatus.BAD_REQUEST,"Name cannot be null");
        if (address == null || address.trim() == "")  throw new WrongInputException(HttpStatus.BAD_REQUEST,"Address cannot be null");
        if (phoneNumber == null || phoneNumber.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Phone number cannot be null");
        if (email == null || email.trim() == "")  throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email cannot be null");

        if (!emailPattern.matcher(email).matches()) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email is invalid");
        // Update Library

        Library library = getLibrary();
        library.setName(name);
        library.setAddress(address);
        library.setPhoneNumber(phoneNumber);
        library.setEmail(email);
        libraryRepository.save(library);

        return library;

    }

    public Library deleteLibrary () {

        // Verification

        if (getLibrary() == null) throw new IllegalStateException("No Library exists");

        // Delete Library

        libraryRepository.deleteAll();
        return null;

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
