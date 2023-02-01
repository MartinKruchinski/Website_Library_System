package ca.mcgill.ecse321.projectgroup14.controller;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.dto.*;
import ca.mcgill.ecse321.projectgroup14.services.*;
import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;

@CrossOrigin(origins = "*")
@RestController
public class OpeningHourController {

    @Autowired
    private OpeningHourService openingHourService;

    @PostMapping (value = {"/openingHour/{day}", "/openingHour/{day}/"})
    public OpeningHourDto createOpeningHour (@PathVariable(name = "day") String day,
                                             @RequestParam(name = "start_time") String startTime,
                                             @RequestParam(name = "end_time") String endTime) {

        OpeningHour openingHour = openingHourService.createOpeningHour(DayOfWeek.valueOf(day), Time.valueOf(startTime), Time.valueOf(endTime));
        return OpeningHourController.convertToDto(openingHour);

    }

    @GetMapping (value = {"/openingHours/{id}", "/openingHours/{id}/"})
    public OpeningHourDto getOpeningHourById (@PathVariable("id") Long id) {

        return convertToDto(openingHourService.getOpeningHourById(id));

    }

    @GetMapping (value = {"/openingHours", "/openingHours/"})
    public List<OpeningHourDto> getOpeningHours () {

        // Following line taken from tutorial code

        return openingHourService.getAllOpeningHours().stream().map(p -> convertToDto(p)).collect(Collectors.toList());

    }

    @DeleteMapping (value = {"/openingHours/delete/{id}", "/openingHours/delete/{id}/"})
    public void deleteOpeningHourById (@PathVariable("id") Long id) {

        openingHourService.deleteOpeningHour(openingHourService.getOpeningHourById(id));

    }

    @PatchMapping (value = {"/openingHours/edit/{id}", "/openingHours/edit/{id}/"})
    public OpeningHourDto editOpeningHourById (@PathVariable("id") Long id,
                                     @RequestParam("day") String day,
                                     @RequestParam("start_time") String startTime,
                                     @RequestParam("end_time") String endTime) {

        OpeningHour toReturn = openingHourService.editOpeningHour(openingHourService.getOpeningHourById(id), OpeningHour.DayOfWeek.valueOf(day),
                                                                                Time.valueOf(startTime),
                                                                                Time.valueOf(endTime));
        return convertToDto(toReturn);
    }


    private static OpeningHourDto convertToDto (OpeningHour openingHour) {

        if (openingHour == null) throw new IllegalArgumentException("Opening Hour cannot be null");

        OpeningHourDto openingHourDto = new OpeningHourDto(openingHour.getId(),
                                            OpeningHourDto.DayOfWeek.valueOf(openingHour.getDay().toString()),
                                            openingHour.getStartTime(), openingHour.getEndTime());
        return openingHourDto;
    }



}
