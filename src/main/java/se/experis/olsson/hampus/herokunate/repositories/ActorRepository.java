package se.experis.olsson.hampus.herokunate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.experis.olsson.hampus.herokunate.models.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Actor getById(String id);
}
