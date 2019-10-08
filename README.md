## Twitter-Based Recommendations

### [Access web application here](http://mov-rec.us-west-1.elasticbeanstalk.com/)

The web app uses twitter data from [movie tweetings](https://github.com/sidooms/MovieTweetings) to generate movie recommendations. The recommendation algorithm is user-based collaborative filtering combined with content filtering. Similarity is computed with a modified cosine method. More details are available [here](http://mov-rec.us-west-1.elasticbeanstalk.com/algorithm.html). The web app is deployed on AWS elastic beanstalk with Apache Tomcat 8.5 and Java 8 EE. I originally started this project for a capstone in the Coursera/Duke specialization: "Java Programming and Software Engineering Fundamentals." I expanded the dataset, changed the algorithm and structure of the model, and built the web app using Servlet/JSP and HTML/CSS/Javascript.

## Prerequisites

- Java EE 8
- Apache Tomcat 8.5

## Directory Overview

```
├───data                    <- data and scripts to processes data
│   ├───processed           <- data used in web app
│   └───raw                 <- original data
├───EDA                     <- exploratory data analysis NBs
├───model                   <- java files for model
└───web-app                 <- files for web app
    ├───src			
    │   └───com
    │       └───iafinn      <- library with backend java files for model
    └───WebContent          <-all content for the frontend + jsp
        ├───META-INF
        ├───resources
        └───WEB-INF
            └───lib 
```
