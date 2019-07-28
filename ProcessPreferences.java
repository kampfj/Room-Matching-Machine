import java.util.ArrayList;
import java.util.List;



public class ProcessPreferences {
    
    // We aid our process by separating the participants with requests from those with none. 
    // This makes it much simpler to accommodate those with requests while adding in the 
    // indifferent after the picky are satisfied. 
    List<Participant> participants;
    List<Participant> noRequests;
    List<Participant> haveRequests;
    
    // We maintain this list of participant "still available" throughout the algorithm and 
    // update as needed. 
    List<Participant> stillAvailable;
    
    // We always want to fill a certain number of rooms
    int maxNumberOfRooms;
    
    // A given room's cumulative "behavior score" cannot exceed this. Otherwise 
    // the guests will be upset!
    static int maxBehavior;
    
    public ProcessPreferences(List<Room> firstRooms, List<Participant> participants, int maxNumberOfRooms) {
        this.participants = participants;
        stillAvailable = new ArrayList<>(participants);
        for (Room room : firstRooms) {
            for (Participant p : room.roommates) {
                stillAvailable.remove(p);
            }
        }
        noRequests = new ArrayList<>();
        haveRequests = new ArrayList<>();
        maxBehavior = 11;
        this.maxNumberOfRooms = maxNumberOfRooms;
    }
    
    public void divideIntoGroups() {
        for (Participant p : participants) {
            if (p.getPreferences().size() == 0) {
                noRequests.add(p);
            } else {
                haveRequests.add(p);
            }
        }
    }
    
    
    // Getting all two-combinations of participants whoHaveRequests, to be used later
    // time complexity O(n^2)
    public List<Participant[]> getDuos() {
        List<Participant[]> duos = new ArrayList<>();
        for (int i = 0; i < haveRequests.size() - 1; i++) {
            for (int j = i + 1; j < haveRequests.size(); j++) {
                duos.add(new Participant[] {haveRequests.get(i), haveRequests.get(j)});
            }
        }
        return duos;
    }
    
    // Getting all three combinations of participants who HaveRequests, to be used later 
    public List<Participant[]> getTrios() {
        List<Participant[]> trios = new ArrayList<>();
        for (int i = 0; i < haveRequests.size() - 2; i++) {
            for (int j = i + 1; j < haveRequests.size() - 1; j++) {
                for (int k = j + 1; k < haveRequests.size(); k++) {
                    Participant first = haveRequests.get(i);
                    Participant second = haveRequests.get(j);
                    Participant third = haveRequests.get(k);
                    trios.add(new Participant[] {first, second, third});
                }
            }
        }
        
        return trios;
    }
    
    
    // Calculates cumulative "behavior score" of a group of kids
    public int groupBehaviorScore(Participant[] group) {
        int behaviorSum = 0;
        for (Participant p : group) {
            behaviorSum += p.getPriority();
        }
        return behaviorSum;
    }
    
    
    // Returns true if the participants in input array are a good fit for a room.
    // I'll only call this function with groups of 2 or 3.
    public boolean groupVibes(Participant[] group) {
        if (groupBehaviorScore(group) > maxBehavior) {
            return false;
        }
        if (groupContainsDisrequest(group) || hasUnavailable(group)) {
            return false;
        }
        if (group.length == 2) {
            Participant p1 = group[0], p2 = group[1];
            if (p1.getPreferences().contains(p2) && p2.getPreferences().contains(p1)) {
                return true;
            }
        } else if (group.length == 3){
            Participant p1 = group[0], p2 = group[1], p3 = group[2];
            boolean one = p1.getPreferences().contains(p2) && p2.getPreferences().contains(p3);
            boolean two = p1.getPreferences().contains(p3) && p2.getPreferences().contains(p1);
            boolean three = p2.getPreferences().contains(p3) && p3.getPreferences().contains(p1);
            boolean four = p1.getPreferences().contains(p2) && p3.getPreferences().contains(p2);
            boolean five = p2.getPreferences().contains(p1) && p3.getPreferences().contains(p1);
            boolean six = p1.getPreferences().contains(p3) && p3.getPreferences().contains(p2);
            return one || two || three || four || five || six;
        }
        
        return false;
        
    }
    
 
    
