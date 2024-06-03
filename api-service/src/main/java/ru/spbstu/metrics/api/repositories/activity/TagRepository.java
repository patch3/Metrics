package ru.spbstu.metrics.api.repositories.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.metrics.api.models.activity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByElementNameAndElementIdAndClasses(String tagName, String tagId, String classes);
}
