package fr.gab.postgresdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gab.postgresdemo.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}