package ac1.ac1lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ac1.ac1lab.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository <Ticket,Long>{

}
