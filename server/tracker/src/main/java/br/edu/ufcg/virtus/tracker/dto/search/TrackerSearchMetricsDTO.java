package br.edu.ufcg.virtus.tracker.dto.search;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerSearchMetricsDTO extends ModelDTO {

    public AttributeType attributeType;

    public String metricField;

    public String metric;

}
