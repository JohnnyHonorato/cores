package br.edu.ufcg.virtus.tracker.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.AuditModel;
import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tracker_model")
@SQLDelete(sql = "UPDATE tracker_model SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
@NoArgsConstructor
public class TrackerModel extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "{tracker-model.name.required}")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "module_id", nullable = false)
    private Integer moduleId;

    @OneToMany(mappedBy = "trackerModel")
    @OrderBy(value = "position asc")
    private List<Status> status;

    @OneToMany(mappedBy = "trackerModel")
    @OrderBy(value = "position asc")
    private List<Attribute> attributes;

    @OneToMany(mappedBy = "trackerModel")
    @OrderBy(value = "position asc")
    private List<Assignee> assignees;

    @OneToMany(mappedBy = "trackerModel")
    private List<Tag> tags;

    @OneToMany(mappedBy = "trackerModel")
    private List<Transition> transitions;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "file_types_restrictions")
    private String fileTypesRestrictions;

}
