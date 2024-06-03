package ru.spbstu.metrics.api.repositories.activity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.api.models.Token;
import ru.spbstu.metrics.api.models.activity.VisitActivity;

import java.util.List;

@Repository
public interface VisitActivityRepository extends JpaRepository<VisitActivity, Long> {
    List<VisitActivity> findByToken(Token token, Pageable pageable);


}
