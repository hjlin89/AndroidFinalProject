package com.example.gwygw_000.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by HuijunLin on 4/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class News implements Serializable {
    String Content, Source, Team, Title, Updated, Url, TermsOfUse;
    Long NewsID, PlayerID, TeamID;
    public News() {

    }

    @JsonProperty("Title")
    public String getTitle() {
        return Title;
    }

    @JsonProperty("Title")
    public void setTitle(String Title) {
        this.Title = Title;
    }

    @JsonProperty("Content")
    public String getContent() {
        return Content;
    }

    @JsonProperty("NewsID")
    public Long getNewsID() {
        return NewsID;
    }

    public Long getPlayerID() {
        return PlayerID;
    }

    public String getSource() {
        return Source;
    }

    public String getTeam() {
        return Team;
    }

    public Long getTeamID() {
        return TeamID;
    }

    public String getTermsOfUse() {
        return TermsOfUse;
    }

    public String getUpdated() {
        return Updated;
    }

    public String getUrl() {
        return Url;
    }

    @JsonProperty("Content")
    public void setContent(String content) {
        Content = content;
    }

    public void setNewsID(Long newsID) {
        NewsID = newsID;
    }

    public void setPlayersID(Long playerID) {
        PlayerID = playerID;
    }

    public void setSources(String source) {
        Source = source;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public void setTeamID(Long teamID) {
        TeamID = teamID;
    }

    public void setTermsOfUse(String termsOfUse) {
        TermsOfUse = termsOfUse;
    }

    public void setUpdated(String updated) {
        Updated = updated;
    }

    public void setUrl(String url) {
        Url = url;
    }

}
