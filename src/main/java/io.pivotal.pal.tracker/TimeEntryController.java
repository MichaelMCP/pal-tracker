package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            MeterRegistry meterRegistry
    ) {
        this.timeEntryRepository = timeEntriesRepo;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        return new ResponseEntity(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if (Objects.isNull(timeEntry)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(timeEntry, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry expected) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id, expected);
        if (!Objects.isNull(updatedTimeEntry)) {
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        timeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
