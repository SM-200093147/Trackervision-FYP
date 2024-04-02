package com.example.trackervision;

public class uploadScheduleInfo {
    private String showName, showSeason, showEpisode, date, time, userUid;

    public uploadScheduleInfo() {
    }

    public uploadScheduleInfo(String showName, String showSeason, String showEpisode, String date, String time, String userUid) {
        this.showName = showName;
        this.showSeason = showSeason;
        this.showEpisode = showEpisode;
        this.date = date;
        this.time = time;
        this.userUid = userUid;
    }

    public String getShowName() {
        return showName;
    }

    public String getShowSeason() {
        return showSeason;
    }

    public String getShowEpisode() {
        return showEpisode;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserUid() {
        return userUid;
    }

}
