import java.util.*;
import java.util.List;
import java.util.Set;

public class Participant {
    private String name;
    
    // Each participant given priority score based on 
    // behavior and history of quiet and respect throughout travels. A lower score
    // is a better score, i.e. a "higher" priority
    private int priority;
    
    private boolean isAvailable;
    
    // We don't want the same people rooming together too many times! 
    private Set<Participant> roomed;
    
    // Stores the participants with which this person CANNOT be matched with 
    private Set<Participant> disrequested;
    
    // Who do you wanna room with tho? 
    private List<Participant> preferences;
    
    
    public Participant(String name) {
        this.name = name;
        isAvailable = true;
        disrequested = new HashSet<>();
        preferences = new ArrayList<>();
        roomed = new HashSet<>();
    }
    
    public String getName() {
        return name;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void noLongerAvailable() {
        isAvailable = false;
    }
    
    
    // Returns TRUE if the participants have previously been in a room together
    public boolean roomedWith(Participant p) {
        return roomed.contains(p);
    }
    
    
    // Returns TRUE if the given pair cannot room with each other. 
    // In other words, if either person has the other on their disrequested list
    public boolean notAnOption(Participant p) {
        if (disrequested.size() == 0 && p.disrequested.size() == 0) {
            return false;
            
        } else if (disrequested.size() == 0 && p.disrequested.contains(this)) {
            return true;
            
        } else if (p.disrequested.size() == 0 && disrequested.contains(p)) {
            return true;
        }
          else {
            return (disrequested.contains(p) || p.disrequested.contains(this));
        }
        
    }
    
    public List<Participant> getPreferences() {
        return preferences;
    }
    
    public Set<Participant> getDisrequests() {
        return disrequested;
    }
    
    
    // One night of politeness and quiet in the rooms can land you 
    // a much better chance of getting your request! 
    public void setPriority(int newPriority) {
        this.priority = newPriority;
    }
    
    public void setPreferences(List<Participant> preferences) {
        this.preferences = preferences;
    }
    
    public void addPreference(Participant p) {
        preferences.add(p);
    }
    
    public void removePreference(Participant p) {
        preferences.remove(p);
    }
    
    public void setDisrequests(Set<Participant> disrequested) {
        this.disrequested = disrequested;
    }
    
    public void addDisrequest(Participant p) {
        disrequested.add(p);
    }
    
    public void removeDisrequest(Participant p) {
        disrequested.remove(p);
    }
    
    public String toString() {
        return name;
    }
 
}
                            