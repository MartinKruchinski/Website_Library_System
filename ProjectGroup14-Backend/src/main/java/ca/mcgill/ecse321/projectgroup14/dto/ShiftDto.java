package ca.mcgill.ecse321.projectgroup14.dto;

import java.sql.Time;

public class ShiftDto {

    public enum DayOfWeek {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday

    }
    private Long id;
    private DayOfWeek dayOfWeek;
    private Time startTime;
    private Time endTime;
    private LibrarianDto librarianDto;

    public ShiftDto () {}

    public ShiftDto (Long id, LibrarianDto librarianDto, DayOfWeek dayOfWeek, Time startTime, Time endTime) {

        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.librarianDto = librarianDto;

    }

    public Long getId () {
        return this.id;
    }

    public DayOfWeek getDayOfWeek () {
        return this.dayOfWeek;
    }

    public Time getStartTime () {
        return this.startTime;
    }

    public Time getEndTime () {
        return this.endTime;
    }

    public LibrarianDto getLibrarianDto() {
        return librarianDto;
    }

    public void setLibrarianDto(LibrarianDto librarianDto) {
        this.librarianDto = librarianDto;
    }

}
