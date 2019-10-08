package com.iafinn;
// An immutable passive data object (PDO) to represent item data
public class Movie {
    private String id;
    private String title;
    private int year;
    private String genres;
    double avgRating;
    int numRaters;
    protected Movie (String anID, String aTitle, String aYear, String theGenres, String avgR, String numR) {
        // just in case data file contains extra whitespace
        id = anID.trim();
        title = aTitle.trim();
        year = Integer.parseInt(aYear.trim());
        genres = theGenres;
        avgRating = Double.parseDouble(avgR);
        numRaters = Integer.parseInt(numR);
    }

    // Returns ID associated with this item
    protected String getID () {
        return id;
    }

    protected double getAvgRating () {
        return avgRating;
    }
    
    protected int getNumRaters () {
        return numRaters;
    }
    
    // Returns title of this item
    protected String getTitle () {
        return title;
    }

    // Returns year in which this item was published
    protected int getYear () {
        return year;
    }

    // Returns genres associated with this item
    protected String getGenres () {
        return genres;
    }

    // Returns a string of the item's information
    public String toString () {
        String result = "Movie [id=" + id + ", title=" + title + ", year=" + year;
        result += ", genres= " + genres + "]";
        return result;
    }
}
