package com.example.gwygw_000.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gwygw_000 on 2016/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Team {
    boolean active;
    String city;
    String conference;
    String division;
    String key;
    Long leagueID;
    String name;
    Long stadiumID;
    Long TeamID;

    public Team() {

    }

    @JsonProperty("Key")
    public String getKey() {return key;}

    @JsonProperty("Name")
    public String getName() {return name;}

    @JsonProperty("Division")
    public String getDivision() {return division;}

}
