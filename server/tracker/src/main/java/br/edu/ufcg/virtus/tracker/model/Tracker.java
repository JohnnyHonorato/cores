package br.edu.ufcg.virtus.tracker.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang.time.DateUtils;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.model.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tracker")
@Getter
@Setter
@NoArgsConstructor
public class Tracker extends AuditModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private Calendar dueDate;

    @Transient
    private Boolean delay;
    
    @Transient
    private Integer numberComments;
    
    @Transient
    private Integer numberAttachments;

    @OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "tracker")
    private List<Comment> comment;

    @OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "tracker")
    private List<Checklist> checklists;

    @OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "tracker")
    private List<FilePath> attachments;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "transition_id", referencedColumnName = "id")
    private Transition transition;

    @ManyToOne
    @JoinColumn(name = "tracker_model_id", referencedColumnName = "id")
    private TrackerModel trackerModel;

    @ManyToMany(cascade = { CascadeType.REMOVE }, mappedBy = "tracker")
    private List<AttributeValue> attributesValue;

    @ManyToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @JoinTable(name = "tracker_tag", joinColumns = {
            @JoinColumn(name = "tracker_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id") })
    private List<Tag> tags;

    @ManyToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @JoinTable(name = "tracker_assignee", joinColumns = {
            @JoinColumn(name = "tracker_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "assignee_id", referencedColumnName = "id") })
    private List<Assignee> assignees;

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable(name = "tracker_link", joinColumns = {
            @JoinColumn(name = "first_link", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "second_link", referencedColumnName = "id") })
    private List<LinkedTracker> links;

    @PostLoad
    private void calulateDelay() throws BusinessException {
        if (this.dueDate != null) {
            final Date today = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
            final Date dueDate = DateUtils.truncate(this.dueDate.getTime(), java.util.Calendar.DAY_OF_MONTH);

            this.delay = today.after(dueDate);
        }
    }

}
