## Twitter-Based Recommendations

The web app is hosted on AWS elastic beanstalk and can be found [here](http://mov-rec.us-west-1.elasticbeanstalk.com/)

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
