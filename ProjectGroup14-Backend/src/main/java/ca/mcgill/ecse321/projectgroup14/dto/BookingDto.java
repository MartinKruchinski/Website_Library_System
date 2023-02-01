package ca.mcgill.ecse321.projectgroup14.dto;

import java.sql.Date;

public class BookingDto {

    public enum BookingType {
        Reservation,
        Checkout
    }

    private Long Id;
    private BookingType bookingType;
    private Date startDate;
    private Date endDate;
    private CustomerDto customer;
    private ItemDto item;

    public BookingDto () {}

    public BookingDto (Long id, BookingType aBookingType, Date aStartDate, Date aEndDate, ItemDto item, CustomerDto customer){

        this.Id = id;
        this.bookingType = aBookingType;
        this.startDate = aStartDate;
        this.endDate = aEndDate;
        this.item = item;
        this.customer = customer;
    
    }

    public Long getId () {
        return Id;
    }

    public BookingType getBookingType () {
        return bookingType;
    }

    public Date getStartDate () {
        return startDate;
    }

    public Date getEndDate () {
        return endDate;
    }

    public CustomerDto getCustomer () {
        return customer;
    }

    public void setCustomer (CustomerDto aCustomer) {
        this.customer = aCustomer;
    }

    public ItemDto getItem () {
        return item;
    }

    public void setItem (ItemDto aItem) {
        this.item = aItem;
    }

    
}
