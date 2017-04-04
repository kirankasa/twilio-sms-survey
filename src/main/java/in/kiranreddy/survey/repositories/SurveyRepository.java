package in.kiranreddy.survey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.kiranreddy.survey.models.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
