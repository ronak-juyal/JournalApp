package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    public List<JournalEntry> getAllJournalEntryOfUser(){
        User user = userService.getUser(getUserName());
        if(user!=null) return user.getJournalEntries();
        return null;
    }
    @Transactional
    public JournalEntry createJournalEntryOfUser(JournalEntry myEntry){
        User user = userService.getUser(getUserName());
        if(user==null) return null;
        myEntry.setDate(LocalDateTime.now());
        JournalEntry save = journalEntryRepository.save(myEntry);
        user.getJournalEntries().add(save);
        userService.saveUser(user);
        return save;
    }

    public JournalEntry getById(ObjectId id) {
        User user = userService.getUser(getUserName());
        List<JournalEntry> entry = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(id))
                .collect(Collectors.toList());
        if(!entry.isEmpty()){
            return entry.get(0);
        }
        return null;
    }
    @Transactional
    public boolean deleteJournalById(ObjectId myId) {
        User user =userService.getUser(getUserName());
        if(user==null) return false;
        if(!user.getJournalEntries().removeIf(x->x.getId().equals(myId))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Journal entry not found");
        }
        userService.saveUser(user);
        journalEntryRepository.deleteById(myId);
        return true;
    }
    public JournalEntry updateJournalById(ObjectId id, JournalEntry journalEntry) {
        User user=userService.getUser(getUserName());
        JournalEntry journal = user.getJournalEntries().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Journal entry not found"
                ));
        journal.setTitle(journalEntry.getTitle());
        journal.setContent(journalEntry.getContent());
        journal.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journal);
    }

    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


}
