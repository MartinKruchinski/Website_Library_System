package ca.mcgill.ecse321.projectgroup14.services;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.projectgroup14.Exception.WrongInputException;
import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.model.Shift;
import ca.mcgill.ecse321.projectgroup14.repository.LibrarianRepository;
import ca.mcgill.ecse321.projectgroup14.repository.ShiftRepository;


@Service
public class LibrarianService {

    public final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    public final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");


    @Autowired
    LibrarianRepository librarianRepository;

    @Autowired
    ShiftRepository shiftRepository;

    @Transactional
    public Librarian createLibrarian(String firstname, String lastname, String email, boolean isHead, String username, String password){

        if (firstname == null || firstname.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid firstname");

        if (lastname == null || lastname.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid lastname");

        if (email == null || email.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid email");

        if (username == null || username.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid username");

        if (password == null || password.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid password");

        if (!passwordPattern.matcher(password).matches() || password.length() <= 8){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Password is invalid");
        }

        if (!emailPattern.matcher(email).matches()){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email is invalid");
        }
        else if(librarianRepository.existsByEmail(email)){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email is taken");
        }

        if (librarianRepository.existsByUsername(username)){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Username is taken");
        }

        Librarian librarian = new Librarian();

        librarian.setFirstName(firstname);
        librarian.setLastName(lastname);
        librarian.setEmail(email);
        librarian.setIsHead(isHead);
        librarian.setPassword(password);
        librarian.setUsername(username);

        librarianRepository.save(librarian);

        return librarian;

    }

    @Transactional
    public Librarian updateLibrarianUsername (Librarian librarian, String username){
        if (!librarianRepository.existsByUsername(librarian.getUsername())){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }
        if (username == null || username.trim() == "")  throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid username");
        else if(!librarian.getUsername().equals(username) && librarianRepository.existsByUsername(username)){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"The username is taken");
        }
        librarian.setUsername(username);
        librarianRepository.save(librarian);
        return librarian;
    }

    @Transactional
    public Librarian updateLibrarianFullName (Librarian librarian, String firstname, String lastname){
        if (!librarianRepository.existsByUsername(librarian.getUsername())){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }
        if (firstname == null || firstname.trim() == "") throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid firstname");

        if (lastname == null || lastname.trim() == "")  throw new WrongInputException(HttpStatus.BAD_REQUEST,"Invalid lastname");
        librarian.setFirstName(firstname);
        librarian.setLastName(lastname);
        librarianRepository.save(librarian);
        return librarian;
    }

    @Transactional
    public Librarian updateLibrarianEmail (Librarian librarian, String email){
        if (!librarianRepository.existsByUsername(librarian.getUsername())){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }
        if (!emailPattern.matcher(email).matches()){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Email is invalid");
        }
        else if(!librarian.getEmail().equals(email) && librarianRepository.existsByEmail(email)){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"The email is taken");
        }

        librarian.setEmail(email);
        librarianRepository.save(librarian);
        return librarian;
    }

    @Transactional
    public Librarian updateLibrarianPassword (Librarian librarian, String password){
        if (!librarianRepository.existsByUsername(librarian.getUsername())){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }
        if (!passwordPattern.matcher(password).matches()){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Password is invalid");
        }

        librarian.setPassword(password);
        librarianRepository.save(librarian);
        return librarian;
    }

    @Transactional
    public Librarian deleteLibrarian(Librarian librarian){

        if (librarian == null) throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian is null");


        if (!librarianRepository.existsByUsername(librarian.getUsername())){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }
        List<Shift> shifts = shiftRepository.findShiftsByLibrarianId(librarian.getId());
        for (Shift shift : shifts) {
            shiftRepository.delete(shift);
        }
        librarianRepository.delete(librarian);
        return null;
    }

    @Transactional
    public List<Librarian> getAllLibrarians(){
        return toList(librarianRepository.findAll());
    }

    @Transactional
    public Librarian getLibrarianById(Long id){

        if (id == null){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Id is null");
        } else if (librarianRepository.findLibrarianById(id) == null){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }

        Librarian librarian = librarianRepository.findLibrarianById(id);
        return librarian;
    }

    @Transactional
    public Librarian getLibrarianByUsername(String username){

        if (username == null){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Id is null");
        } else if (librarianRepository.findLibrarianByUsername(username) == null){
            throw new WrongInputException(HttpStatus.BAD_REQUEST,"Librarian does not exist");
        }

        Librarian librarian = librarianRepository.findLibrarianByUsername(username);
        return librarian;
    }


    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
