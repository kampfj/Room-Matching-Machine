import java.util.List;
import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        
        NameFileReader reader = new NameFileReader(args[0]);
        List<Participant> participants = reader.getParticipants();
        // This is the number of rooms that will be constructed. Feel free to 
        // change this, it's currently set to a number assuming each participnat is 
        // placed in a room. 
 
        int numRooms = participants.size() / Integer.parseInt(args[2]);
        System.out.println(participants.size());
        System.out.println(numRooms);
        if (args[1].equals("preferences")) {
            ConstructRooms finalRooms = new ConstructRooms(reader.firstRooms, 
                                participants, numRooms);
            for (Room room : finalRooms.res) {
                System.out.println(room);
            }
            
            
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

