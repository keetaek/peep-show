package com.kakaw.peepshow.dao.dto;

import java.util.Date;

/**
 * Created by keetaekhong on 11/16/14.
 *
 * Sample JSON -
 *
 *
 {
     "dropId": 1234,
     "finders":[
     {
         "id": 1234,
         "name": "Ben Bradley"
     },
     {
         "id": 5678,
         "name": "John Smith"
     },                {
         "id": 9012,
         "name": "John Doe"
     }
     ],
     "photo":"http://localhost:8080/photo.jpg",
     "location":{
        "coordinates": {"latitude":42.0001,"longitude":23.23123}
     },
     "deadline": "01/04/2015"
 }
 */
public class DroppedItemDTO {

    private String dropId;
    private UserDTO[] finders;
    private String photo;
    private LocationDTO location;
    private Date deadline;


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

}
