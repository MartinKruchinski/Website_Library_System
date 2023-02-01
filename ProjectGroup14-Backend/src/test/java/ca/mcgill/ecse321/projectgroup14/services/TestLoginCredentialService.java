package ca.mcgill.ecse321.projectgroup14.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LoginCredentialRepository;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;


@ExtendWith(MockitoExtension.class)
public class TestLoginCredentialService {

    @Mock
    private LoginCredentialRepository loginCredentialRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private LoginCredentialService loginCredentialService;

    @InjectMocks
    private CustomerService customerService;

    private static final Long LOGIN_KEY = 11L;
    private static final String USERNAME = "saba";
    private static final String PASSWORD = "sabaFathi4321";
    private static final String EMAIL = "saba@email.com";

    private static final String EXISTING_USERNAME = "saba";

    private static final String FIRSTNAME = "Saba";
    private static final String LASTNAME = "Fathi";
    private static final String ADDRESS = "5 Grimes";
    private static final Boolean ISLOCAL = false;

    @BeforeEach
    public void setMockOutput() {

        // Whenever anything is saved, just return the parameter object ??????
        lenient().when(loginCredentialRepository.existsByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EXISTING_USERNAME)) {

                return true;

            } else {
                return false;
            }
        });


        lenient().when(loginCredentialRepository.findLoginCredentialById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(LOGIN_KEY)) {

                LoginCredential loginCredential = new LoginCredential();
                loginCredential.setId(LOGIN_KEY);
                loginCredential.setEmail(EMAIL);
                loginCredential.setUsername(USERNAME);
                loginCredential.setPassword(PASSWORD);


                return loginCredential;
            } else {
                return null;
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

        lenient().when(loginCredentialRepository.save(any(LoginCredential.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateLoginCredential(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);


        String username = "Saba";
        String password = "sabaFathi4321";
        String email = "saba@email.com";

        LoginCredential loginCredential = new LoginCredential();

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            fail();
        }

        assertNotNull(loginCredential);
        // assertNotNull(loginCredential.getId());
        assertEquals(username, loginCredential.getUsername());
        assertEquals(password, loginCredential.getPassword());
        assertEquals(email, loginCredential.getEmail());

    }

    @Test
    public void testDeleteLogin(){


        Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
        customer.setId(11L);
        lenient().when(loginCredentialRepository.existsById(any())).thenReturn(true);

        lenient().when(customerRepository.findByLoginCredential(any())).thenAnswer((InvocationOnMock invocation) -> {

            Customer newCustomer = new Customer();
            newCustomer.setAddress(ADDRESS);
            newCustomer.setFirstName(FIRSTNAME);
            newCustomer.setLastName(LASTNAME);


            return newCustomer;

        });

        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        LoginCredential login = loginCredentialService.createLoginCredential("test", "testT4321", "saba@email.com", customer);

        try {
            login = loginCredentialService.deleteLoginCredential(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

        assertNull(login);
    }

    @Test
    public void testCreateLoginCredentialUsernameNull(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = null;
        String password = "sabaFathi4321";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Username cannot be null");
    }

    @Test
    public void testCreateLoginCredentialUsernameTaken(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);


        String username = "saba";
        String password = "sabaFathi4321";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(loginCredential);
        assertEquals(error, "Username is taken");
    }

    @Test
    public void testCreateLoginCredentialUsernameEmpty(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "";
        String password = "sabaFathi4321";
        String email = "saba@gmail.com";


        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Username cannot be null");
    }

    @Test
    public void testCreateLoginCredentialUsernameSpace(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = " ";
        String password = "sabaFathi4321";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Username cannot be null");
    }

    @Test
    public void testCreateLoginCredentialPasswordNull(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = null;
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Password cannot be null");
    }

    @Test
    public void testCreateLoginCredentialPasswordEmpty(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Password cannot be null");
    }

    @Test
    public void testCreateLoginCredentialPasswordSpace(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = " ";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Password cannot be null");
    }

    @Test
    public void testCreateLoginCredentialPasswordInvalidNoNumber(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "noNumberPresent";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Password needs to have at least 8 characters and include 1 lowercase letter, 1 uppercase letter and one number");
    }

    @Test
    public void testCreateLoginCredentialPasswordInvalidNoCapital(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "no_capital";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Password needs to have at least 8 characters and include 1 lowercase letter, 1 uppercase letter and one number");
    }

    @Test
    public void testCreateLoginCredentialPasswordInvalidTooShort(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "short";
        String email = "saba@gmail.com";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Password needs to have at least 8 characters and include 1 lowercase letter, 1 uppercase letter and one number");
    }


    @Test
    public void testCreateLoginCredentialEmailNull(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "sabaFathi4321";
        String email = null;

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Email cannot be null");
    }

    @Test
    public void testCreateLoginCredentialEmailEmpty(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "sabaFathi4321";
        String email = "";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Email cannot be null");
    }

    @Test
    public void testCreateLoginCredentialEmailSpace(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "sabaFathi4321";
        String email = " ";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Email cannot be null");
    }

    @Test
    public void testCreateLoginCredentialEmailInvalid(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String username = "Saba";
        String password = "sabaFathi4321";
        String email = "notAnEmail";

        LoginCredential loginCredential = null;

        String error = "";

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(loginCredential);
        assertEquals(error, "Email is invalid");
    }

    @Test
    public void testUpdateLoginCredential(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());

        String username = "Saba";
        String password = "sabaFathi4321";
        String email = "saba@email.com";

        LoginCredential loginCredential = new LoginCredential();

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            lenient().when(customerRepository.existsById(any())).thenReturn(true);
            lenient().when(loginCredentialRepository.existsByUsername(any())).thenAnswer((InvocationOnMock invocation) -> {
                if (invocation.getArgument(0).equals(username)) {

                    return false;

                } else {
                    return true;
                }
            });

            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);

            loginCredential = loginCredentialService.updateLoginCredential(username, "passwordT1", "test@gmail.info", customer, loginCredential);

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            fail();
        }

        assertNotNull(loginCredential);
        assertEquals(username, loginCredential.getUsername());
        assertEquals("passwordT1", loginCredential.getPassword());
        assertEquals("test@gmail.info", loginCredential.getEmail());

    }


    @Test
    public void testUpdateLoginCredentialUsername(){
        assertEquals(0, loginCredentialService.getAllLoginCredentials().size());

        String username = "Saba";
        String password = "sabaFathi4321";
        String email = "saba@email.com";

        LoginCredential loginCredential = new LoginCredential();

        try{
            Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
            lenient().when(customerRepository.existsById(any())).thenReturn(true);
            lenient().when(loginCredentialRepository.existsByUsername(any())).thenAnswer((InvocationOnMock invocation) -> {
                if (invocation.getArgument(0).equals(username)) {

                    return false;

                } else {
                    return false;
                }
            });

            loginCredential = loginCredentialService.createLoginCredential(username, password, email, customer);

            loginCredential = loginCredentialService.updateLoginCredential("username", "passwordT1", "test@gmail.info", customer, loginCredential);

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            fail();
        }

        assertNotNull(loginCredential);
        assertEquals("username", loginCredential.getUsername());
        assertEquals("passwordT1", loginCredential.getPassword());
        assertEquals("test@gmail.info", loginCredential.getEmail());

    }

}
