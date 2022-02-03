package br.edu.ufcg.virtus.tracker.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.edu.ufcg.virtus.common.model.AuditModel;
import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity(name = "filter")
@SQLDelete(sql = "UPDATE filter SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
@NoArgsConstructor
public class Filter extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "favourite")
    private Boolean isFavourite;

    @Column(name = "number_of_attributes")
    private Integer numberOfAttributes;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "tracker_model_id", referencedColumnName = "id", nullable = false)
    private TrackerModel trackerModel;

    @ManyToMany
    @JoinTable(name = "filter_tag", joinColumns = {
            @JoinColumn(name = "filter_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "tag_id", referencedColumnName = "id")})
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(name = "filter_assignee", joinColumns = {
            @JoinColumn(name = "filter_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "assignee_id", referencedColumnName = "id")})
    private List<Assignee> assignees;

}
