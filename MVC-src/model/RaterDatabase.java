package com.iafinn;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.*;
import java.lang.Math; 

/**
 * Creates a HashMap of raters.
 * String (key) is rater id, and
 * Rater is rater object with the 
 * movies they've rated.
 */



class RaterDatabase {
    private static HashMap<String,Rater> ourRaters;
    // avgMap keeps track of [sum(ratings), sum((ratings-avg)^2), count] (value) for each rater id (key)
    private static HashMap<String, Double[]> avgMap; 
    private static void initialize() {
	    // this method is only called from addRatings 
		if (ourRaters == null) {
			ourRaters = new HashMap<String,Rater>();
			avgMap = new HashMap<String, Double[]>();
		}
	}

	protected static void initialize(String filename) {
 		if (ourRaters == null) {
 			ourRaters= new HashMap<String,Rater>();
 			avgMap = new HashMap<String, Double[]>();
 			addRatings(filename);
 		}
 	}	
 	
	

	private static void addRatings(String filename) {
        initialize(); 
	    try {
        	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        	InputStream input = classLoader.getResourceAsStream(filename);

            BufferedReader br = new BufferedReader(new InputStreamReader(input));
	        CSVParser csvp= new CSVParser(br,CSVFormat.RFC4180.withFirstRecordAsHeader());
	
	        for(CSVRecord rec : csvp) {
	                String id = rec.get("rater_id");
	                String item = rec.get("movie_id");
	                String rating = rec.get("rating");
	                addRaterRating(id,item,Double.parseDouble(rating));	                
	        } 
	        //updateAvgMap(); for Pearson Correlation
	        csvp.close();
	    }
        catch (IOException e) {
        	e.printStackTrace();
        	
        }
    }

/*
	private static void updateAvgMap() {
		//This calculates the sum((ratings-avg_rating)^2) for Pearson correlation 
		for (String raterId : avgMap.keySet()) {
			Rater currRater = ourRaters.get(raterId);
			Double currAvg = getAvgForRater(raterId);
			ArrayList<Double> currRatings = currRater.getRatings();
			double sumSqAvg = 0.0;
			for (double currR : currRatings) {
				sumSqAvg += (currR-currAvg) * (currR-currAvg);
			}
			Double[] avgCounter = avgMap.get(raterId);
			avgCounter[1] = sumSqAvg;
		}
	}
*/
	
	protected static double getAvgForRater(String raterId) {
		if (avgMap.containsKey(raterId)) {
			Double[] avgCounter = avgMap.get(raterId);
			return avgCounter[0]/avgCounter[2];
		}
		else {
			return -1.0;
		}	
	}
	
	protected static double getRootSumSqMinusAvg(String raterId) {
		if (avgMap.containsKey(raterId)) {
			Double[] avgCounter = avgMap.get(raterId);
			return Math.sqrt(avgCounter[1]);
		}
		else {
			return -1.0;
		}	
	
	}
	
	private static void addRaterRating(String raterID, String movieID, double rating) {
        initialize(); 
//        System.out.println(movieID);
        double movieAvg = MovieDatabase.getAvgRating(movieID);
        
        Rater rater =  null;
        double ssa = 1.0;
        //double ssa = (rating-movieAvg)*(rating-movieAvg);
        if (ourRaters.containsKey(raterID)) {
            rater = ourRaters.get(raterID);
            Double[] oldAvg = avgMap.get(raterID);
            oldAvg[0]+=rating; //update sum(rater_ratings)
            oldAvg[1]+=ssa; //update sum((rater_ratings - movie_avg)^2)
            oldAvg[2]+=1.0; //update count
        } 
        else { 
            rater = new EfficientRater(raterID);
            ourRaters.put(raterID,rater);
            Double[] newAvg = {rating, ssa, 1.0};
            avgMap.put(raterID, newAvg);
         }
         rater.addRating(movieID,rating);
    } 
	         
	protected static Rater getRater(String id) {
    	initialize();
    	
    	return ourRaters.get(id);
    }
	
	protected static ArrayList<Rater> getRaters() {
    	initialize();
    	ArrayList<Rater> list = new ArrayList<Rater>(ourRaters.values());
    	
    	return list;
    }
 
	protected static int size() {
	    return ourRaters.size();
    }      
}
