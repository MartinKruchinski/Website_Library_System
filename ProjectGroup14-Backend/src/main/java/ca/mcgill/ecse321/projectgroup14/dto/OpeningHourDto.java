package ca.mcgill.ecse321.projectgroup14.dto;

import java.sql.Time;

public class OpeningHourDto {

    private Long id;
    public enum DayOfWeek {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday

    }
    private DayOfWeek dayOfWeek;
    private Time startTime;
    private Time endTime;
    
    public OpeningHourDto () {}

    public OpeningHourDto (Long id, DayOfWeek dayOfWeek, Time startTime, Time endTime) {

        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;

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

}
