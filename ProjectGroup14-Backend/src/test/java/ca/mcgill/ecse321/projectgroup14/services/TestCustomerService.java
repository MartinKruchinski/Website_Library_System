package ca.mcgill.ecse321.projectgroup14.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup14.model.Customer;
import ca.mcgill.ecse321.projectgroup14.model.LoginCredential;
import ca.mcgill.ecse321.projectgroup14.repository.CustomerRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LoginCredentialRepository;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoginCredentialRepository loginCredentialRepository;

    @InjectMocks
    private CustomerService customerService;

    @InjectMocks
    private LoginCredentialService loginCredentialService;

    private static final Long CUSTOMER_KEY = 11L;
    private static final String FIRSTNAME = "Saba";
    private static final boolean ISLOCAL = false;
    private static final String LASTNAME = "Fathi";
    private static final String ADDRESS = "5 Grimes";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(customerRepository.findByLoginCredential(any())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(CUSTOMER_KEY)) {
                Customer customer = new Customer();
                customer.setId(CUSTOMER_KEY);
                return customer;
            } else {
                return null;
            }
        });

        // Whenever anything is saved, just return the parameter object ??????
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateCustomer(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

    }

    @Test
    public void testCreateCustomerWithLogin(){
        assertEquals(0, customerService.getAllCustomers().size());

        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        String username = "saba";
        String password = "sabaFathi4321";
        String email = "saba@email.com";

        Customer customer = null;

        LoginCredential login = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);

            login = loginCredentialService.createLoginCredential(username, password, email, customer);

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            fail();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertNotNull(login);
        assertEquals(username, customer.getLoginCredential().getUsername());
        assertEquals(password, customer.getLoginCredential().getPassword());
        assertEquals(email, customer.getLoginCredential().getEmail());
    }


    @Test
    public void testGetCustomerByUsername(){
        assertEquals(0, customerService.getAllCustomers().size());

        lenient().when(customerRepository.existsById(any())).thenReturn(true);
        lenient().when(customerRepository.findByLoginCredentialUsername(any())).thenAnswer((InvocationOnMock invocation) -> {

            Customer customer = new Customer();

            customer.setAddress(ADDRESS);
            customer.setFirstName(FIRSTNAME);
            customer.setIsLocal(ISLOCAL);
            customer.setLastName(LASTNAME);

            String username = "saba";
            String password = "sabaFathi4321";
            String email = "saba@email.com";

            LoginCredential login = new LoginCredential();

            login.setEmail(email);
            login.setPassword(password);
            login.setUsername(username);

            customer.setLoginCredential(login);

            return customer;

        });

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        String username = "saba";
        String password = "sabaFathi4321";
        String email = "saba@email.com";
        LoginCredential login = null;

        Customer customer = null;

        Customer testCustomer = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);

            login = loginCredentialService.createLoginCredential(username, password, email, customer);

            testCustomer = customerService.getCustomerByLoginCredentialUsername(username);

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            fail();
        }

        assertNotNull(testCustomer);
        assertEquals(firstname, testCustomer.getFirstName());
        assertEquals(firstname, testCustomer.getFirstName());
        assertEquals(lastname, testCustomer.getLastName());
        assertEquals(address, testCustomer.getAddress());

        assertNotNull(login);
        assertEquals(username, testCustomer.getLoginCredential().getUsername());
        assertEquals(password, testCustomer.getLoginCredential().getPassword());
        assertEquals(email, testCustomer.getLoginCredential().getEmail());
    }


    @Test
    public void testCreateCustomerFirstNameNull(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = null;
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Firstname cannot be null");
    }


    @Test
    public void testCreateCustomerLastnameNull(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = null;
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Lastname cannot be null");
    }


    @Test
    public void testCreateCustomerAddressNull(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = null;
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Address cannot be null");
    }

    @Test
    public void testCreateCustomerFirstnameSpace(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = " ";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Firstname cannot be null");
    }


    @Test
    public void testCreateCustomerFirstNameEmpty(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Firstname cannot be null");
    }

    @Test
    public void testCreateCustomerLastnameSpace(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = " ";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Lastname cannot be null");
    }


    @Test
    public void testCreateCustomerLastnameEmpty(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Lastname cannot be null");
    }


    @Test
    public void testCreateCustomerAddressEmpty(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();

        }

        assertNull(customer);
        assertEquals(error, "Address cannot be null");
    }


    @Test
    public void testCreateCustomerAddressSpace(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = " ";
        boolean isLocal = false;

        Customer customer = null;

        String error = "";

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals(error, "Address cannot be null");
    }

    @Test
    public void testUpdateCustomerFirstname(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;
        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, "test", lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(customer);
        assertEquals("test", customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertNotNull(testCustomer);
        assertEquals("test", testCustomer.getFirstName());
        assertEquals(lastname, testCustomer.getLastName());
        assertEquals(address, testCustomer.getAddress());
    }

    @Test
    public void testUpdateCustomerFirstnameFailEmpty(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;

        String error = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, " ", lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertEquals(error, "Firstname cannot be null");
        assertNull(testCustomer);
    }

    @Test
    public void testUpdateCustomerFirstnameFailNull(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;

        String error = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, null, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertEquals(error, "Firstname cannot be null");
        assertNull(testCustomer);
    }

    @Test
    public void testUpdateCustomerLastname(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;
        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, firstname, "test", address, isLocal);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals("test", customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertNotNull(testCustomer);
        assertEquals(firstname, testCustomer.getFirstName());
        assertEquals("test", testCustomer.getLastName());
        assertEquals(address, testCustomer.getAddress());
    }

    @Test
    public void testUpdateCustomerLastnameFailNull(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;

        String error = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, null, lastname, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertEquals(error, "Firstname cannot be null");
        assertNull(testCustomer);
    }

    @Test
    public void testUpdateCustomerLastnameFailEmpty(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;

        String error = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, firstname, null, address, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertEquals(error, "Lastname cannot be null");
        assertNull(testCustomer);
    }

    @Test
    public void testUpdateCustomerAddress(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;
        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, firstname, lastname, "test", isLocal);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals("test", customer.getAddress());

        assertNotNull(testCustomer);
        assertEquals(firstname, testCustomer.getFirstName());
        assertEquals(lastname, testCustomer.getLastName());
        assertEquals("test", testCustomer.getAddress());
    }

    @Test
    public void testUpdateCustomerAddressFailNull(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;

        String error = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, firstname, lastname, null, isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertEquals(error, "Address cannot be null");
        assertNull(testCustomer);
    }


    @Test
    public void testUpdateCustomerAddressFailEmpty(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;

        String error = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, firstname, lastname, " ", isLocal);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertEquals(error, "Address cannot be null");
        assertNull(testCustomer);
    }

    @Test
    public void testUpdateCustomerIsLocal(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;
        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, firstname, lastname, address, false);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());
        assertEquals(false, customer.getIsLocal());

        assertNotNull(testCustomer);
        assertEquals(firstname, testCustomer.getFirstName());
        assertEquals(lastname, testCustomer.getLastName());
        assertEquals(address, testCustomer.getAddress());
        assertEquals(false, testCustomer.getIsLocal());
    }

    @Test
    public void testDeleteCustomer(){

        Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);
        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        try {
            customer = customerService.deleteCustomer(customer);
        } catch (Exception e) {
            fail();
        }
        assertNull(customer);
    }

    @Test
    public void testDeleteCustomerWithLogin(){

        lenient().when(loginCredentialRepository.existsById(any())).thenReturn(true);

        Customer customer = customerService.createCustomer(FIRSTNAME, LASTNAME, ADDRESS, ISLOCAL);

        lenient().when(customerRepository.existsById(any())).thenReturn(true);

        loginCredentialService.createLoginCredential("test", "testT4321", "saba@email.com", customer);

        try {
            customer = customerService.deleteCustomer(customer);
        } catch (Exception e) {
            fail();
        }

        assertNull(customer);
    }

    @Test
    public void testUpdateCustomerIsLocalFailNull(){
        assertEquals(0, customerService.getAllCustomers().size());

        String firstname = "Saba";
        String lastname = "Fathi";
        String address = "5 Grimes";
        boolean isLocal = false;

        Customer customer = null;

        Customer testCustomer = null;

        String error = null;

        try{
            customer = customerService.createCustomer(firstname, lastname, address, isLocal);
            testCustomer = customerService.updateCustomer(customer, firstname, lastname, address, null);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(customer);
        assertEquals(firstname, customer.getFirstName());
        assertEquals(lastname, customer.getLastName());
        assertEquals(address, customer.getAddress());

        assertEquals(error, "Is local cannot be null");
        assertNull(testCustomer);
    }

}
