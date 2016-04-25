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
    String team;
    String DOB;
    String position;
    String photoUrl;
    //

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
}
