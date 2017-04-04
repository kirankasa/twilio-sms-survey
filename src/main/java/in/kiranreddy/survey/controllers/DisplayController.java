package in.kiranreddy.survey.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.kiranreddy.survey.models.Question;
import in.kiranreddy.survey.models.Survey;
import in.kiranreddy.survey.repositories.ResponseRepository;
import in.kiranreddy.survey.repositories.SurveyRepository;
import in.kiranreddy.survey.services.ResponseService;
import in.kiranreddy.survey.services.SurveyService;

@Controller
public class DisplayController {
    @Autowired
    private SurveyRepository surveyRepository;
    private SurveyService surveyService;
    @Autowired
    private ResponseRepository responseRepository;
    private ResponseService responseService;

    public DisplayController() {
    }

    /**
     * Renders the survey results
     *
     * @param model    Empty model where you fill in the data that the template will use
     * @param request  Standard HttpServletRequest request
     * @param response Standard HttpServletResponse response
     * @return returns the template's name
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Map<String, Object> model, HttpServletRequest request,
                        HttpServletResponse response) {
        this.surveyService = new SurveyService(surveyRepository);
        this.responseService = new ResponseService(responseRepository);

        Survey lastSurvey = surveyService.findLast();
        model.put("surveyTitle", lastSurvey.getTitle());

        List<Question> questions = lastSurvey.getQuestions();

        model.put("questions", questions);

        return "index";
    }
}
