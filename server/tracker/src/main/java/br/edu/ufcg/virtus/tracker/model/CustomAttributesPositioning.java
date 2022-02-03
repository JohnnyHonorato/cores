package br.edu.ufcg.virtus.tracker.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CustomAttributesPositioning {
    
    @Column(name = "x_axis")
    private Integer xAxis;
    
    @Column(name = "y_axis")
    private Integer yAxis;
    
    @Column(name = "number_of_columns")
    private Integer numberOfColumns;
}
