package com.iafinn;

/**
 * Filter for genre
 */
public class GenreFilter implements Filter {
    private String myGenre;
    
    protected GenreFilter(String genre) {
        myGenre = genre;
    }
    
    @Override
	public boolean satisfies(String id) {
        if (MovieDatabase.getGenres(id).indexOf(myGenre)==-1) {
            return false;
        }
        return true;
    }
}
