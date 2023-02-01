package ca.mcgill.ecse321.projectgroup14.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.model.Shift;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.repository.LibrarianRepository;
import ca.mcgill.ecse321.projectgroup14.repository.ShiftRepository;

@ExtendWith(MockitoExtension.class)
public class TestShiftService {

    @Mock
    private LibrarianRepository librarianRepository;

    @Mock
    private ShiftRepository shiftRepository;

    @InjectMocks
    private ShiftService shiftService;

    @InjectMocks
    private LibrarianService librarianService;

    // Librarian Attributes
    private static final Long LIBRARIAN_KEY = 11L;
    private static final String FIRSTNAME = "Saba";
    private static final boolean ISHEAD = false;
    private static final String LASTNAME = "Fathi";
    private static final String USERNAME = "saba";
    private static final String EXISTING_USER = "exist";
    private static final String PASSWORD = "sabaFathi4321";
    private static final String EMAIL = "saba@email.com";

    // Shift Attributes
    private static final Long SHIFT_KEY = 11L;
    private static final DayOfWeek DAY = DayOfWeek.Wednesday;
    private static final Time START_TIME = Time.valueOf("09:00:00");
    private static final Time END_TIME= Time.valueOf("17:00:00");


    @BeforeEach
    public void setMockOutput() {
        lenient().when(shiftRepository.findShiftById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(SHIFT_KEY)) {

                Librarian librarian = new Librarian();
                librarian.setEmail(EMAIL);
                librarian.setFirstName(FIRSTNAME);
                librarian.setLastName(LASTNAME);
                librarian.setIsHead(ISHEAD);
                librarian.setId(LIBRARIAN_KEY);
                librarian.setUsername(USERNAME);
                librarian.setPassword(PASSWORD);

                Shift shift = new Shift();
                shift.setDay(DAY);
                shift.setEndTime(END_TIME);
                shift.setStartTime(START_TIME);
                shift.setLibrarian(librarian);

                return shift;
            } else {
                return null;
            }
        });

        lenient().when(shiftRepository.findShiftsByLibrarianId(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(LIBRARIAN_KEY)) {

                Librarian librarian = new Librarian();
                librarian.setEmail(EMAIL);
                librarian.setFirstName(FIRSTNAME);
                librarian.setLastName(LASTNAME);
                librarian.setIsHead(ISHEAD);
                librarian.setId(LIBRARIAN_KEY);
                librarian.setUsername(USERNAME);
                librarian.setPassword(PASSWORD);

                List<Shift> shifts = new ArrayList<>();

                Shift shift1 = new Shift();
                shift1.setDay(DAY);
                shift1.setEndTime(END_TIME);
                shift1.setStartTime(START_TIME);
                shift1.setLibrarian(librarian);

                Shift shift2 = new Shift();
                shift2.setDay(DayOfWeek.Sunday);
                shift2.setEndTime(END_TIME);
                shift2.setStartTime(START_TIME);
                shift2.setLibrarian(librarian);

                shifts.add(shift1);

                shifts.add(shift2);

                return shifts;
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
		lenient().when(shiftRepository.save(any(Shift.class))).thenAnswer(returnParameterAsAnswer);

    }

    // @Test
    // public void testCreateShift(){
    //     assertEquals(0, shiftService.getAllShifts().size());

    //     Librarian librarian = null;

    //     Shift shift = null;

    //     try{
    //         librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //         shift = shiftService.createShift(librarian, DAY, START_TIME, END_TIME);
    //     } catch (IllegalArgumentException e){
    //         fail();
    //     }

    //     assertNotNull(shift);
    //     assertEquals(DAY, shift.getDay());
    //     assertEquals(START_TIME, shift.getStartTime());
    //     assertEquals(END_TIME, shift.getEndTime());
    // }

    // @Test
    // public void testCreateShiftWithGetById(){
    //     assertEquals(0, shiftService.getAllShifts().size());

    //     Librarian librarian = null;

    //     Shift shift = null;

    //     Shift testShift = null;

    //     try{
    //         librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //         shift = shiftService.createShift(librarian, DAY, START_TIME, END_TIME);

    //         testShift = shiftService.getShiftById(SHIFT_KEY);

    //     } catch (IllegalArgumentException e){
    //         fail();
    //     }

    //     assertNotNull(shift);
    //     assertEquals(DAY, shift.getDay());
    //     assertEquals(START_TIME, shift.getStartTime());
    //     assertEquals(END_TIME, shift.getEndTime());

    //     assertNotNull(testShift);
    //     assertEquals(DAY, testShift.getDay());
    //     assertEquals(START_TIME, testShift.getStartTime());
    //     assertEquals(END_TIME, testShift.getEndTime());
    // }

    // @Test
    // public void testCreateShiftWithGetByIdFailNull(){
    //     assertEquals(0, shiftService.getAllShifts().size());

    //     Librarian librarian = null;

    //     Shift shift = null;

    //     Shift testShift = null;

    //     String error = null;
    //     try{
    //         librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //         shift = shiftService.createShift(librarian, DAY, START_TIME, END_TIME);

    //         testShift = shiftService.getShiftById(null);

    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertNotNull(shift);
    //     assertEquals(DAY, shift.getDay());
    //     assertEquals(START_TIME, shift.getStartTime());
    //     assertEquals(END_TIME, shift.getEndTime());

    //     assertNull(testShift);
    //     assertEquals(error, "Id is null");
    // }

    @Test
    public void testCreateShiftFailLibrarianNull(){
        assertEquals(0, shiftService.getAllShifts().size());

        Librarian librarian = null;

        Shift shift = null;

        String error = null;

        try{
            shift = shiftService.createShift(librarian, DAY, START_TIME, END_TIME);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals(error, "Librarian cannot be null");
        assertNull(shift);
    }

    @Test
    public void testCreateShiftFailDayNull(){
        assertEquals(0, shiftService.getAllShifts().size());

        Librarian librarian = null;

        Shift shift = null;

        String error = null;

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
            shift = shiftService.createShift(librarian, null, START_TIME, END_TIME);

        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals(error, "Day of Week cannot be null");
        assertNull(shift);
    }


    @Test
    public void testCreateShiftFailStartTimeNull(){
        assertEquals(0, shiftService.getAllShifts().size());

        Librarian librarian = null;

        Shift shift = null;

        String error = null;

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
            shift = shiftService.createShift(librarian, DAY, null, END_TIME);

        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals(error, "Start Time cannot be null");
        assertNull(shift);
    }

    @Test
    public void testCreateShiftFailEndTimeNull(){
        assertEquals(0, shiftService.getAllShifts().size());

        Librarian librarian = null;

        Shift shift = null;

        String error = null;

        try{
            librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
            shift = shiftService.createShift(librarian, DAY, START_TIME, null);

        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals(error, "End Time cannot be null");
        assertNull(shift);
    }

    // @Test
    // public void testCreateShiftFail2ShiftsSameDay(){
    //     assertEquals(0, shiftService.getAllShifts().size());

    //     Librarian librarian = null;

    //     Shift shift = null;
///
    //     String error = null;

    //     try{
    //         librarian = librarianService.createLibrarian(FIRSTNAME, LASTNAME, EMAIL, ISHEAD, USERNAME, PASSWORD);
    //         librarian.setId(LIBRARIAN_KEY);
    //         shift = shiftService.createShift(librarian, DAY, START_TIME, END_TIME);

    //     } catch (IllegalArgumentException e){
    //         error = e.getMessage();
    //     }

    //     assertEquals(error, "Cannot have 2 seperate shifts on the same day");
    //     assertNull(shift);
    // }
}
