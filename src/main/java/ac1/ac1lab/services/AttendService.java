package ac1.ac1lab.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ac1.ac1lab.dto.AttendDTO;
import ac1.ac1lab.dto.AttendInsertDTO;
import ac1.ac1lab.dto.AttendUpdateDTO;
import ac1.ac1lab.entities.Admin;
import ac1.ac1lab.entities.Attend;
import ac1.ac1lab.repositories.AdminRepository;
import ac1.ac1lab.repositories.AttendRepository;

@Service
public class AttendService {
    
    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Page<AttendDTO> getAttendees(PageRequest pageRequest, String name) {
        Page<Attend>list = attendRepository.find(pageRequest, name);
        return list.map( at -> new AttendDTO(at) );
    }

    public AttendDTO getAttendById(Long id) {
        Optional<Attend> op = attendRepository.findById(id);
        Attend atd = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found"));

        return new AttendDTO(atd);
    }

    public AttendDTO insert(AttendInsertDTO dto){

        if(dto.getName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
        }

        List<Admin> admins = adminRepository.findAll();
        for(Admin admin : admins){
            if(admin.getEmail().equals(dto.getEmail())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This email is already being used!");
            }
        }

        List<Attend> attends = attendRepository.findAll();
        for(Attend attend : attends){
            if(attend.getEmail().equals(dto.getEmail())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This email is already being used!");
            }
        }

        Attend entity = new Attend(dto);
        entity.setBalance(0.0);
        entity = attendRepository.save(entity);
        return new AttendDTO(entity);
    }

    public void delete(Long id){
        try{
            attendRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found");
        }
    }

    public AttendDTO update(Long id, AttendUpdateDTO dto){

        try{
            if(dto.getName().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
            }

            Attend entity = attendRepository.getOne(id);
            entity.setName(dto.getName());
            entity = attendRepository.save(entity);
            return new AttendDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found");
        }  
    }
}
