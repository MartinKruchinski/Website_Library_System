package ca.mcgill.ecse321.projectgroup14.controller;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup14.dto.LibrarianDto;
import ca.mcgill.ecse321.projectgroup14.dto.ShiftDto;
import ca.mcgill.ecse321.projectgroup14.model.Librarian;
import ca.mcgill.ecse321.projectgroup14.model.Shift;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.projectgroup14.services.LibrarianService;
import ca.mcgill.ecse321.projectgroup14.services.ShiftService;

@CrossOrigin(origins = "*")
@RestController
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private LibrarianService librarianService;

    @PostMapping (value = {"/shift/{librarian_id}", "/shift/{librarian_id}/"})
    public ShiftDto createShift(@PathVariable(name = "librarian_id") Long id,
                                @RequestParam(name = "day") String day,
                                @RequestParam(name = "start_time") String startTime,
                                @RequestParam(name = "end_time") String endTime) {

        Shift shift = shiftService.createShift(librarianService.getLibrarianById(id), DayOfWeek.valueOf(day), Time.valueOf(startTime), Time.valueOf(endTime));

        return convertToDto(shift);
    }

    @GetMapping(value = { "/shifts", "/shifts/" })
    public List<ShiftDto> getShifts(){

        return shiftService.getAllShifts().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/shifts/librarian/{librarian_id}", "/shifts/librarian/{librarian_id}/" })
    public List<ShiftDto> getLibrarianShifts(@PathVariable(name = "librarian_id") Long id){

        return shiftService.getShiftsByLibrarianId(id).stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/shifts/{shiftId}", "/shifts/{shiftId}/" })
    public ShiftDto getShiftById(@PathVariable(name = "shiftId") Long id){
        return convertToDto(shiftService.getShiftById(id));
    }

    @DeleteMapping (value = {"/shifts/delete/{shift_id}", "/shifts/delete/{shift_id}/"})
    public void deleteShift (@PathVariable("shift_id") Long id) {

        shiftService.deleteShift(shiftService.getShiftById(id));

    }

    private static LibrarianDto convertToDto(Librarian librarian){

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


    private static ShiftDto convertToDto(Shift shift){

        if (shift == null) throw new IllegalArgumentException("Shift cannot be null");

        ShiftDto shiftDto = new ShiftDto(shift.getId(),
                            convertToDto(shift.getLibrarian()),
                            ShiftDto.DayOfWeek.valueOf(shift.getDay().toString()),
                            shift.getStartTime(),
                            shift.getEndTime());

        return shiftDto;
    }

}
