package se.experis.olsson.hampus.herokunate.controllers;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.experis.olsson.hampus.herokunate.models.Actor;
import se.experis.olsson.hampus.herokunate.repositories.ActorRepository;

@RestController
public class ActorController {
    
    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/actor/{id}")
    public ResponseEntity<Actor> getActorById(HttpServletRequest request, @PathVariable Integer id) {

        Actor actor;
        HttpStatus response;

        if (actorRepository.existsById(id)) {
            System.out.println("Actor with id: " + id);
            actor = actorRepository.findById(id).get();
            response = HttpStatus.OK;
        } else {
            System.out.println("Actor not found.");
            actor = null;
            response = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(actor, response);
    }

    @GetMapping("/actor")
    public ResponseEntity<ArrayList<Actor>> getAllActors(HttpServletRequest request) {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        HttpStatus response;

        actors = (ArrayList<Actor>)actorRepository.findAll();
        response = HttpStatus.OK;
        System.out.println("Fetched all actors");

        return new ResponseEntity<>(actors, response);
    }

    @PostMapping("/actor")
    public ResponseEntity<Actor> addActor(HttpServletRequest request, @RequestBody Actor newActor) {
        newActor = actorRepository.save(newActor);

        System.out.println("New actor with id: " + newActor.getId());

        HttpStatus response = HttpStatus.CREATED;

        return new ResponseEntity<>(newActor, response);
    }

    @PatchMapping("/actor/{id}")
    public ResponseEntity<Actor> updateActor(HttpServletRequest request, @RequestBody Actor newActor, @PathVariable Integer id) {
        Actor actor;
        HttpStatus response;

        if (actorRepository.existsById(id)) {
            actor = actorRepository.findById(id).get();
            
            if (newActor.getFirstName() != null) {
                actor.setFirstName(newActor.getFirstName());
            }
            if (newActor.getLastName() != null) {
                actor.setLastName(newActor.getLastName());
            }
            if (newActor.getDateOfBirth() != null) {
                actor.setDateOfBirth(newActor.getDateOfBirth());
            }
            if (newActor.getImdb() != null) {
                actor.setImdb(newActor.getImdb());
            }

            actorRepository.save(actor);
            response = HttpStatus.OK;
            System.out.println("Updated actor with id: " + actor.getId());
        } else {
            System.out.println("Could not find actor with id: " + id);
            actor = null;
            response = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(actor, response);
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<String> deleteActor(HttpServletRequest request, @PathVariable Integer id) {
        String message = "";
        HttpStatus response;

        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            System.out.println("Deleted actor with id: " + id);
            message = "SUCCESS";
            response = HttpStatus.OK;
        } else {
            System.out.println("Could not find actor with id: " + id);
            message = "FAIL";
            response = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(message, response);
    }

}
