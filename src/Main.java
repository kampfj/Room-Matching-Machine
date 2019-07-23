import java.util.List;
import java.util.*;

public class Main {
    
    public static void main(String[] args) {

        List<Participant> participants = new NameFileReader(args[0]).getParticipants();
        // This is the number of rooms that will be constructed. Feel free to 
        // change this, it's currently set to a number assuming each participnat is 
        // placed in a room. 
 
        int numRooms = participants.size() / Integer.parseInt(args[2]);
        List<Participant> mysteryMan = new ArrayList<>(participants);
        if (args[1].equals("preferences")) {
            ConstructRooms finalRooms = new ConstructRooms(participants, 
                                    numRooms);
            for (Room room : finalRooms.res) {
                System.out.println(room);
                for (Participant p : room.roommates) {
                    mysteryMan.remove(p);
                }
            }
            System.out.println(mysteryMan.get(0));
            
        } else {
            NoPreferences pref = new NoPreferences(participants, numRooms, 
                                            Integer.parseInt(args[2]));
            for (Room room : pref.getRooms()) {
                System.out.println(room);
            }
        }
      
        
      
        
        
    }

    private static void print(List<Participant> participants) {
        for (Participant p : participants) {
            System.out.print(p + ": ");
            for (Participant p1 : p.getPreferences()) {
                System.out.print("+" + p1 + " ");
            }
            for (Participant p1 : p.getDisrequests()) {
                System.out.print("-" + p1 + " ");
            }
            System.out.println();
        }
    }

}

