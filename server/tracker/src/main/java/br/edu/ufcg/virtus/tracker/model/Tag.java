package br.edu.ufcg.virtus.tracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tag")
@Getter
@Setter
@NoArgsConstructor
public class Tag extends Model<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tracker_model_id", referencedColumnName = "id")
    private TrackerModel trackerModel;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;
}
