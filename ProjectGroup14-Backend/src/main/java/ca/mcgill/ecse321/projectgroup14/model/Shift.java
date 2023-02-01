package ca.mcgill.ecse321.projectgroup14.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ca.mcgill.ecse321.projectgroup14.model.OpeningHour.DayOfWeek;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.sql.Time;

@Entity
public class Shift {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private DayOfWeek day;
    private Time startTime;
    private Time endTime;

    @ManyToOne (optional = false)
    private Librarian librarian;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public DayOfWeek getDay () {
        return day;
    }

    public void setDay (DayOfWeek day) {
        this.day = day;
    }

    public Time getStartTime () {
        return startTime;
    }

    public void setStartTime (Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime () {
        return endTime;
    }

    public void setEndTime (Time endTime) {
        this.endTime = endTime;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian (Librarian librarian) {
        this.librarian = librarian;
    }

}
