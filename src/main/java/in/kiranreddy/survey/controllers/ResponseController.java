package in.kiranreddy.survey.controllers;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.kiranreddy.survey.models.Question;
import in.kiranreddy.survey.models.Response;
import in.kiranreddy.survey.models.Survey;
import in.kiranreddy.survey.repositories.QuestionRepository;
import in.kiranreddy.survey.repositories.ResponseRepository;
import in.kiranreddy.survey.services.QuestionService;
import in.kiranreddy.survey.services.ResponseService;
import in.kiranreddy.survey.util.ResponseParser;
import in.kiranreddy.survey.util.TwiMLUtil;

@Controller
public class ResponseController {
    @Autowired
    private QuestionRepository questionRepository;
    private QuestionService questionService;
    @Autowired
    private ResponseRepository responseRepository;
    private ResponseService responseService;

    public ResponseController() {
    }

    /**
     * End point that saves a question response and redirects the call to the next question,
     * if one is available.
     */
    @RequestMapping(value = "/save_response", method = RequestMethod.POST, produces="application/xml")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter responseWriter = response.getWriter();
        this.questionService = new QuestionService(questionRepository);
        this.responseService = new ResponseService(responseRepository);

        Question currentQuestion = getQuestionFromRequest(request);
        Survey survey = currentQuestion.getSurvey();
        persistResponse(new ResponseParser(currentQuestion, request).parse());

        if (survey.isLastQuestion(currentQuestion)) {
            String message = "Tank you for taking the " + survey.getTitle() + " survey. Good Bye";
            if (request.getParameter("MessageSid") != null) {
                responseWriter.print(TwiMLUtil.messagingResponse(message));
            } else {
                responseWriter.print(TwiMLUtil.voiceResponse(message));
            }
        } else {
            responseWriter.print(TwiMLUtil.redirect(survey.getNextQuestionNumber(currentQuestion), survey));
        }
        response.setContentType("application/xml");
    }

    private void persistResponse(Response questionResponse) {
        Question currentQuestion = questionResponse.getQuestion();
        Response previousResponse = responseService.getBySessionSidAndQuestion(questionResponse.getSessionSid(), currentQuestion);
        if (previousResponse != null) {
            // it's already answered. That's an update from Twilio API (Transcriptions, for instance)
            questionResponse.setId(previousResponse.getId());
        }

        /** creates the question response on the db */
        responseService.save(questionResponse);
    }

    private Question getQuestionFromRequest(HttpServletRequest request) {
        return questionService.find(Long.parseLong(request.getParameter("qid")));
    }
}
