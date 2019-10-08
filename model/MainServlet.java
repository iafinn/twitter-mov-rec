package com.iafinn;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main servlet for the web app
 * Processes movie ratings and filter requests
 * and calls the recommendation model
 * then forwards results to results.jsp
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		processRequest(req, res);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		MovieDatabase.initialize("com/iafinn/movies_clean.csv");
		ArrayList<String> titles = MovieDatabase.getMovieTitles();
		
		Rater me = new EfficientRater("me");
		for (String title : titles) {
			Object rated = req.getParameter(title);
			if (rated!=null) {
				me.addRating(MovieDatabase.getIdFromTitle(title), Double.parseDouble((String) rated));
			}
		}
		
        String year1 = (String) req.getParameter("startyear");
        String year2 = (String) req.getParameter("endyear");
        int startYear = 1900;
        int endYear = 2020;
        
        if (!year1.equals("Start year")) {
        	startYear = Integer.parseInt(year1);
        }

        if (!year2.equals("End year")) {
        	endYear = Integer.parseInt(year2);
        }
        
        String genre = (String) req.getParameter("genre");
        ModelRunner mr = new ModelRunner();
		List<Rating> recs = mr.printSimilarRatingsByYearAndGenre(me, startYear, endYear, genre);
		String movie = "";
		for (int k=0; k<recs.size() && k<=10; k++) {
			String id = recs.get(k).getItem();
			movie += id + ";;" + MovieDatabase.getTitle(id) + ";;" +  MovieDatabase.getGenres(id) + "::";
		}

		req.setAttribute("recMovies", movie);
		RequestDispatcher rd = req.getRequestDispatcher("results.jsp");
		rd.forward(req,  res);	
	}
}
