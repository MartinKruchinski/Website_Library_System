package ca.mcgill.ecse321.projectgroup14.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.repository.LibrarianRepository;

@ExtendWith(MockitoExtension.class)
public class TestLibrarianService {

    @Mock
    private LibrarianRepository librarianRepository;

    @InjectMocks
    private LibrarianService librarianService;

    private static final Long LIBRARIAN_KEY = 11L;
    private static final String FIRSTNAME = "Saba";
    private static final boolean ISHEAD = false;
    private static final String LASTNAME = "Fathi";
    private static final String USERNAME = "saba";
    private static final String EXISTING_USER = "exist";
    private static final String PASSWORD = "sabaFathi4321";
    private static final String EMAIL = "saba@email.com";


    @BeforeEach
    public void setMockOutput() {
        lenient().when(librarianRepository.findLibrarianById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(LIBRARIAN_KEY)) {

                 Librarian librarian = new Librarian();
                 librarian.setEmail(EMAIL);
                 librarian.setFirstName(FIRSTNAME);
                 librarian.setLastName(LASTNAME);
                 librarian.setIsHead(ISHEAD);
                 librarian.setId(LIBRARIAN_KEY);
                 librarian.setUsername(USERNAME);
                 librarian.setPassword(PASSWORD);

                 return librarian;
            } else {
                return null;
            }
        });


        lenient().when(librarianRepository.existsByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(EXISTING_USER)) {
                return true;
            } else {
                return false;
            }
        });

        // Whenever anything is saved, just return the parameter object ??????
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(librarianRepository.save(any(Librarian.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateLibrarian(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(librarian);
        assertEquals(FIRSTNAME, librarian.getFirstName());
        assertEquals(LASTNAME, librarian.getLastName());
        assertEquals(EMAIL, librarian.getEmail());
        assertEquals(ISHEAD, librarian.getIsHead());
        assertEquals(USERNAME, librarian.getUsername());
        assertEquals(PASSWORD, librarian.getPassword());
    }

    @Test
    public void testCreateLibrarianWithGetById(){
        assertEquals(0, librarianService.getAllLibrarians().size());


        Librarian librarian = null;
        librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
        Librarian testLibrarian = null;

        librarian.setId(LIBRARIAN_KEY);

        try{
            testLibrarian = librarianService.getLibrarianById(librarian.getId());
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(librarian);
        assertEquals(FIRSTNAME, librarian.getFirstName());
        assertEquals(LASTNAME, librarian.getLastName());
        assertEquals(EMAIL, librarian.getEmail());
        assertEquals(ISHEAD, librarian.getIsHead());
        assertEquals(USERNAME, librarian.getUsername());
        assertEquals(PASSWORD, librarian.getPassword());

        assertNotNull(testLibrarian);
        assertEquals(FIRSTNAME, testLibrarian.getFirstName());
        assertEquals(LASTNAME, testLibrarian.getLastName());
        assertEquals(EMAIL, testLibrarian.getEmail());
        assertEquals(ISHEAD, testLibrarian.getIsHead());
        assertEquals(USERNAME, testLibrarian.getUsername());
        assertEquals(PASSWORD, testLibrarian.getPassword());

    }

    @Test
    public void testCreateLibrarianWithGetByIdFailNotExist(){
        assertEquals(0, librarianService.getAllLibrarians().size());


        Librarian librarian = null;
        librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
        Librarian testLibrarian = null;

        librarian.setId(LIBRARIAN_KEY);

        String error = null;

        try{
            testLibrarian = librarianService.getLibrarianById(1111L);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(librarian);
        assertEquals(FIRSTNAME, librarian.getFirstName());
        assertEquals(LASTNAME, librarian.getLastName());
        assertEquals(EMAIL, librarian.getEmail());
        assertEquals(ISHEAD, librarian.getIsHead());
        assertEquals(USERNAME, librarian.getUsername());
        assertEquals(PASSWORD, librarian.getPassword());

        assertNull(testLibrarian);
        assertEquals(error, "Librarian does not exist");
    }


    @Test
    public void testCreateLibrarianWithGetByIdFailNull(){
        assertEquals(0, librarianService.getAllLibrarians().size());


        Librarian librarian = null;
        librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
        Librarian testLibrarian = null;

        librarian.setId(LIBRARIAN_KEY);

        String error = null;

        try{
            testLibrarian = librarianService.getLibrarianById(null);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(librarian);
        assertEquals(FIRSTNAME, librarian.getFirstName());
        assertEquals(LASTNAME, librarian.getLastName());
        assertEquals(EMAIL, librarian.getEmail());
        assertEquals(ISHEAD, librarian.getIsHead());
        assertEquals(USERNAME, librarian.getUsername());
        assertEquals(PASSWORD, librarian.getPassword());

        assertNull(testLibrarian);
        assertEquals(error, "Id is null");
    }

    @Test
    public void testCreateLibrarianFailFirstnameNull(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(null, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid firstname", error);
    }


    @Test
    public void testCreateLibrarianFailFirstnameEmpty(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(" ", LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid firstname", error);
    }

    @Test
    public void testCreateLibrarianFailLastnameNull(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, null, EMAIL, ISHEAD, USERNAME, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid lastname", error);
    }

    @Test
    public void testCreateLibrarianFailLastnameEmpty(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, " ", EMAIL, ISHEAD, USERNAME, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid lastname", error);
    }

    @Test
    public void testCreateLibrarianFailEmailNull(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, null, ISHEAD, USERNAME, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid email", error);
    }

    @Test
    public void testCreateLibrarianFailEmailEmpty(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, " ", ISHEAD, USERNAME, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid email", error);
    }

    @Test
    public void testCreateLibrarianFailUsernameEmpty(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, " ", PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid username", error);
    }

    @Test
    public void testCreateLibrarianFailUsernameTaken(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, EXISTING_USER, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Username is taken", error);
    }

    @Test
    public void testCreateLibrarianFailUsernameNull(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, null, PASSWORD);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid username", error);
    }

    @Test
    public void testCreateLibrarianFailPasswordNull(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, null);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid password", error);
    }

    @Test
    public void testCreateLibrarianFailPasswordEmpty(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, " ");
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Invalid password", error);
    }


    @Test
    public void testCreateLibrarianFailPasswordInvalid(){
        assertEquals(0, librarianService.getAllLibrarians().size());

        Librarian librarian = null;

        String error = "";

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, "not_valid");
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(librarian);
        assertEquals("Password is invalid", error);
    }

    // @Test
    // public void testUpdateLibrarian(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         lenient().when(librarianRepository.existsById(any())).thenReturn(true);
    //         // lenient().when(librarianRepository.existsByEmail(any())).thenReturn(true);
    // 		lenient().when(librarianRepository.existsByUsername(any())).thenReturn(true);
    //         testLibrarian = librarianService.updateLibrarian(librarian, "FIRSTNAME", "LASTNAME", "test@email.com", false, USERNAME, "testPASSWORD1");
    //     } catch (IllegalArgumentException e){
    //         System.out.println(e.getMessage());
    //         System.out.println(librarian.getUsername());
    //         System.out.println(FIRSTNAME);
    //         fail();
    //     }

    //     assertNotNull(librarian);
    //     assertEquals("FIRSTNAME", librarian.getFirstName());
    //     assertEquals("LASTNAME", librarian.getLastName());
    //     assertEquals("test@email.com", librarian.getEmail());
    //     assertEquals(false, librarian.getIsHead());
    //     assertEquals("testPASSWORD1", librarian.getPassword());

    //     assertNotNull(testLibrarian);
    //     assertEquals("FIRSTNAME", testLibrarian.getFirstName());
    //     assertEquals("LASTNAME", testLibrarian.getLastName());
    //     assertEquals("test@email.com", testLibrarian.getEmail());
    //     assertEquals(false, testLibrarian.getIsHead());
    //     assertEquals("testPASSWORD1", testLibrarian.getPassword());
    // }

    // @Test
    // public void testUpdateLibrarianFailFirstnameNull(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, null, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid firstname", error);
    // }


    // @Test
    // public void testUpdateLibrarianFailFirstnameEmpty(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, " ", LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid firstname", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailLastnameNull(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, null, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid lastname", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailLastnameEmpty(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, " ", EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid lastname", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailEmailNull(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, null, ISHEAD, USERNAME, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid email", error);
    // }


    // @Test
    // public void testUpdateLibrarianFailEmailInvalid(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, null, ISHEAD, USERNAME, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid email", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailEmailEmpty(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, " ", ISHEAD, USERNAME, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid email", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailUsernameEmpty(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, EMAIL, ISHEAD, " ", PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid username", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailUsernameTaken(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, EMAIL, ISHEAD, EXISTING_USER, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Username is taken", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailUsernameNull(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, EMAIL, ISHEAD, null, PASSWORD);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid username", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailPasswordNull(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, null);
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid password", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailPasswordEmpty(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, " ");
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Invalid password", error);
    // }

    // @Test
    // public void testUpdateLibrarianFailPasswordInvalid(){
    //     assertEquals(0, librarianService.getAllLibrarians().size());

    //     Librarian librarian = null;

    //     String error = "";
    //     librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //     Librarian testLibrarian = null;

    //     try{
    //         testLibrarian = librarianService.updateLibrarian(librarian, FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, "not_valid");
    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNull(testLibrarian);
    //     assertEquals("Password is invalid", error);
    // }


}
