package br.edu.ufcg.virtus.tracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "attribute_value")
@Getter
@Setter
@NoArgsConstructor
public class AttributeValue extends Model<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @Column(name = "value_complement")
    private String valueComplement;

    @ManyToOne
    @JoinColumn(name = "tracker_id", referencedColumnName = "id")
    @NotNull(message = "{attribute-value.tracker.required}")
    private Tracker tracker;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    @NotNull(message = "{attribute-value.attribute.required}")
    private Attribute attribute;
}
