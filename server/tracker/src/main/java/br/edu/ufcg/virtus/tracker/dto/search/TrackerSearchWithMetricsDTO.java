package br.edu.ufcg.virtus.tracker.dto.search;

import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.enums.Metric;
import br.edu.ufcg.virtus.tracker.model.TrackerMetrics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerSearchWithMetricsDTO extends ModelDTO {

    public String field;

    public Metric metric;

    public Double value;

    public String warning;

    public String valueComplement;

    public List<TrackerSearchDTO> trackers;

    public TrackerSearchWithMetricsDTO(TrackerMetrics trackerMetrics) {
        this.field = trackerMetrics.getField();
        this.metric = trackerMetrics.getMetric();
        this.value = trackerMetrics.getValue();
        this.warning = trackerMetrics.getWarning();
        this.valueComplement = trackerMetrics.getValueComplement();
    }
}
