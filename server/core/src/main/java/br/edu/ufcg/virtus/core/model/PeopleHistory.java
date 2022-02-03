package br.edu.ufcg.virtus.core.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.core.domain.PeopleType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "people_history")
@Getter
@Setter
public class PeopleHistory extends Model<Integer> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PeopleType type;
    
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "module_source")
    private String moduleSource;
    
    @OneToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    private People people;
}
