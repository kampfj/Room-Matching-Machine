## Motivation

Consider the following situation: you're a counselor on a summer program with 150 participants, sometimes traveling to up to 3 hotels in a single week. As a staff, your job is to help the participants have a great time: this entails taking care of logistical issues as well as facilitating relationships and friendships among fellow participants. It's also your job to pair participants in roommate groups throughout your travels. To keep participants happy, you allow them to submit preferences: a list of peers they'd like to room with at a given hotel. Let's consider a group of participants: 

`
John Doe
Jeff Green
Gordon Fox

'

Now, there may be overlapping requests: for example, `John Doe` may request `Jeff Green`, while `Gordon Fox` requested Jeff as well. There also may be incongruous requests - what if John requests Jeff, but Jeff doesn't feel the same way? Further, direquests are permitted - John and Gordon may have gotten in a fight, and under no circumstances may they be placed in the same room. 

In other words, the input for such a situation is quite complex, and manually constructing rooms is both inefficient and tedious. Often, it's very difficult to make everyone happy. As a collective staff dealing with a plethora of issues on such a trip including event logistics and transportation details, automating this process would save a lot of time and allow you to focus your attention on more pressing and tangible aspects of the trip. This was the original motivation for Room Matching Machine, but it's applicable to any situation where you're trying to group people together based on preference lists. 

The problem, at first glance, seems analagous to Gale and Shapley's famed "marriage problem" which is solved using their stable matching algorithm. "Stable matching" conventionally refers to a situation where you're mapping a bijection from set `A` to set `B` - the most common form of the problem is matching a set of boys to a set of girls where each person has their own ordered preference list. The difference in our problem is that our matching is not 1 to 1. Instead, we're trying to create groups. Further, the GS algorithm does not deal with "disrequests" in its input. Finally, our version of the problem does not care for the formal definition of "stable matching" that the GS algorithm guarantees - our goal is to try to construct rooms such that we satisfy as many preferences as possible, but our algorithm doesn't rigorously guarantee that every participant walks away perfectly happy. 

## Solution 



## Project Structure
