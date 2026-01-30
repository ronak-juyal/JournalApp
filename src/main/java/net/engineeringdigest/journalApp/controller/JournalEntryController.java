package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {

    JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser(){
        List<JournalEntry> all = journalEntryService.getAllJournalEntryOfUser();
        if(all!=null && !all.isEmpty()) return  new ResponseEntity<>(all,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createJournalEntryOfUser(@RequestBody JournalEntry myEntry){
        JournalEntry journalEntryOfUser = journalEntryService.createJournalEntryOfUser(myEntry);
        if(journalEntryOfUser!=null) return new ResponseEntity<>(journalEntryOfUser,HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public  ResponseEntity<?> getById(@PathVariable ObjectId id){
        JournalEntry entry = journalEntryService.getById(id);
        if(entry!=null) return new ResponseEntity<>(entry,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        boolean userExist = journalEntryService.deleteJournalById(myId);
        if(userExist) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
    }
    @PutMapping("id/{id}")
    public  ResponseEntity<?> updateJournalEntry(@RequestBody JournalEntry journalEntry, @PathVariable ObjectId id){
        JournalEntry entry = journalEntryService.updateJournalById(id,journalEntry);
        if(entry!=null) return new ResponseEntity<>(entry,HttpStatus.OK);
        return new ResponseEntity<>("user",HttpStatus.NOT_FOUND);
    }
}
