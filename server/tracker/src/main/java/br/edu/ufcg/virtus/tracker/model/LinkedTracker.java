package br.edu.ufcg.virtus.tracker.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class LinkedTracker extends AuditModel<Integer> {

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
    
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "tracker_model_id", referencedColumnName = "id")
    private TrackerModel trackerModel;

    @PostLoad
    private void calulateDelay() throws BusinessException {
        if (this.dueDate != null) {
            final Date today = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
            final Date dueDate = DateUtils.truncate(this.dueDate.getTime(), java.util.Calendar.DAY_OF_MONTH);

            this.delay = today.after(dueDate);
        }
    }

}
