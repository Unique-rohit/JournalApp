package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    private static final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);


    public void saveEntry(JournalEntity journalEntity, String userName){
        try{
            User user=userService.findByUserName(userName);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved=journalEntryRepository.save(journalEntity);
            user.getJournalEntities().add(saved);
            userService.saveUser(user);
        }catch(Exception e){
            logger.error("hahahahahahahahah");
            logger.warn("hahahahahahahahah");
            logger.info("hahahahahahahahah");
            logger.debug("hahahahahahahahah");
            logger.trace("hahahahahahahahah");
            throw new RuntimeException("An error occured while saving the entry.",e);
        }

    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();

    }

    public Optional<JournalEntity> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName){
        boolean removed=false;
       try{
           User user=userService.findByUserName(userName);
           removed=user.getJournalEntities().removeIf(x->x.getId().equals(id));
           if(removed){
               userService.saveUser(user);
               journalEntryRepository.deleteById(id);
           }
       }catch (Exception e){
           throw new RuntimeException("An error occured while deleting the entry.",e);
       }
        return removed;

    }

    public void saveEntry(JournalEntity journalEntity) {
        journalEntryRepository.save(journalEntity);
    }


}
