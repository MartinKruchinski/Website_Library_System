package ca.mcgill.ecse321.projectgroup14.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup14.model.Library;
import ca.mcgill.ecse321.projectgroup14.repository.LibraryRepository;
import ca.mcgill.ecse321.projectgroup14.repository.OpeningHourRepository;


@ExtendWith(MockitoExtension.class)
public class TestLibraryService {

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private OpeningHourRepository openingHourRepository;

    @InjectMocks
    private LibraryService libraryService;

    // Constants

    // private static final Long LIBID = 101l;
    // private static final String NAME = "A Lib";
    // private static final String ADDRESS = "A Place";
    // private static final String PHONE = "765432";
    // private static final String EMAIL = "saba@email.com";

    @BeforeEach
    public void setMockOutput () {

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(libraryRepository.save(any(Library.class))).thenAnswer(returnParameterAsAnswer);

    }

    @Test
    public void TestCreateLibrary () {

        String name = "A Lib";
        String address = "A Place";
        String phone = "6543";
        String email = "saba@email.com";

        Library library = null;

        try { library = libraryService.createLibrary(name, address, phone, email); }
        catch (IllegalArgumentException e) { fail(); }

        assertNotNull(library);
        assertEquals(name, library.getName());
        assertEquals(address, library.getAddress());
        assertEquals(phone, library.getPhoneNumber());
        assertEquals(email, library.getEmail());

    }

    @Test
    public void TestCreateLibraryNoName () {

        String error = null;

        String name = "";
        String address = "A Place";
        String phone = "A Number";
        String email = "saba@email.com";

        Library library = null;

        try { library = libraryService.createLibrary(name, address, phone, email); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(library);
        assertEquals("Name cannot be null", error);

    }

    @Test
    public void TestCreateLibraryNoAddress () {

        String error = null;

        String name = "A Lib";
        String address = "";
        String phone = "A Number";
        String email = "saba@email.com";

        Library library = null;

        try { library = libraryService.createLibrary(name, address, phone, email); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(library);
        assertEquals("Address cannot be null", error);

    }

    @Test
    public void TestCreateLibraryNoPhone () {

        String error = null;

        String name = "A Lib";
        String address = "A Place";
        String phone = "";
        String email = "saba@email.com";

        Library library = null;

        try { library = libraryService.createLibrary(name, address, phone, email); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(library);
        assertEquals("PhoneNumber cannot be null", error);

    }
    
    @Test
    public void TestCreateLibraryNoEmail () {

        String error = null;

        String name = "A Lib";
        String address = "A Place";
        String phone = "123456";
        String email = "";

        Library library = null;

        try { library = libraryService.createLibrary(name, address, phone, email); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(library);
        assertEquals("Email cannot be null", error);

    }


    // @Test
    // public void TestGetLibrary () {
    //     Library library = null;

    //     lenient().when(libraryRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {

    //         Library l = new Library();
    //         l.setId(LIBID);
    //         l.setName(NAME);
    //         l.setAddress(ADDRESS);
    //         l.setPhoneNumber(PHONE);
    //         l.setEmail(EMAIL);

    //         List<Library> libList= new ArrayList<Library> ();
    //         libList.add(l);

    //         return libList;

    //     });

    //     try{
    //     library = libraryService.getLibrary();
    //     }catch(Exception e){
    //         System.out.println(e.getMessage());
    //         fail();
    //     }

    //     assertNotNull(library);
    //     assertEquals(NAME, library.getName());
    //     assertEquals(ADDRESS, library.getAddress());
    //     assertEquals(PHONE, library.getPhoneNumber());
    //     assertEquals(EMAIL, library.getEmail());

    // }

    @Test
    public void TestGetLibraryNull () {

        Library library = null;

        library = libraryService.getLibrary();

        assertNull(library);

    }

    // @Test
    // public void TestDeleteLibrary () {

    //     String name = "A Lib";
    //     String address = "A Place";
    //     String phone = "A Number";
    //     String email = "saba@email.com";

    //     Library library = null;

    //     try { library = libraryService.createLibrary(name, address, phone, email); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     lenient().when(libraryRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {

    //         Library l = new Library();
    //         l.setId(LIBID);
    //         l.setName(NAME);
    //         l.setAddress(ADDRESS);
    //         l.setPhoneNumber(PHONE);
    //         l.setEmail(EMAIL);

    //         List<Library> libList= new ArrayList<Library> ();
    //         libList.add(l);

    //         return libList;

    //     });

    //     library = libraryService.deleteLibrary();

    //     assertNull(library);

    //     lenient().when(libraryRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {

    //         return null;

    //     });

    //     library = libraryService.getLibrary();

    //     assertNull(library);

    // }

    // @Test
    // public void TestUpdateLibrary () {

    //     String name = "A Lib";
    //     String address = "A Place";
    //     String phone = "A Number";
    //     String email = "saba@email.com";

    //     Library library = null;

    //     try { library = libraryService.createLibrary(name, address, phone, email); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     lenient().when(libraryRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {

    //         Library l = new Library();
    //         l.setId(LIBID);
    //         l.setName(NAME);
    //         l.setAddress(ADDRESS);
    //         l.setPhoneNumber(PHONE);
    //         l.setEmail(EMAIL);

    //         List<Library> libList= new ArrayList<Library> ();
    //         libList.add(l);

    //         return libList;

    //     });

    //     String name2 = "A Lib2";
    //     String address2 = "A Place2";
    //     String phone2 = "A Number2";
    //     String email2 = "saba@email.com2";

    //     try { library = libraryService.updateLibrary(name2, address2, phone2, email2); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     assertNotNull(library);
    //     assertEquals(name2, library.getName());
    //     assertEquals(address2, library.getAddress());
    //     assertEquals(phone2, library.getPhoneNumber());
    //     assertEquals(email2, library.getEmail());

    // }

}
