package com.example.trackervision;

public class uploadLogInfo {
    private String showName, firstAirDate, posterURL, dateCompleted;

    public uploadLogInfo() {
    }

    public uploadLogInfo(String showName, String firstAirDate, String posterURL, String dateCompleted) {
        this.showName = showName;
        this.firstAirDate = firstAirDate;
        this.posterURL = posterURL;
        this.dateCompleted = dateCompleted;

    }

    public String getShowName() {
        return showName;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }
    public String getPosterURL() {
        return posterURL;
    }
    public String getDateCompleted() {
        return dateCompleted;
    }

}
