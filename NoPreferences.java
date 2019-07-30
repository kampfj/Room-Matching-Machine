import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


// We want access to the fields and methods of ProcessPreferences, for this reason we extend
// that class. 

public class NoPreferences extends ProcessPreferences {
    private List<Room> rooms;
    int roommates;
    
    // No preference algorithm: (WHILE there are participants left)
    //                          1) randomly index into your list of participants to get three
    //                          2) if they don't hate each other and aren't gonna make a ton of noise, make them a room. 

    public NoPreferences(List<Participant> participants, int maxNumberOfRooms, int roommates) {
        super(new ArrayList<>(), participants, maxNumberOfRooms);
        rooms = new ArrayList<>();
        if (roommates == 2) {
            while (!participants.isEmpty()) {
                if (participants.size() < 3) {
                    Room last = new Room(new Participant[] {participants.get(0)});
                    rooms.add(last);
                    if (participants.size() == 2) last.addRoommate(participants.get(1));
                    break;
                } 
                Integer[] indices = twoInRange();
                Participant p = participants.get(indices[0]);
                Participant p1 = participants.get(indices[1]);
                Participant[] group = new Participant[] {p, p1};
                if (!groupContainsDisrequest(group)) {
                    rooms.add(new Room(group));
                    participants.remove(p);
                    participants.remove(p1);
                }
            }
        }
        if (roommates == 3) {
            while (!participants.isEmpty()) {
                if (participants.size() < 4) {
                    Room last = new Room(new Participant[] {participants.get(0)});
                    participants.remove(participants.get(0));
                    while (!participants.isEmpty()) {
                       last.addRoommate(participants.get(0));
                       participants.remove(0);
                    }
                    rooms.add(last); 
                    break;
                    
                }
                Integer[] indices = threeInRange();
                Participant p = participants.get(indices[0]);
                Participant p1 = participants.get(indices[1]);
                Participant p2 = participants.get(indices[2]);
                Participant[] group = new Participant[] {p, p1, p2};
                if (!groupContainsDisrequest(group)) {
                    rooms.add(new Room(group));
                    participants.remove(p);
                    participants.remove(p1);
                    participants.remove(p2);
                }
            }
        } else  {
            while (!participants.isEmpty()) {
                Integer[] indices = fourInRange();
                Participant p = participants.get(indices[0]);
                Participant p1 = participants.get(indices[1]);
                Participant p2 = participants.get(indices[2]);
                Participant p3 = participants.get(indices[3]);
                Participant[] group = new Participant[] {p, p1, p2, p3};
                if (!groupContainsDisrequest(group)) {
                    rooms.add(new Room(group));
                    participants.remove(p);
                    participants.remove(p1);
                    participants.remove(p2);
                    participants.remove(p3);
                } 
            }
        }
    }
    
    public Integer[] twoInRange() {
        int r1 = ThreadLocalRandom.current().nextInt(0, participants.size());
        int r2 = ThreadLocalRandom.current().nextInt(0, participants.size());
        
        while (r1 == r2) {
            r1 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        
        return new Integer[] {r1, r2};
    }
    
    
    // We generate three random number within the range [0...participants.size - 1] in order 
    // to choose three random participants for our potential next room. The key is that we need
    // three DISTINCT integers, which explains the three while loops - in other words, we might land
    // with two numbers that are the same after our first random selection (we don't want that). Thus,
    // we'll just keep randomly selecting until our integers are completely distinct. Pretty simple.
    public Integer[] threeInRange() {
        int r1 = ThreadLocalRandom.current().nextInt(0, participants.size());
        int r2 = ThreadLocalRandom.current().nextInt(0, participants.size());
        int r3 = ThreadLocalRandom.current().nextInt(0, participants.size());
        while (r1 == r2 || r1 == r3) {
            r1 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        while (r2 == r1 || r2 == r3) {
            r2 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        while (r3 == r1 || r3 == r2) {
            r3 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        
        return new Integer[] {r1, r2, r3};
    }
    
    public Integer[] fourInRange() {
        int r1 = ThreadLocalRandom.current().nextInt(0, participants.size());
        int r2 = ThreadLocalRandom.current().nextInt(0, participants.size());
        int r3 = ThreadLocalRandom.current().nextInt(0, participants.size());
        int r4 = ThreadLocalRandom.current().nextInt(0, participants.size());
        while (r1 == r2 || r1 == r3 || r1 == r4) {
            r1 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        while (r2 == r1 || r2 == r3 || r2 == r4) {
            r2 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        while (r3 == r1 || r3 == r2 || r3 == r4) {
            r3 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        while (r4 == r1 || r4 == r2 || r4 == r3) {
            r4 = ThreadLocalRandom.current().nextInt(0, participants.size());
        }
        
        return new Integer[] {r1, r2, r3, r4};
    }
    
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    

}
