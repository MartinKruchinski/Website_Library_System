package ca.mcgill.ecse321.projectgroup14.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup14.dto.LibrarianDto;
import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.services.LibrarianService;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianController {

    @Autowired
    private LibrarianService librarianService;

    @PostMapping (value = {"/librarian/{username}", "/librarian/{username}/"})
    public LibrarianDto createLibrarian(@PathVariable (name = "username") String username,
                                       @RequestParam(name = "firstname") String firstname,
                                       @RequestParam(name = "lastname") String lastname,
                                       @RequestParam(name = "email") String email,
                                       @RequestParam(name = "isHead") boolean isHead,
                                       @RequestParam(name = "password") String password) throws IllegalArgumentException{

        Librarian librarian = librarianService.createLibrarian(firstname, lastname, email, isHead, username, password);

        return convertToDto(librarian);
    }

    @GetMapping(value = { "/librarians", "/librarians/" })
    public List<LibrarianDto> getLibrarians() throws IllegalArgumentException{

        return librarianService.getAllLibrarians().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/librarians/{librarianId}", "/librarians/{librarianId}/" })
    public LibrarianDto getLibrarianbyId(@PathVariable (name = "librarianId") Long librarianId) throws IllegalArgumentException{
        return convertToDto(librarianService.getLibrarianById(librarianId));
    }

    @GetMapping(value = { "/librarian/{username}", "/librarian/{username}/" })
    public LibrarianDto getLibrarianbyUsername(@PathVariable (name = "username") String username) throws IllegalArgumentException{
        return convertToDto(librarianService.getLibrarianByUsername(username));
    }


    @PatchMapping (value = {"/librarians/edit/fullname/{librarian_id}", "/librarians/edit/fullname/{librarian_id}/"})
    public LibrarianDto updateLibrarianFullName (@PathVariable("librarian_id") Long id,
                                 @RequestParam(name = "firstname") String firstname,
                                 @RequestParam(name = "lastname") String lastname) throws IllegalArgumentException{
        return convertToDto(librarianService.updateLibrarianFullName(librarianService.getLibrarianById(id), firstname, lastname));
    }

    @PatchMapping (value = {"/librarians/edit/username/{librarian_id}", "/librarians/edit/username/{librarian_id}/"})
    public LibrarianDto updateLibrarianUsername (@PathVariable("librarian_id") Long id,
                                                 @RequestParam(name = "username") String username) throws IllegalArgumentException{
        return convertToDto(librarianService.updateLibrarianUsername(librarianService.getLibrarianById(id), username));
    }

    @PatchMapping (value = {"/librarians/edit/email/{librarian_id}", "/librarians/edit/email/{librarian_id}/"})
    public LibrarianDto updateLibrarianEmail (@PathVariable("librarian_id") Long id,
                                              @RequestParam(name = "email") String email) throws IllegalArgumentException{
        return convertToDto(librarianService.updateLibrarianEmail(librarianService.getLibrarianById(id), email));
    }

    @PatchMapping (value = {"/librarians/edit/password/{librarian_id}", "/librarians/edit/password/{librarian_id}/"})
    public LibrarianDto updateLibrarianPassword (@PathVariable("librarian_id") Long id,
                                              @RequestParam(name = "password") String password) throws IllegalArgumentException{
        return convertToDto(librarianService.updateLibrarianPassword(librarianService.getLibrarianById(id), password));
    }

    @DeleteMapping (value = {"/librarians/delete/{librarian_id}", "/librarians/delete/{librarian_id}/"})
    public void deleteLibrarian (@PathVariable("librarian_id") Long id) throws IllegalArgumentException{
        librarianService.deleteLibrarian(librarianService.getLibrarianById(id));

    }

    public static LibrarianDto convertToDto(Librarian librarian){

        if (librarian == null) throw new IllegalArgumentException("Librarian cannot be null");

        LibrarianDto librarianDto = new LibrarianDto(librarian.getId(),
                                                    librarian.getFirstName(),
                                                    librarian.getLastName(),
                                                    librarian.getEmail(),
                                                    librarian.getIsHead(),
                                                    librarian.getUsername(),
                                                    librarian.getPassword());

        return librarianDto;
    }

}
