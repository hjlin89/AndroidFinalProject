package com.example.gwygw_000.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by HuijunLin on 4/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class News implements Serializable {
    String Content, Source, Team, Title, Updated, Url, TermsOfUse, PlayerID;
    Long NewsID, TeamID;
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

    @JsonProperty("PlayerID")
    public String getPlayerID() {
        return PlayerID;
    }

    @JsonProperty("Source")
    public String getSource() {
        return Source;
    }

    @JsonProperty("Team")
    public String getTeam() {
        return Team;
    }

    @JsonProperty("TeamID")
    public Long getTeamID() {
        return TeamID;
    }

    @JsonProperty("TermsOfUse")
    public String getTermsOfUse() {
        return TermsOfUse;
    }

    @JsonProperty("Updated")
    public String getUpdated() {
        return Updated;
    }

    @JsonProperty("Url")
    public String getUrl() {
        return Url;
    }

    @JsonProperty("Content")
    public void setContent(String content) {
        Content = content;
    }

    @JsonProperty("NewsID")
    public void setNewsID(Long newsID) {
        NewsID = newsID;
    }

    @JsonProperty("PlayerID")
    public void setPlayersID(String playerID) {
        PlayerID = playerID;
    }

    @JsonProperty("Sources")
    public void setSources(String source) {
        Source = source;
    }

    @JsonProperty("Team")
    public void setTeam(String team) {
        Team = team;
    }

    @JsonProperty("TeamID")
    public void setTeamID(Long teamID) {
        TeamID = teamID;
    }

    @JsonProperty("TermsOfUse")
    public void setTermsOfUse(String termsOfUse) {
        TermsOfUse = termsOfUse;
    }

    @JsonProperty("Updated")
    public void setUpdated(String updated) {
        Updated = updated;
    }

    @JsonProperty("Url")
    public void setUrl(String url) {
        Url = url;
    }

}
