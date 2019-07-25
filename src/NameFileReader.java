// Shouts to Martin Rubin for key and invaluable guidance throughout the IO portions of this project!

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class NameFileReader {
    
    private String filename;
    private Map<String, Participant> participants;
    private ReadingMode readingMode;
    // This will store all of the must-rooms that we have from the get-go
    List<Room> firstRooms;

    private enum ReadingMode {
        NAMES, DISREQUESTS, REQUESTS, MUST_ROOM, PRIORITY;
    }

    public NameFileReader(String filename) {
        firstRooms = new ArrayList<>();
        this.filename = filename;
        this.participants = new HashMap<String, Participant>();
        this.read();
    }


    private void read() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                line = line.trim();

                if (line.equals("")) {continue;}

                if (line.equals("NAMES")) {readingMode = ReadingMode.NAMES;}
                else if (line.equals("DISREQUESTS")) {readingMode = ReadingMode.DISREQUESTS;}
                else if (line.equals("REQUESTS")) {readingMode = ReadingMode.REQUESTS;}
                else if (line.equals("PRIORITY")) {readingMode = ReadingMode.PRIORITY;}
                else if (line.equals("MUST")) {readingMode = ReadingMode.MUST_ROOM;}
                else {
                    if (readingMode == null) {
                        throw new IOException("Cannot understand line: '" + line + "'");
                    }

                    switch (readingMode) {
                    case NAMES:
                        if (participants.containsKey(line)) {
                            throw new IOException("Duplicate name found: '" + line + "'");
                        }
                        participants.put(line, new Participant(line));
                        break;
                    case DISREQUESTS:
                    case REQUESTS:
                        String[] parts = line.split("->");
                        if (parts.length != 2) {
                            throw new IOException("Illegal (dis)request: '" + line + "'");
                        }

                        Participant p0 = participants.get(parts[0].trim());
                        Participant p1 = participants.get(parts[1].trim());

                        if (p0 == null || p1 == null) {
                            throw new IOException("Name not found in (dis)request: '" + line + "'");
                        }

                        if (readingMode == ReadingMode.DISREQUESTS) {
                            p0.addDisrequest(p1);
                        }
                        if (readingMode == ReadingMode.REQUESTS) {
                            p0.addPreference(p1);
                        }

                        break;
                   case PRIORITY:
                        String[] priority = line.split("->");
                        Participant p = participants.get(priority[0].trim());
                        if (p == null) {
                            throw new IOException("Unknown name encountered in priority");
                        }
                        p.setPriority(Integer.parseInt(priority[1].trim()));
                        break;
                    case MUST_ROOM: 
                        String[] mates = line.split(",");
                        Participant first = participants.get(mates[0].trim()), second = participants.get(mates[1].trim()), 
                                                                        third = participants.get(mates[2].trim());
                        firstRooms.add(new Room(new Participant[] {first, second, third}));
                    }
                        
                        
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Participant> getParticipants() {
        return new ArrayList<Participant>(participants.values());
    }

}
