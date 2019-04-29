package fr.gab.postgresdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.gab.postgresdemo.exception.ResourceNotFoundException;
import fr.gab.postgresdemo.models.Answer;
import fr.gab.postgresdemo.repository.AnswerRepository;
import fr.gab.postgresdemo.repository.QuestionRepository;

@RestController
public class AnswerController {

    @Autowired
    AnswerRepository answerRepo;

    @Autowired
    QuestionRepository questionRepo;

    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerRepo.findByQuestionId(questionId);
    }

    @PostMapping("/questions/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer) throws ResourceNotFoundException {
        return questionRepo.findById(questionId).map(question -> {
            answer.setQuestion(question);
            return answerRepo.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Question %d was not found.", questionId)));
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public Answer updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId,
            @Valid @RequestBody Answer answerRequest) throws ResourceNotFoundException {
        if (!questionRepo.existsById(questionId))
            throw new ResourceNotFoundException(String.format("Resource %s is not a thing.", questionId));

        return answerRepo.findById(answerId).map(answer -> {
            answer.setText(answerRequest.getText());
            return answerRepo.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Resource %d is not a thing.", answerId)));
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId)
            throws ResourceNotFoundException {
        if (!questionRepo.existsById(questionId))
            throw new ResourceNotFoundException(String.format("Resource %s is not a thing.", questionId));

        return answerRepo.findById(answerId).map(answer -> {
            answerRepo.delete(answer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Resource %d is not a thing.", answerId)));
    }

}