package com.iafinn;
import java.util.ArrayList;

/**
 * Interface for raters and the movies that
 * they have rated
 */

public interface Rater {
	void addRating(String item, double rating) ;

    boolean hasRating(String item) ;

    String getID() ;

    double getRating(String item) ;
    
    ArrayList<Double> getRatings() ;
    
    int numRatings() ;
    
    double getAvgForRater() ;

    ArrayList<String> getItemsRated() ;

    double getRootSumSqMinusAvgForRater();
}
