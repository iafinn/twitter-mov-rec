package com.iafinn;

/**
 * This class holds a hashmap of  movie ratings for a 
 * particular user myID. The string (key) is the id of 
 * a movie. The rating object holds the rating for the movie.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math; 

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String,Rating> myRatings;

    protected EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey(item);
       
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (myRatings.containsKey(item)) {
            return myRatings.get(item).getValue();
        }
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(Rating r : myRatings.values()){
            list.add(r.getItem());
        }
        return list;
    }

    public ArrayList<Double> getRatings() {
        ArrayList<Double> list = new ArrayList<Double>();
        for(Rating r : myRatings.values()){
            list.add(r.getValue());
        }
        return list;
    }
    
    public double getAvgForRater() { 	
    	ArrayList<Double> ratings = getRatings();
    	int len = ratings.size();
    	if (len==0) {
    		return -1;
    	}
    	double sum = 0.0;
    	for (double r : ratings) {
    		sum +=r;
    	}
    	return sum/len;	
    }

    public double getRootSumSqMinusAvgForRater() { 	
    	double sum = 0.0;
    	for (String movieId : myRatings.keySet()) {
    		double currRating = myRatings.get(movieId).getValue();
    		double avg = MovieDatabase.getAvgRating(movieId); 
    	    sum +=(currRating-avg)*(currRating-avg);
    	}
    	return Math.sqrt(sum);	
    }
}
