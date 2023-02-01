package ca.mcgill.ecse321.projectgroup14.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.sql.Date;

@Entity
public class Booking {

    public enum BookingType {
        Reservation,
        Checkout
    }
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private BookingType bookingType;
    private Date startDate;
    private Date endDate;

    @ManyToOne (optional = false)
    private Item item;

    @ManyToOne (optional = false)
    private Customer customer;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public BookingType getBookingType () {
        return bookingType;
    }

    public void setBookingType (BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate (Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate (Date endDate) {
        this.endDate = endDate;
    }

    public Item getItem () {
        return this.item;
    }

    public void setItem (Item item) {
        this.item = item;
    }

    public Customer getCustomer () {
        return customer;
    }

    public void setCustomer (Customer customer) {
        this.customer = customer;
    }

}
