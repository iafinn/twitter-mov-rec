package com.iafinn;
import java.util.ArrayList;
import java.util.Collections;
/**
 * This class calculates the similarities
 * between the user (me) and the database of
 * raters. Then it calculates the weighted average
 * for movie ratings using the similarities
 * and filters for movies within a particular
 * year and genre
 */
public class SimilarityModel {

    private double cosineSimilarity(Rater me, Rater r) {
    	// input: 2 rater objects (me and someone else)
    	// output: similarity (dot product between two raters)
        double sum = 0.0;
        ArrayList<String> meRatings = me.getItemsRated();
        for (String id : meRatings) {
            if (r.hasRating(id)) {
            	double avg = MovieDatabase.getAvgRating(id);//important -- subtract average for dot product 
                sum += (me.getRating(id)-avg) * (r.getRating(id) - avg);
            }
        }
        return sum/(me.getRootSumSqMinusAvgForRater() * r.getRootSumSqMinusAvgForRater() + 1.0);
    }
     
    private ArrayList<Rating> getSimilaritiesRater(Rater me, int numSimilarRaters) {
    	// input: rater me and number of similar raters
    	// output: an ArrayList of Rating objects that contain the id and similarity
    	// of raters that are similar to me (similarity > 0)
    	String id = me.getID();
        ArrayList<Rating> similarities = new ArrayList<Rating>();
        for (Rater r : RaterDatabase.getRaters()) {
            String currID = r.getID();
            double sim = cosineSimilarity(me, r);
            // make sure curr ID is not me and similarity>0
            if (!currID.equals(id) && sim>0.0) {
                similarities.add(new Rating(currID, sim));
            }
        }
        Collections.sort(similarities);
        Collections.reverse(similarities);
        if (numSimilarRaters>similarities.size()) {
        	return similarities;
        }
        return new ArrayList<Rating>(similarities.subList(0, numSimilarRaters));
    }

    protected ArrayList<Rating> 
	    getSimilarRatingsByFilterRater(Rater me, int numSimilarRaters, 
	    int minimalRaters, Filter filterCriteria) {
        // input: a rater object (me), number of similar raters,
    	// minimal raters per movie, and filters for the output
    	
	    //get top numSimilarRaters from the similar raters sorted list
	    ArrayList<Rating> similarRaters = getSimilaritiesRater(me, numSimilarRaters);
	    ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
	    ArrayList<Rating> ratings = new ArrayList<Rating>();
	    for (String movieId : movies) {
	        double sumRatings = 0.0;
	        int count = 0;
	        for (Rating r : similarRaters) {
	            //find rater in RDB
	            Rater currRater = RaterDatabase.getRater(r.getItem());  
	            if (currRater.hasRating(movieId)) {
	                //scale by similarity => r.getValue()
	                sumRatings += currRater.getRating(movieId) * r.getValue();
	                count+=1;
	            }
	        }
	        // make sure number of people that rated movie is >= min count and 
	        // I haven't rated it
	        if (count>=minimalRaters && !me.hasRating(movieId)) {
	            double rating = sumRatings/ (double) count;
	            ratings.add(new Rating(movieId, rating));
	        }
	    }
	    Collections.sort(ratings);
	    Collections.reverse(ratings);
	    return  ratings;
    }
}
