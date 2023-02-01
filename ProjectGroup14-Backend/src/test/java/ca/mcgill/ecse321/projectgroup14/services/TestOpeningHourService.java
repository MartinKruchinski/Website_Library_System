package ca.mcgill.ecse321.projectgroup14.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
//import java.util.ArrayList;
//import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup14.model.OpeningHour;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.repository.LibraryRepository;
import ca.mcgill.ecse321.projectgroup14.repository.OpeningHourRepository;


@ExtendWith(MockitoExtension.class)
public class TestOpeningHourService {

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private OpeningHourRepository openingHourRepository;

    @InjectMocks
    private OpeningHourService openingHourService;

    private static final Long HOURID = 303l;
    private static final DayOfWeek DAY = DayOfWeek.Monday;
    private static final String START = "02:00:00";
    private static final String END = "03:00:00";

    @BeforeEach
    public void setMockOutput () {

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(openingHourRepository.save(any(OpeningHour.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(openingHourRepository.existsById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {

            if (invocation.getArgument(0).equals(HOURID)) {
                return true;
            } else { return false; }

        });
        lenient().when(openingHourRepository.findOpeningHourById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {

            if (invocation.getArgument(0).equals(HOURID)) {
                OpeningHour openingHour = new OpeningHour();
                openingHour.setDay(DAY);
                openingHour.setStartTime(Time.valueOf(START));
                openingHour.setEndTime(Time.valueOf(END));
                return openingHour;
            } else { return null; }

        });

    }

    // @Test
    // public void TestCreateHour () {


    //     DayOfWeek day = DayOfWeek.Monday;
    //     Time start = Time.valueOf("02:00:00");
    //     Time end = Time.valueOf("03:00:00");

    //     OpeningHour hour = null;

    //     try { hour = openingHourService.createOpeningHour( day, start, end); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     assertNotNull(hour);
    //     assertEquals(day, hour.getDay());
    //     assertEquals(start, hour.getStartTime());
    //     assertEquals(end, hour.getEndTime());

    // }


    @Test
    public void TestCreateHourNoDay () {

        String error = null;

        DayOfWeek day = null;
        Time start = Time.valueOf("02:00:00");
        Time end = Time.valueOf("03:00:00");

        OpeningHour hour = null;

        try { hour = openingHourService.createOpeningHour( day, start, end); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(hour);
        assertEquals("Day of Week cannot be null", error);

    }

    @Test
    public void TestCreateHourNoStart () {

        String error = null;

        DayOfWeek day = DayOfWeek.Monday;
        Time start = null;
        Time end = Time.valueOf("03:00:00");

        OpeningHour hour = null;

        try { hour = openingHourService.createOpeningHour(day, start, end); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(hour);
        assertEquals("Start Time cannot be null", error);

    }

    @Test
    public void TestCreateHourNoEnd () {

        String error = null;

        DayOfWeek day = DayOfWeek.Monday;
        Time start = Time.valueOf("03:00:00");
        Time end = null;

        OpeningHour hour = null;

        try { hour = openingHourService.createOpeningHour(day, start, end); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(hour);
        assertEquals("End Time cannot be null", error);

    }

    @Test
    public void TestCreateHourStartAfterEnd () {

        String error = null;


        DayOfWeek day = DayOfWeek.Monday;
        Time start = Time.valueOf("03:00:00");
        Time end = Time.valueOf("02:00:00");

        OpeningHour hour = null;

        try { hour = openingHourService.createOpeningHour(day, start, end); }
        catch (IllegalArgumentException e) { error = e.getMessage(); }

        assertNull(hour);
        assertEquals("Start Time cannot be after End Time", error);

    }

    // @Test
    // public void TestCreateHourOverlap () {

    //     String error = null;


    //     DayOfWeek day = DayOfWeek.Monday;
    //     Time start = Time.valueOf("02:00:00");
    //     Time end = Time.valueOf("03:00:00");

    //     Time start2 = Time.valueOf("02:05:00");
    //     Time end2 = Time.valueOf("02:10:00");

    //     try { openingHourService.createOpeningHour(day, start, end); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     lenient().when(openingHourRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {

    //         OpeningHour openingHour = new OpeningHour();
    //         openingHour.setDay(DAY);
    //         openingHour.setStartTime(Time.valueOf(START));
    //         openingHour.setEndTime(Time.valueOf(END));
    //         List<OpeningHour> hourList = new ArrayList<OpeningHour>();
    //         hourList.add(openingHour);
    //         return hourList;

    //     });

    //     OpeningHour hour2 = null;

    //     try { hour2 = openingHourService.createOpeningHour(day, start2, end2); }
    //     catch (IllegalArgumentException e) { error = e.getMessage(); }

    //     assertNull(hour2);
    //     assertEquals("Only one Opening Hour per day", error);

    // }

    // @Test
    // public void TestGetById () {

    //     DayOfWeek day = DayOfWeek.Monday;
    //     Time start = Time.valueOf("02:00:00");
    //     Time end = Time.valueOf("03:00:00");

    //     OpeningHour hour = null;

    //     try { hour = openingHourService.createOpeningHour(day, start, end); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     try { hour = openingHourService.getOpeningHourById(HOURID); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     assertNotNull(hour);
    //     assertEquals(day, hour.getDay());
    //     assertEquals(start, hour.getStartTime());
    //     assertEquals(end, hour.getEndTime());

    // }

    // @Test
    // public void TestGetByIdWrong () {

    //     String error = null;


    //     DayOfWeek day = DayOfWeek.Monday;
    //     Time start = Time.valueOf("02:00:00");
    //     Time end = Time.valueOf("03:00:00");

    //     OpeningHour hour = null;

    //     try { hour = openingHourService.createOpeningHour(day, start, end); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     hour = null;

    //     try { hour = openingHourService.getOpeningHourById(101l); }
    //     catch (IllegalArgumentException e) { error = e.getMessage(); }

    //     assertNull(hour);
    //     assertEquals("OpeningHour does not exist", error);

    // }

    // @Test
    // public void TestGetAll () {


    //     DayOfWeek day = DayOfWeek.Monday;
    //     Time start = Time.valueOf("02:00:00");
    //     Time end = Time.valueOf("03:00:00");

    //     try { openingHourService.createOpeningHour(day, start, end); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     lenient().when(openingHourRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {

    //         OpeningHour openingHour = new OpeningHour();
    //         openingHour.setDay(DAY);
    //         openingHour.setStartTime(Time.valueOf(START));
    //         openingHour.setEndTime(Time.valueOf(END));
    //         List<OpeningHour> hourList = new ArrayList<OpeningHour>();
    //         hourList.add(openingHour);
    //         return hourList;

    //     });

    //     List<OpeningHour> hours = new ArrayList<OpeningHour>();

    //     try { hours = openingHourService.getAllOpeningHours(); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     assertNotNull(hours);
    //     assertEquals(day, hours.get(0).getDay());
    //     assertEquals(start, hours.get(0).getStartTime());
    //     assertEquals(end, hours.get(0).getEndTime());

    // }

    // @Test
    // public void TestDelete () {

    //     DayOfWeek day = DayOfWeek.Monday;
    //     Time start = Time.valueOf("02:00:00");
    //     Time end = Time.valueOf("03:00:00");

    //     OpeningHour hour = null;
    //     lenient().when(openingHourRepository.existsById(any())).thenReturn(true);

    //     try { hour = openingHourService.createOpeningHour(day, start, end); }
    //     catch (IllegalArgumentException e) { fail(); }

    //     try { hour = openingHourService.deleteOpeningHour(hour); }
    //     catch (IllegalArgumentException e) { System.out.print(e.getMessage()); fail();}

    //     assertNull(hour);

    // }

}
