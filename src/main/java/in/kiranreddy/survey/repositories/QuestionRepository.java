package in.kiranreddy.survey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.kiranreddy.survey.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
