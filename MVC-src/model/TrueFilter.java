package com.iafinn;

/**
 * satisfies everything (no filter)
 * 
*/

public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
