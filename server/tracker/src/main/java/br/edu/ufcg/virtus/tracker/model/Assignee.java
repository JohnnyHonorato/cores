package br.edu.ufcg.virtus.tracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.edu.ufcg.virtus.common.model.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "assignee")
@Getter
@Setter
@NoArgsConstructor
public class Assignee extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "people_id", nullable = false)
    private Integer peopleId;

    @ManyToOne
    @JoinColumn(name = "tracker_model_id", referencedColumnName = "id")
    private TrackerModel trackerModel;

    @Transient
    private String name;
}
