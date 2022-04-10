package ac1.ac1lab.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ac1.ac1lab.dto.AttendTicketDTO;
import ac1.ac1lab.entities.Attend;
import ac1.ac1lab.entities.Event;
import ac1.ac1lab.entities.Ticket;
import ac1.ac1lab.entities.View;
import ac1.ac1lab.repositories.AttendRepository;
import ac1.ac1lab.repositories.EventRepository;
import ac1.ac1lab.repositories.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private TicketRepository ticketRepository;
    
    public View getTickets(Long id){
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!"));

        View view = new View();

        view.setTotalFreeTickets(eve.getAmountFreeTickets());
        view.setTotalPayedTickets(eve.getAmountPayedTickets());

        for(Ticket ticket : eve.getTickets()){        
            if(ticket.getType().equals("Free")){
                view.setSoldFreeTickets(view.getSoldFreeTickets() + 1);
                view.addListFreeTickets(ticket.getAttend().getName());
            }
            else{
                view.setSoldPayedTickets(view.getSoldPayedTickets() + 1);
                view.addListPayedTickets(ticket.getAttend().getName());
            }
        }

        return view;
    }

    public Attend insertTicket(AttendTicketDTO dto, Long id){
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!"));

        Optional<Attend> op2 = attendRepository.findById(dto.getId());
        Attend atd = op2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found!"));

        if(!dto.getType().equals("Free") && !dto.getType().equals("Payed")){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "In Type use: Free or Payed!");
        }

        if(eve.hasTicket(dto.getType()) != true){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This event does not have enough ticket! -> " + dto.getType());
        }

        Ticket ticket = new Ticket();
        ticket.setType(dto.getType());
        ticket.setAttend(atd);
        ticket.setEvent(eve);
        ticket.setPrice(eve.getPriceTicket());
        ticket.setDate(Instant.now());
        ticket = ticketRepository.save(ticket);

        return atd;
    }

    public void deleteTickets(Long id){
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!"));

        LocalDateTime start = LocalDateTime.of(eve.getStartDate(), eve.getStartTime());
        LocalDateTime end = LocalDateTime.of(eve.getEndDate(), eve.getEndTime());

        if(LocalDateTime.now().isAfter(start) && LocalDateTime.now().isBefore(end)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This event is happening!");
        }

        for(Ticket ticket : eve.getTickets()){
            if(ticket.getType().equals("Payed")){
                ticket.getAttend().addBalance(ticket.getPrice());
                attendRepository.save(ticket.getAttend());
            }

            ticketRepository.deleteById(ticket.getId());
        }
    }
}
