package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry> timeEntries = new HashMap<>();
    private long lastCreatedId = 1L;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(lastCreatedId);
        lastCreatedId++;
        timeEntries.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long timeEntryId) {

        return timeEntries.get(timeEntryId);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntries.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(Objects.isNull(find(id))){
            return null;
        }
        timeEntries.replace(id, timeEntry);
        timeEntry.setId(id);
        return timeEntry;
    }

    public void delete(long id) {
        timeEntries.remove(id);
    }
}
