package in.kiranreddy.survey.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kiranreddy.survey.models.Question;
import in.kiranreddy.survey.models.Survey;
import in.kiranreddy.survey.repositories.QuestionRepository;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question question) {
        questionRepository.save(question);
        return question;
    }

    public void delete(Long id) {
        questionRepository.delete(id);
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    public Long count() {
        return questionRepository.count();
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question find(Long id) {
        return questionRepository.findOne(id);
    }

    public Question findFirst(Survey survey) throws IndexOutOfBoundsException {
        return questionRepository.findAll(new Sort(Sort.Direction.ASC, "id")).get(0);
    }
}
