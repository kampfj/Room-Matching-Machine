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

    private enum ReadingMode {
        NAMES, DISREQUESTS, REQUESTS
    }

    public NameFileReader(String filename) {
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
