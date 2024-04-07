package com.example.trackervision;

public class uploadWatchlistInfo {
    private String showName, firstAirDate, averageRating, posterURL;

    public uploadWatchlistInfo() {
    }

    public uploadWatchlistInfo(String showName, String averageRating, String firstAirDate, String posterURL) {
        this.showName = showName;
        this.averageRating = averageRating;
        this.firstAirDate = firstAirDate;
        this.posterURL = posterURL;

    }

    public String getShowName() {
        return showName;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getPosterURL() {
        return posterURL;
    }
}
