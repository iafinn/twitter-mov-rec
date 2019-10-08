package com.iafinn;

/**
 * satisfies movies between start and end
 * year inclusive
 * 
*/

public class YearFilter implements Filter {
	private int startYear;
	private int endYear;
	
	public YearFilter(int sYear, int eYear) {
		startYear = sYear;
		endYear = eYear;
	}
	
	@Override
	public boolean satisfies(String id) {
		return (MovieDatabase.getYear(id) >= startYear) && (MovieDatabase.getYear(id) <= endYear);
	}

}