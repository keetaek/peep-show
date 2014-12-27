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
    private FinderDTO[] finders;
    private MediaDTO[] media;
    private LocationDTO location;
    private Date deadline;
    private String comments;


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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public MediaDTO[] getMedia() {
        return media;
    }

    public void setMedia(MediaDTO[] media) {
        this.media = media;
    }

    public FinderDTO[] getFinders() {
        return finders;
    }

    public void setFinders(FinderDTO[] finders) {
        this.finders = finders;
    }

    /**
     * Extending the User class to add the status property
     */
    public static class FinderDTO extends UserDTO {

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