    // If any person in the group disrequested any other person in the group, 
    // this group CANNOT be together. We can maket this check by brute force
    // because we have a cap on our input size -> we'll never have a group of over 
    // 4 kids, so this is really basically an O(1) asymptotic operation. 
    public boolean groupContainsDisrequest(Participant[] group) {
        if (group.length == 2) {
            return (group[0].getDisrequests().contains(group[1]) || 
                        group[1].getDisrequests().contains(group[0]));   
            
        } else if (group.length == 3) {
            boolean firstMad = group[0].getDisrequests().contains(group[1]) || 
                            group[0].getDisrequests().contains(group[2]);
            boolean secondMad = group[1].getDisrequests().contains(group[0]) || 
                            group[1].getDisrequests().contains(group[2]);
            boolean thirdMad = group[2].getDisrequests().contains(group[0]) || 
                            group[2].getDisrequests().contains(group[1]);
            
            return firstMad || secondMad || thirdMad;
        }
        
        return false;
    }
    
    
    // If anyone in the input array is ALREADY taken, return TRUE i.e. this group cannot room
    // together. 
    public boolean hasUnavailable(Participant[] group) {
        for (Participant p : group) {
            if (!p.isAvailable()) {
                return true;
            }
        }
        return false;
    }
    
    
    // Here we make our initial check, going through all 3-combinations
    // that vibe together and behave well. If we encounter such a match, we add it to our 
    // list of rooms and we then update our data structures to reflect the fact that we 
    // no longer have to deal with these guys 
    public List<Room> constructClearMatches() {
        List<Participant[]> trios = getTrios();
        List<Room> initialRooms = new ArrayList<>();
        for (Participant[] group : trios) {
            
            if (groupVibes(group)) {
                
                // Make a new room
                Room roomVibes = new Room(group);
                initialRooms.add(roomVibes);
                
                for (Participant p : group) {
                    
                    // Remove participant from our list and remove him 
                    // from the preference lists of others
                    
                    // We have to remove them after the loop so as to avoid
                    // a ConcurrentModificationException, so we store 
                    // temporarily in this list toRemove
                    List<Participant> toRemove = new ArrayList<>();
                    
                    for (Participant kid : haveRequests) {
                        if (kid.getPreferences().contains(p)) {
                            kid.removePreference(p);
                            if (kid.getPreferences().size() == 0) {
                                toRemove.add(kid);
                                noRequests.add(kid);
                            }
                        }
                    }
                    
                    for (Participant remove : toRemove) {
                        haveRequests.remove(remove);
                    }
                    
                    p.noLongerAvailable();
                    stillAvailable.remove(p);
                    haveRequests.remove(p);
                }
            }
        }
        
        return initialRooms;
    }
    
    
    // We construct rooms with mutual requestors to allow those people to be in the 
    // same room. This again cleans up our list and makes our final (While) loop a
    // little easier. 
    public void satisfyMutualRequests(List<Room> rooms) {
        List<Participant[]> duos = getDuos();
        
        for (Participant[] duo : duos) {
            
            if (feelingMutual(duo[0], duo[1])) {
                
                if (duo[0].isAvailable() && duo[1].isAvailable()) {
                    
                    rooms.add(new Room(duo[0], duo[1], 3));
                    
                    // Modify data structures to reflect the fact that these guys are
                    // no longer available 
                    haveRequests.remove(duo[0]); haveRequests.remove(duo[1]);
                    for (Participant kid : participants) {
                        if (kid.getPreferences().contains(duo[0])) {
                            kid.removePreference(duo[0]);
                        }
                        if (kid.getPreferences().contains(duo[1])) {
                            kid.removePreference(duo[1]);
                        }
                    }
                    duo[0].noLongerAvailable(); duo[1].noLongerAvailable();
                    stillAvailable.remove(duo[0]); stillAvailable.remove(duo[1]);
                    if (rooms.size() == maxNumberOfRooms) {
                        break;
                    }
                }
            }
        }
    }
    
    public boolean feelingMutual(Participant p1, Participant p2) {
        return p1.getPreferences().contains(p2) && p2.getPreferences().contains(p1);
    }
    
    public void accommodateRemainingRequests(List<Participant> remaining, List<Room> rooms) {
        remaining.sort((p1, p2) -> p2.getPriority() - p1.getPriority());
        int count = remaining.size();
            for (Participant p : remaining) {
                if (p.isAvailable()) {
                    if (p.getPreferences().size() > 0) {
                        Participant topChoice = p.getPreferences().get(0);
                        if (topChoice.isAvailable()) {
                            Room newRoom = new Room(p, topChoice, 3);
                            rooms.add(newRoom);
                            stillAvailable.remove(p);
                            stillAvailable.remove(topChoice);
                            if (p.getPreferences().size() > 1 && newRoom.willInviteParticipant(p.getPreferences().get(1))) {
                                Participant secondChoice = p.getPreferences().get(1);
                                newRoom.addRoommate(secondChoice);
                                secondChoice.noLongerAvailable();
                                stillAvailable.remove(secondChoice);
                                if (remaining.contains(secondChoice)) count--;
                                if (noRequests.contains(secondChoice)) noRequests.remove(secondChoice);
                                p.getPreferences().remove(secondChoice);
                            }
                            
                            p.noLongerAvailable();
                            topChoice.noLongerAvailable();
                            p.getPreferences().remove(topChoice);
       
                            count--; 
                            if (remaining.contains(topChoice)) count--;
                            if (noRequests.contains(topChoice)) noRequests.remove(topChoice);
                        
                            // If we've done all we can do in this loop
                            if (rooms.size() == maxNumberOfRooms || count <= 0) {
                                break;
                            }
                        } else if (p.getPreferences().size() > 1 && p.getPreferences().get(1).isAvailable()) {
                            Participant secondChoice = p.getPreferences().get(1);
                            Room newRoom = new Room(p, secondChoice, 3);
                            rooms.add(newRoom);
                            secondChoice.noLongerAvailable();
                            stillAvailable.remove(p);
                            stillAvailable.remove(secondChoice);
                            if (remaining.contains(secondChoice)) count--;
                            if (noRequests.contains(secondChoice)) noRequests.remove(secondChoice);
                            p.getPreferences().remove(secondChoice);
                        }
                    }
                }
            }
       
            
        // There are still some rooms with only 2 people, or there may not be enough 
        // rooms to fit everyone. So we construct all the empty rooms we need in this 
        // loop. 
        for (int i = rooms.size(); i < maxNumberOfRooms; i++) {
            rooms.add(new Room(3));
        }
        
        
        // Now we fill the rest of the rooms to make sure we end up with a complete set. 
        stillAvailable.sort((p1, p2) -> p2.getDisrequests().size() - p1.getDisrequests().size());
        for (Participant participant : stillAvailable) {
            for (Room room : rooms) {
                if (room.willInviteParticipant(participant)) {
                    room.addRoommate(participant);
                    break;
                }
            }
        }
    }
  
    

}

