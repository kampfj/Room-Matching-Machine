import java.util.*;

public class ConstructRooms {
    // This will be our solution set, i.e. our final list of rooms
    // We will need to have a while-loop in our constructor that makes sure 
    // the rooms get filled
    // If we still have people in pref.noRequests, we will fill the remaining rooms with them
    List<Room> res;
    ProcessPreferences pref;
    int maxNumberOfRooms;
    
    
    public ConstructRooms(List<Room> firstRooms, List<Participant> participants, int maxNumberOfRooms) {
        res = new ArrayList<>();
        res.addAll(firstRooms);
        pref = new ProcessPreferences(firstRooms, participants, maxNumberOfRooms);
        organizeList(pref);
        initialMatches(pref);
        pref.satisfyMutualRequests(res);
        pref.accommodateRemainingRequests(pref.haveRequests, res);
    }
    
    public void organizeList(ProcessPreferences pref) {
        pref.divideIntoGroups();
    }
    
    public void initialMatches(ProcessPreferences pref) {
        res.addAll(pref.constructClearMatches());
    }
    
    

}
