package ac1.ac1lab.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ac1.ac1lab.dto.AttendInsertDTO;

@Entity
@Table(name = "TB_ATTENDEES")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Attend extends BaseUser{

    private Double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "attend", cascade = CascadeType.REMOVE)
    private List<Ticket> tickets = new ArrayList<>();

    public Attend() {
     
	}

    public Attend(AttendInsertDTO dto) {
        this.setName(dto.getName());
        this.setEmail(dto.getEmail());
	}

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void addBalance(Double balance) {
        this.balance += balance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}
