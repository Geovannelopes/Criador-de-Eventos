package ac1.ac1lab.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ac1.ac1lab.dto.EventDTO;
import ac1.ac1lab.dto.EventInsertDTO;
import ac1.ac1lab.dto.EventUpdateDTO;
import ac1.ac1lab.entities.Admin;
import ac1.ac1lab.entities.Event;
import ac1.ac1lab.repositories.AdminRepository;
import ac1.ac1lab.repositories.EventRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description) {
        Page<Event> list = eventRepository.find(pageRequest, name, description);
        return list.map( e -> new EventDTO(e) );
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!"));

        return new EventDTO(eve);
    }

    public EventDTO insert(EventInsertDTO dto){
        LocalDateTime start = LocalDateTime.of(dto.getStartDate(), dto.getStartTime());
        LocalDateTime end = LocalDateTime.of(dto.getEndDate(), dto.getEndTime());

        if(dto.getName().isEmpty() || dto.getDescription().isEmpty() || dto.getEmailContact().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
        }

        if(dto.getAmountFreeTickets() < 0 || dto.getAmountPayedTickets() < 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The number of Tickets must be greater than or equal to 0!");
        }

        if(dto.getPriceTicket() < 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets price must be greater than or equal to 0!");
        }

        if(start.isAfter(end)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The end date must be greater than the start date!");
        }

        if(start.isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The date must be greater than the current date!");
        }

        Optional<Admin> op = adminRepository.findById(dto.getAdmin());
        op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found!"));

        Event entity = new Event(dto);
        entity = eventRepository.save(entity);
        return new EventDTO(entity);
    }

    public void delete(Long id){
        try{
            Optional<Event> op = eventRepository.findById(id);
            Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!"));

            if(eve.getTickets().size() > 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This event has tickets sold!");
            }

            eventRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!");
        }
    }

    public EventDTO update(Long id, EventUpdateDTO dto){
        try{
            LocalDateTime start = LocalDateTime.of(dto.getStartDate(), dto.getStartTime());
            LocalDateTime end = LocalDateTime.of(dto.getEndDate(), dto.getEndTime());

            if(dto.getDescription().isEmpty() || dto.getEmailContact().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
            }
    
            if(dto.getAmountFreeTickets() < 0 || dto.getAmountPayedTickets() < 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The number of Tickets must be greater than or equal to 0!");
            }
    
            if(dto.getPriceTicket() < 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets price must be greater than or equal to 0!");
            }
    
            if(dto.getStartDate().isAfter(dto.getEndDate())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The end date must be greater than the start date!");
            }

            if(start.isAfter(end)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The end date must be greater than the start date!");
            }
    
            if(start.isBefore(LocalDateTime.now())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The date must be greater than the current date!");
            }

            Event entity = eventRepository.getOne(id);

            LocalDateTime start_event = LocalDateTime.of(entity.getStartDate(), entity.getStartTime());
            LocalDateTime end_event = LocalDateTime.of(entity.getEndDate(), entity.getEndTime());

            if(LocalDateTime.now().isAfter(start_event) && LocalDateTime.now().isBefore(end_event)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This event is happening!");
            }

            entity.setDescription(dto.getDescription());
            entity.setStartDate(dto.getStartDate());
            entity.setEndDate(dto.getEndDate());
            entity.setStartTime(dto.getStartTime());
            entity.setEndTime(dto.getEndTime());
            entity.setEmailContact(dto.getEmailContact());
            entity.setAmountFreeTickets(dto.getAmountFreeTickets());
            entity.setAmountPayedTickets(dto.getAmountPayedTickets());
            entity.setPriceTicket(dto.getPriceTicket());
            entity = eventRepository.save(entity);
            return new EventDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!");
        }  
    }
}
