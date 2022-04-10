package ac1.ac1lab.services;

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
import ac1.ac1lab.dto.PlaceDTO;
import ac1.ac1lab.dto.PlaceInsertDTO;
import ac1.ac1lab.dto.PlaceUpdateDTO;
import ac1.ac1lab.entities.Event;
import ac1.ac1lab.entities.Place;
import ac1.ac1lab.repositories.EventRepository;
import ac1.ac1lab.repositories.PlaceRepository;

@Service
public class PlaceService {
    
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EventRepository eventRepository;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name) {
        Page<Place>list = placeRepository.find(pageRequest, name);
        return list.map( p -> new PlaceDTO(p) );
    }

    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> op = placeRepository.findById(id);
        Place plc = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        return new PlaceDTO(plc);
    }

    public PlaceDTO insert(PlaceInsertDTO dto){

        if(dto.getName().isEmpty() || dto.getAddress().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
        }

        Place entity = new Place(dto);
        entity = placeRepository.save(entity);
        return new PlaceDTO(entity);
    }

    public void delete(Long id){
        try{
            for(Event eve : eventRepository.findAll()){
                for(Place plc : eve.getPlaces()){
                    if(plc.getId().equals(id)){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This place is being used by an event!");
                    }
                }
            }

            placeRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public PlaceDTO update(Long id, PlaceUpdateDTO dto){

        try{
            if(dto.getName().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fill in all fields!");
            }

            Place entity = placeRepository.getOne(id);
            entity.setName(dto.getName());
            entity = placeRepository.save(entity);
            return new PlaceDTO(entity);
        }
        catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }  
    }

    public EventDTO addPlace(Long id, Long place) {
        Optional<Event> op = eventRepository.findById(id);
        Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!"));

        Optional<Place> op2 = placeRepository.findById(place);
        Place plc = op2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found!"));

        eve.addPlace(plc);
        eve = eventRepository.save(eve);
        return new EventDTO(eve);
    }

    public void removePlace(Long id, Long place){
        try{
            Optional<Event> op = eventRepository.findById(id);
            Event eve = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!"));

            Optional<Place> op2 = placeRepository.findById(place);
            Place plc = op2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found!"));

            for(Place event_place : eve.getPlaces()){
                if(event_place.getId() == plc.getId()){
                    eve.removePlace(plc);
                    eve = eventRepository.save(eve);
                    return;
                }
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This event is not associated with this place!");      
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 not found!");
        }
    }
}
