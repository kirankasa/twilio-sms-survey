package in.kiranreddy;

import in.kiranreddy.survey.repositories.QuestionRepository;
import in.kiranreddy.survey.repositories.SurveyRepository;
import in.kiranreddy.survey.services.QuestionService;
import in.kiranreddy.survey.services.SurveyService;
import in.kiranreddy.survey.util.SurveyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwilioSmsSurveySampleApplication implements CommandLineRunner{

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private SurveyRepository surveyRepository;

	public static void main(String[] args) {
		SpringApplication.run(TwilioSmsSurveySampleApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		SurveyService surveyService = new SurveyService(surveyRepository);
		QuestionService questionService = new QuestionService(questionRepository);

		SurveyParser surveyParser = new SurveyParser(surveyService, questionService);
		surveyParser.parse("survey.json");
	}
}
