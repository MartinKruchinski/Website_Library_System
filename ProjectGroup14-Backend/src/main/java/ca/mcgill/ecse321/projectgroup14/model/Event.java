package ca.mcgill.ecse321.projectgroup14.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Event {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private Date date;
    private Time startTime;
    private Time endTime;


    @OneToOne (optional = false)
    private Customer customer;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
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

    public Customer getCustomer () {
        return this.customer;
    }

    public void setCustomer (Customer customer) {
        this.customer = customer;
    }

}
