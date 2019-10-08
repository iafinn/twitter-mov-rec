<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<link
	href="https://fonts.googleapis.com/css?family=Lato:400,900&amp;display=swap"
	rel="stylesheet">
<style>
* {
  box-sizing: border-box;
}
h4 {text-align:center;}
h3 {text-align:center;}
h2 {text-align:center;}
p {text-align:center;}
body {
	font-family: 'Lato', sans-serif;
	color: white;
	background-color: black;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position-x: center;
	background-position-y: top;
	background-image: url("resources/spotlight3.png");
	background-size: 1500px;
}

.content {
	max-width: 700px;
	font-size: 18px;
	margin: auto;
	background: rgba(0, 0, 0, 0.5);
	border-top: 15px solid rgba(0, 0, 0, 0.1);
	border-bottom: 15px solid rgba(0, 0, 0, 0.1);
	border-right: 15px solid rgba(0, 0, 0, 0.1);
	border-left: 15px solid rgba(0, 0, 0, 0.1);
}

table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

input {
  border: 1px solid transparent;
  background-color: DodgerBlue;
  padding: 10px;
  font-size: 16px;
  color: #fff;
  cursor: pointer;
}

.link { 
	color: dodgerblue;  /* CSS link color (red) */
	padding: 5px; }
.link:hover { color: white; } /* CSS link hover (green) */


tr:hover {background-color:rgb(64, 64, 64);}
</style>


<meta charset="UTF-8">
<title>Your Recommendations</title>
</head>
<body>
<div class="content">
<h2> Recommendations  </h2>



<% 
String results = (String) request.getAttribute("recMovies"); 
if (results.equals("")) {
	out.println("<h3>No movies returned for this query</h3>");
	out.println("<p>Try removing filters, or adding more movie ratings to your list.</p>");
	out.println("<br>");
	out.println("<br>");
	out.println("<br>");
}
else {
	out.println("<table>");
	out.println("<tr>");
	out.println("<th>Title</th>");
	out.println(" <th>Genres</th>");
	out.println("</tr>");
	String[] movies = results.split("::");
	for (int k=0; k<movies.length; k++) {
		String[] m = movies[k].split(";;");
		String id = m[0];
		String title = m[1];
		String genres = m[2].replaceAll("\\|", ", " );
		out.println("<tr>");
		out.println("<td><a class='link' href=\"https://www.imdb.com/title/tt" + String.format("%08d", Integer.parseInt(id)) + "/\">" + title + "</a></td>");
		out.println("<td>" + genres + "</td>");
}
	out.println("</table>");
	out.println("<h4> Click titles for link to IMDB page  </h4>");
}
%>

<input type=button value="Return to previous page" onCLick="history.back()">
</div>

</body>
</html>