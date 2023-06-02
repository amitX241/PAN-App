## Running the code

Do a git clone of the repository and switch branch to "optimization".
- git clone https://github.com/amitX241/PAN-App.git
- git checkout optimization

Then Run the PANMain.java to start the application.
Preferred IDE: Eclipse

## Menu Driven Code Instructions
After running the code you will have 4 options
- Take Input
- Search for Information
- Display All PAN Information
- Quit
The options are self explainatory.
After running the application, first choose option 1 to Take Input,
Then choose option 2 or option 3 to Search or Display All results.



## Application Overview

## Input
- For input to the code here we have used an input text file named accountfile.txt which consist of pan numbers, name, address in a comma separated manner.
- The program reads through each line and stores all the pan number, corresponding name and address in a object which is later used for hashing and storing in hash table.
- For hashing we are only using the pan number.

## Output
- The program is mainly about storing Pan numbers in hash table and searching if the given Pan number is available in the hash table.
- Therefore we are using another file named findaccountfile.txt which consist of Pan numbers to be searched.
- Other outputs like menu, found or not, hashing results are shown in the console itself.

## TimeComplexity 
