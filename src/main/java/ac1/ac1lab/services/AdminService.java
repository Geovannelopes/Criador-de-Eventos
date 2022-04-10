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

import ac1.ac1lab.dto.AdminInsertDTO;
import ac1.ac1lab.dto.AdminUpdateDTO;
import ac1.ac1lab.dto.AdminDTO;
import ac1.ac1lab.entities.Admin;
import ac1.ac1lab.entities.Attend;
import ac1.ac1lab.repositories.AdminRepository;
import ac1.ac1lab.repositories.AttendRepository;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired 
    private AttendRepository attendRepository;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest, String name) {
        Page<Admin> list = adminRepository.find(pageRequest, name);
        return list.map( a -> new AdminDTO(a) );
    }

    public AdminDTO getAdminById(Long id) {
        Optional<Admin> op = adminRepository.findById(id);
        Admin adm = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        return new AdminDTO(adm);
    }

    public AdminDTO insert(AdminInsertDTO dto){

        if(dto.getName().isEmpty() || dto.getEmail().isEmpty() || dto.getPhoneNumber().isEmpty()){
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

        Admin entity = new Admin(dto);
        entity = adminRepository.save(entity);
        return new AdminDTO(entity);
    }

    public void delete(Long id){
        try{
            adminRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public AdminDTO update(Long id, AdminUpdateDTO dto){
        try{
            if(dto.getName().isEmpty() || dto.getPhoneNumber().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
            }

            Admin entity = adminRepository.getOne(id);
            entity.setName(dto.getName());
            entity.setPhoneNumber(dto.getPhoneNumber());
            entity = adminRepository.save(entity);
            return new AdminDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }  
    }
}
