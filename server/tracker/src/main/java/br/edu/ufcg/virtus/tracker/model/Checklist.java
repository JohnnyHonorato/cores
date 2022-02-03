package br.edu.ufcg.virtus.tracker.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "checklist")
@Getter
@Setter
@NoArgsConstructor
public class Checklist extends Model<Integer> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "tracker_id", referencedColumnName = "id")
    private Tracker tracker;

    @OrderBy(value = "id asc")
    @OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "checklist")
    private List <ChecklistItem> items;

}
