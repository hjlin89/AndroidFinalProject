package com.example.gwygw_000.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gwygw_000 on 2016/4/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    String firstName;
    String lastName;
    Long Height;
    Long Weight;
    Long Jersey;
    Long Experience;
    String Status;
    String team;
    String DOB;
    String position;
    String photoUrl;


    public Player() {

    }

    @JsonProperty("FirstName")
    public String getFirstName() {return firstName;}

    @JsonProperty("LastName")
    public String getLastName() {return lastName;}

    @JsonProperty("Height")
    public Long getHeight() {return Height;}

    @JsonProperty("Team")
    public String getTeam() {return team;}

    @JsonProperty("BirthDate")
    public String getDOB() {return DOB;}

    @JsonProperty("Position")
    public String getPosition() {return position;}

    @JsonProperty("PhotoUrl")
    public String getPhotoUrl() {return photoUrl;}

    @JsonProperty("Weight")
    public Long getWeight() {return Weight;}

    @JsonProperty("Jersey")
    public Long getJersey() {return Jersey;}

    @JsonProperty("Experience")
    public Long getExperience() {return Experience;}

    @JsonProperty("Status")
    public String getStatus() {return Status;}
}
