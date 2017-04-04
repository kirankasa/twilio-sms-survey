package in.kiranreddy.survey.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kiranreddy.survey.models.Question;
import in.kiranreddy.survey.models.Survey;
import in.kiranreddy.survey.repositories.SurveyRepository;

@Service
public class SurveyService {
    private SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey create(Survey survey) {
        surveyRepository.save(survey);
        return survey;
    }

    public void delete(Long id) {
        surveyRepository.delete(id);
    }

    public void deleteAll() {
        surveyRepository.deleteAll();
    }

    public Long count() {
        return surveyRepository.count();
    }

    public List<Survey> findAll() {
        return surveyRepository.findAll();
    }

    public Survey find(Long id) {
        return surveyRepository.findOne(id);
    }

    public Survey findLast() {
        List<Survey> surveys = surveyRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        return surveys.isEmpty() ? null : surveys.get(0);
    }

    public List<Question> findSurveyQuestions(Survey survey) {
        return survey.getQuestions();
    }
}
