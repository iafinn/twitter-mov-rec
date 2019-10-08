package com.iafinn;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
/**
 * Creates HashMap of movies id String (keys)
 * and associated Movie objects (values).
 */
public class MovieDatabase {
    private static HashMap<String, Movie> ourMovies;
    protected static void initialize(String moviefile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies(moviefile);
        }
    }
    
    protected static void initialize() { 	
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies("com/iafinn/movies_clean.csv");
        }
    }	

    private static ArrayList<Movie> loadCSV (String filename) {
        ArrayList<Movie> result = new ArrayList<Movie>();
        try {
        	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        	InputStream input = classLoader.getResourceAsStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF8"));
            CSVParser parser = new CSVParser(br,CSVFormat.RFC4180.withFirstRecordAsHeader());
            for (CSVRecord r : parser) {       
                if (r.size()==6) {
                	String id = r.get("id");
                	String title = r.get("title");
                	String year = r.get("year");
                	String genres =  r.get("genres");
                	String avgRating = r.get("avg_rating");
                	String numRatings = r.get("num_ratings");
                    Movie m = new Movie(id, title, year, genres, avgRating, numRatings);
                    result.add(m);  
                }  
            }
            parser.close();
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }    
    
    
    private static void loadMovies(String filename) {
        //Loader fr = new Loader();
        ArrayList<Movie> list = loadCSV(filename);
        for (Movie m : list) {
            ourMovies.put(m.getID(), m);
        }
    }

    protected static boolean containsID(String id) {
        initialize();
        return ourMovies.containsKey(id);
    }

    protected static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    protected static double getAvgRating(String id) {
        initialize();
        if (!ourMovies.containsKey(id)) {
        	return 6.7;//return global mean movie score if id is missing
        }
        return ourMovies.get(id).getAvgRating();
    }

    protected static int getNumRaters(String id) {
        initialize();
        return ourMovies.get(id).getNumRaters();
    }
    
    protected static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }
    
    protected static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    protected static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    protected static int size() {
        initialize();
        return ourMovies.size();
    }

    protected static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for(String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        return list;
    }
    
    protected static String getIdFromTitle(String myTitle) {
    	initialize();
    	for(String id : ourMovies.keySet()) {
    		Movie currMovie = ourMovies.get(id);
    		if (currMovie.getTitle().contentEquals(myTitle)) {
    			return id;
    		}
    	}
    	return "ID NOT FOUND";
    }
    
    protected static ArrayList<String> getMovieTitles() {
    	initialize();
    	ArrayList<String> results = new ArrayList<String>();
    	for (Movie m : ourMovies.values()) {
    		results.add(m.getTitle());
    	}
    	return results;
    }

}
