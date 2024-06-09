package ru.spbstu.metrics.api.models.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spbstu.metrics.api.dtos.activity.ClickActivityDTO;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tag")
public class Tag {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String elementName;

    @Column
    private String elementId;

    @Column
    private String classes;


    @OneToMany(mappedBy = "tag")
    private List<ClickActivity> clickActivities;


    public Tag(String elementName, String elementId, String classes) {
        this.elementName = elementName;
        this.elementId = elementId;
        this.classes = classes;
    }

    public Tag(ClickActivityDTO clickActivityDTO) {
        this.elementName = clickActivityDTO.getElementName();
        this.elementId = clickActivityDTO.getElementId();
        this.classes = clickActivityDTO.getClasses();
    }
}
