## Motivation

Consider the following situation: you're a counselor on a summer program with 150 participants, sometimes traveling to up to 3 hotels in a single week. As trip staff, your job is to help the participants have a great time: this entails taking care of logistical issues as well as facilitating relationships and friendships among fellow participants. It's also your job to pair participants in roommate groups throughout your travels. To keep participants happy, you allow them to submit preferences: a list of peers they'd like to room with at a given hotel. Let's consider a group of participants: 

```
NAMES
John Doe
Jeff Green
Gordon Fox
```

Now, there may be overlapping requests: for example, `John Doe` may request `Jeff Green`, while `Gordon Fox` requested Jeff as well. There also may be incongruous requests - what if John requests Jeff, but Jeff doesn't feel the same way? Further, direquests are permitted - John and Gordon may have gotten in a fight, and under no circumstances may they be placed in the same room. 

In other words, the input for such a situation is quite complex, and manually constructing rooms is both inefficient and tedious. Often, it's very difficult to make everyone happy. As a collective staff dealing with a plethora of issues on such a trip including event logistics and transportation details, automating this process would save a lot of time and allow you to focus your attention on more pressing and tangible aspects of the trip. This was the original motivation for Room Matching Machine, but it's applicable to any situation where you're trying to group people together based on preference lists. 

The problem, at first glance, seems analagous to the well-known "marriage problem" which, Gale and Shapley solved using their stable matching algorithm. The marriage problem conventionally refers to a situation where you're constructing a bijection from set `A` to set `B` - the most common form of the problem is matching a set of boys to a set of girls where each person has their own ordered preference list. The difference in our problem is that our desired matching is not 1 to 1 at all. Instead, we're trying to create groups. Further, the GS algorithm does not deal with "disrequests" in its input. Finally, our version of the problem does not care for the formal definition of "stable matching" that the GS algorithm guarantees - our goal is to try to construct rooms such that we satisfy as many preferences as possible, but our algorithm doesn't rigorously guarantee that every participant walks away perfectly happy - if there is an issue with the output, we can manually change it. Afer all, this is a complex problem, and I highly doubt the algorithm found in this project is the truly "optimal" solution. I am no Gale, and I am certainly no Shapely. But it does do a solid job. 

## Solution 

The Room Matching Machine does adapt the GS algorithm to finish grouping participants, but first we make checks to clean up our input and construct obvious rooms. For the context in which this project was originally created the input size was known (`n = 150`) and we thus indulge in time-costly operations - for example, we iterate through all combinations of 3 participants and check for chains, where a chain is defined as a group of three participants where each participant requests every other participant. In our case, the following would be a valid "chain":

```
John Doe -> Jeff Green
John Doe -> Gordon Fox 

Jeff Green -> John Doe 
Jeff Green -> Gordon Fox

Gordon Fox -> John Doe 
Gordon Fox -> Jeff Green
```
Overall, we first deal with chains, pairs of two that mutually request one another, and other groups that vibe together - this part of the algorithm can be found in the `constructInitialMatches` method within the `ProcessPreferences` class, and it does the bulk of our work in making participant happy and constructing sensible initial rooms. 

After this initial process, we have a bunch of rooms that may or may not be full to capacity. We also have participants that weren't included in this initial matching and are still looking for a room - they may or may not have preferences. This is where the `Gale Shapley` part of the algorithm comes into play - we go to each participant and have them 'propose' to their top preference - if he/she is still available, we put them together in a room. After this process is done, we have the remaining unmatched participants kindly ask to join each of the remaining rooms until they are finally let in by a room that's not yet at full capacity. There are often very few participants left unmatched at this point in the algorithm, and they are usually the one who submitted zero preferences.

## Project Structure

`NameFileReader.java` 
This class parses the input file and constructs our list of participants accordingly. 

`Participant.java`    
Class containing the definition of what a participant on the program is and has for the purpose of rooming - requests, disrequests, behavioral issues etc. This is foundational to the project.

`Room.java` 
A wrapper for a list of participants. Super helpful throughout the algorithm because we have functions like `stillLooking()` and `willIInviteParticipant()` which make a "list" dynamic and can help us accommodate all participants. 

`ProcessPreferences.java`  
Much of the main work happens in this class. This is where we take in an input list of Participants and really break down the data - we construct obvious matches, check for trios of kids that particularly vibe, and we keep important lists that will help us finish off the rooms. 

`NoPreferences.java` extends `ProcessPreferences.java`   
Randomly constructs rooms in the case where no preferences are allowed but disrequests are still in play. For the original purposes of the project, this was used often when we had short hotel stays for the purpose of faciliating new friendships.

`ConstructRooms.java`
Takes in our list of participants and uses `ProcessPreferences` to obtain optimal matching.

`Main.java`
Driver class for the project. 
