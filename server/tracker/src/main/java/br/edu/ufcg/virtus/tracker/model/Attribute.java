package br.edu.ufcg.virtus.tracker.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "attribute")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
@NoArgsConstructor
public class Attribute extends Model<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotBlank(message = "{attribute.name.required}")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{attribute.type.required}")
    private AttributeType type;

    @Column(name = "required")
    private Boolean required;

    @Column(name = "position")
    private Integer position;

    @Column(name = "x_axis")
    private Integer positionX;

    @Column(name = "y_axis")
    private Integer positionY;

    @Column(name = "number_of_columns")
    private Integer numberOfColumns;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "list_values")
    private String listValues;

    @Column(name = "needs_value_complement")
    private boolean needsValueComplement;

    @Column(name = "currency")
    private String currency;

    @Column(name = "min_date")
    private Calendar minDate;

    @Column(name = "max_date")
    private Calendar maxDate;

    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "related_attribute_id", referencedColumnName = "id")
    private Attribute relatedAttribute;

    @Column(name = "show_on_card")
    private Boolean showOnCard;

    @ManyToOne
    @JoinColumn(name = "tracker_model_id", referencedColumnName = "id")
    private TrackerModel trackerModel;

}
