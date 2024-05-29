package ru.spbstu.metrics.api.repositories.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.api.models.activity.ClickActivity;

@Repository
public interface ClickActivityRepository extends JpaRepository<ClickActivity, Long> {

}
