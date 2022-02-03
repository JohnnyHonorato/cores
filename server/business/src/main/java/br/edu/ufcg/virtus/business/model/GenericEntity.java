package br.edu.ufcg.virtus.business.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GenericEntity extends Model<Integer> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
