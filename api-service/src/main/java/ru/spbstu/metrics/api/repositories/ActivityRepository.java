package ru.spbstu.metrics.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.api.models.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
