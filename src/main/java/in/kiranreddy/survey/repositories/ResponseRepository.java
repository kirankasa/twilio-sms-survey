package in.kiranreddy.survey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.kiranreddy.survey.models.Question;
import in.kiranreddy.survey.models.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Response getBySessionSidAndQuestion(String sessionSid, Question question);
}
