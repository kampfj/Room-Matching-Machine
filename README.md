# Room Matching Machine

A Java tool for constructing preference-based or randomized room assignments for students/campers/scouts when on an vacation/field trip/overnight. Originally written as staff member for Achva West Coast Summer Tour and used to fast track room-construction process while accommodating participant requests. 

Notes: 

       - preference-based rooming currently only supports three-person rooms

       - randomized rooming takes into account disrequests (participants who can't share a room under any circumstances)
       
       - all that matters for randomized construction is that the correct names are under "NAMES" in 'names.txt'. Beyond that, you can put 
       whatever you want in the file
       

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
    
    MUST-ROOM
    Bob White, Carol Green, Alice Brown                         # (for some reason or another) this must be a room, and  
                                                                # this pairing is guaranteed by the algorithm before 
                                                                # preferences come into play 
    ```
Participant 'priority' simply offers a measurement by which to sort participants: in our algorithm, we give participants who haven't recently gotten their top requests a higher priority score (during roommate matching, the participant list is sorted in descending order of priority, which gives those with higher priority first dibs on their top choices). Priority can also be used to prevent exceedingly loud or impolite rooms to avoid bothering fellow travelers in hotels (for instance, you can give louder participants a higher integer score and make a check to ensure that no rooms exceeding a certain cumulative score are constructed). In other words, the priority aspect is a customizable feature to your needs. 

2. Open `Terminal`

3. Navigate to project directory `src` (be sure to place `names.txt` in same folder as `.java` files). 

4. run `javac *.java` to compile the project

5. run `java Main args[0] args[1] args[2]` to run the project and enjoy your rooms!

`args[0]`: input file, in our case `names.txt` 
    
 `args[1]`: whether or not you're running preference-based or randomized rooming. This argument should be                "preferences" for preference-based rooms, anything else for randomized.
    
 `args[2]`: the number of roommates desired per room. Should be 3 for preferenced-based, 2/3/4/5 for randomized. 
