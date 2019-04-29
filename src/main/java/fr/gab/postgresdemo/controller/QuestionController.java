package fr.gab.postgresdemo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.gab.postgresdemo.exception.ResourceNotFoundException;
import fr.gab.postgresdemo.models.Question;
import fr.gab.postgresdemo.repository.QuestionRepository;

@RestController
public class QuestionController {

    @Autowired
    QuestionRepository questionRepo;

    @GetMapping("/questions")
    public Page<Question> getQuestion(Pageable pageable) {
        return questionRepo.findAll(pageable);
    }

    @PostMapping("/questions")
    public Question postQuestion(@Valid @RequestBody Question question) {
        return questionRepo.save(question);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long id, @Valid @RequestBody Question questionRequest)
            throws ResourceNotFoundException {
        return questionRepo.findById(id).map(question -> {
            question.setTitle(questionRequest.getTitle());
            question.setDescription(questionRequest.getDescription());
            return questionRepo.save(question);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id : " + id));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) throws ResourceNotFoundException {
        return questionRepo.findById(id).map(question -> {
            questionRepo.delete(question);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Question %d is not found, fucker!", id)));
    }
}