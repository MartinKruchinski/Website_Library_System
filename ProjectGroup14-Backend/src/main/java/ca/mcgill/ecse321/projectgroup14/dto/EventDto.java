package ca.mcgill.ecse321.projectgroup14.dto;

import java.sql.Date;
import java.sql.Time;

public class EventDto {

    private Date date;
    private Time startTime;
    private Time endTime;
    private Long id;
    private CustomerDto customerDto;

    //Empty Constructor
    public EventDto(){
    }

    public EventDto(Long id, Date date, Time startTime, Time endTime, CustomerDto customerDto){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
        this.customerDto = customerDto;
    }

    public Date getDate(){
        return date;
    }

    public Time getStartTime(){
        return startTime;
    }

    public Time getEndTime(){
        return endTime;
    }

    public Long getID(){
        return id;
    }

    public void setCustomerDto(CustomerDto customerDto){
        this.customerDto = customerDto;
    }

    public CustomerDto getCustomerDto(){
        return customerDto;
    }

}
