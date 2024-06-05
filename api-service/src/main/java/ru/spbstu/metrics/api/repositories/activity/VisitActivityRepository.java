package ru.spbstu.metrics.api.repositories.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.api.models.Token;
import ru.spbstu.metrics.api.models.activity.VisitActivity;

@Repository
public interface VisitActivityRepository extends JpaRepository<VisitActivity, Long> {
    Page<VisitActivity> findByToken(Token token, Pageable pageable);


}
