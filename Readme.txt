Programming language: Java
IDE used : Eclipse

Main class:  
Conversion - 
Loads the csv file and applied mapToItem method on each row to process the required output

Methods: 
1. static void processInputFile (String inputFilePath): 
Process the input CSV file and iterate through it by each row

2. static Function<String, AccessInfo> mapToItem = (line): 
Function to access each row and converting it to desired format by 
a. separating the current row by comma and storing cell values into corresponding AccessInfo variable.
b. Maintaining an active list for all the IP elements whose session are not expired.
c. Checking for all the active list elements if the session is expired.
d. If expired; writing its attributed to the output.txt file and removing it from the ActiveList

Other classes: 

1. AccessInfo: Blueprint to store the value of each record in a CSV file, 
2. ActiveID : Blueprint for store the Active IP elements in a list and write to the output as their session expire

