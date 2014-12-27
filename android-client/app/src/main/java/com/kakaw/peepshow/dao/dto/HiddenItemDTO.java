package com.kakaw.peepshow.dao.dto;

import java.util.Date;

/**
 * Created by keetaekhong on 11/16/14.
 *
 * Sample JSON -
 *
 *  {
         "dropper":{
             "id": 1234,
             "name": "Ben Bradley"
         },
         "location":{
            "coordinates": {"latitude":42.0001,"longitude":23.23123}
         },
         "deadline":"12/04/2014",
         "dropId": 123241
    }
 *
 */
public class HiddenItemDTO {
    private UserDTO dropper;
    private LocationDTO location;
    private Date deadline;
    private String dropId;

    public String getDropId() {
        return dropId;
    }

    public void setDropId(String dropId) {
        this.dropId = dropId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public UserDTO getDropper() {
        return dropper;
    }

    public void setDropper(UserDTO dropper) {
        this.dropper = dropper;
    }

}
