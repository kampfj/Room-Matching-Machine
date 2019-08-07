import java.util.*;

public class Room {
    List<Participant> roommates;
    int capacity;
    int roomNumber;

    public Room(int capacity) {
        this.capacity = capacity;
        roommates = new ArrayList<>();
    }
    
    
    // We just make a bunch of different constructors, because we'll be making rooms
    // in different ways depending on our needs. 
    
    // Alternate constructor for creating a room directly from an array of participants
    public Room(Participant[] participants) {
        this.capacity = participants.length;
        roommates = new ArrayList<>();
        for (Participant p : participants) {
            roommates.add(p);
            p.noLongerAvailable();
        }
    }
    
    public Room(Participant p1, Participant p2, int capacity) {
        roommates = new ArrayList<>();
        roommates.add(p1);
        roommates.add(p2);
        p1.noLongerAvailable();
        p2.noLongerAvailable();
        this.capacity = capacity;
    }
    
    public void fillRoom(Participant[] participants) {
        for (Participant p : participants) {
            roommates.add(p);
            p.noLongerAvailable();
        }
    }
    
    public boolean contains(Participant p) {
        return roommates.contains(p);
    }
    
    public int size() {
        return roommates.size();
    }
    
    public boolean stillLooking() {
        return (size() != capacity);
    }

    
    // Ideally we would want to minimize this score for 
    // any arbitrary room. The "score" of a room is how well behaved
    // they are cumulatively
    public int behaviorScore() {
        int score = 0;
        for (Participant p : roommates) {
            score += p.getPriority();
        }
        return score;
    }
    
    
    // This is a huge function which will help us during our WHILE loop
    // to match up students with rooms 
    public boolean willInviteParticipant(Participant p) {
        if (!p.isAvailable() || notAFit(p)) {
            return false;
        }
        return stillLooking();
        
    }
    
    public boolean notAFit(Participant p) {
        for (Participant kid : roommates) {
            if (p.notAnOption(kid)) {
                return true;
            }
        }
        return false;
    }
    
    public void addRoommate(Participant p) {
        roommates.add(p);
    }
    
      
    
    // For debugging purposes, helps us read rooms clearly. 
    public String toString() {
        String s = "This room is: ";
        for (Participant p : roommates) {
            s += (roommates.indexOf(p) == roommates.size() - 1) ? p.getName() + "." : p.getName() + ", "; 
        }
        return s;
    }

}
