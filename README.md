# Room Forming Machine

A Java tool for constructing preference-based or randomized room assignments for students/campers/scouts when on an vacation/field trip/overnight. Originally written as staff member of Achva West Coast Tour and used to fast track room-construction process while accommodating participant requests. Note: preference-based rooming currently only supports three-person rooms.  

## Use

1. Create a file `names.txt` which looks something like this one:
    ```
    NAMES
    Alice Brown
    Bob White
    Carol Green

    DISREQUESTS
    Alice Brown -> Bob White       # Alice does not want to room with Bob

    REQUESTS
    Bob White -> Carol Green       # Bob wants to room with Carol
    
    PRIORITY
    Carol Green -> 1
    Bob White -> 2
    ```
Priority (the lower the better) is linked with conduct in rooms and politeness to guests - the algorithm won't construct a room with too high of a cumulative priority to avoid bothering fellow travelers in hotels (this is of course editable). 

2. Open `Terminal`

3. Navigate to project directory (be sure to place `names.txt` in same folder. 

4. run `javac *.java` to compile the project

5. run `java Main args[0] args[1] args[2]` to run the project

`args[0]`: input file, in our case `names.txt` 
    
 `args[1]`: whether or not you're running preference-based or randomized rooming. This argument should be                "preferences" for preference-based rooms, anything else for randomized.
    
 `args[2]`: the number of roommates desired per room. Should be 3 for preferenced-based, 2/3/4 for randomized. 
