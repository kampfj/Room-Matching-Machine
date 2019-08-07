import java.util.List;
import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        
        NameFileReader reader = new NameFileReader(args[0]);
        List<Participant> participants = reader.getParticipants();
        // This is the number of rooms that will be constructed. Feel free to 
        // change this, it's currently set to a number assuming each participnat is 
        // placed in a room. 
        int roomCapacity = Integer.parseInt(args[2]);
        int numRooms = participants.size() / Integer.parseInt(args[2]);
        // If we don't have an equal number of participants in each room, 
        // we'll need to have an extra room to accomodate the remainder. 
        if (participants.size() % roomCapacity != 0) {
            numRooms++;
        }
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

   

}

