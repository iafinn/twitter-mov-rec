package com.iafinn;
import java.util.List;
/**
 * This runs the recomendation model
 * for a fixed number of similar raters,
 * and minimal raters per movie. It takes in year
 * and genre guidelines to make filter objects
 * 
 */
public class ModelRunner {
    protected List<Rating> printSimilarRatingsByYearAndGenre(Rater me, int startYear, int endYear, String genre) {
        String movieFile = "com/iafinn/movies_clean.csv";
        String raterFile = "com/iafinn/ratings_clean.csv";
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(raterFile);           
        int numSimilarRaters = 500;
        int minimalRaters = 5;
        AllFilters f = new AllFilters();
        Filter f1 = new YearFilter(startYear, endYear);
        f.addFilter(f1);
        if (!genre.equals("Genre")) {
        	Filter f2 = new GenreFilter(genre);
        	f.addFilter(f2);	
        }
        SimilarityModel fr2 = new SimilarityModel();
        List<Rating> ratings = fr2.getSimilarRatingsByFilterRater(me, numSimilarRaters, 
            minimalRaters, f);
        if (ratings.size()>10) {
        	ratings = ratings.subList(0, 10);
        }
        return ratings;
    }
}
