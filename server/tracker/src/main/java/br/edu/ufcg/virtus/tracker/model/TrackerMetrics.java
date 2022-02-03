package br.edu.ufcg.virtus.tracker.model;

import java.util.List;

import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import br.edu.ufcg.virtus.tracker.enums.Metric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerMetrics {

    public String field;

    public Metric metric;

    public Double value;

    public String valueComplement;

    public String warning;

    public AttributeType attributeType;

    public List<Tracker> trackers;

    public TrackerMetrics(String field, String metric, AttributeType attributeType, List<Tracker> trackers) {
        super();
        this.field = field;
        this.trackers = trackers;
        this.attributeType = attributeType;
        this.metric = this.getMetricFromString(metric);

        if (this.isMetricRequestEmpty(field, metric, attributeType)) {
            this.setWarning("");
        } else if (attributeType == null) {
            this.setWarning("Atributuo Inválido");
        } else if (this.isEmptyOrNull(metric)) {
            this.setWarning("Métrica Inválida");
        } else if (this.isEmptyOrNull(field)) {
            this.setWarning("Campo Inválido");
        } else if (trackers.size() > 0) {
            if (this.checkHasMetricForAttributeType()) {
                this.calculateMetrics();
            } else {
                this.setWarning("Atributuo Inválido");
            }
        } else {
            this.value = 0.0;
        }
    }

    private boolean isMetricRequestEmpty(String field, String metric, AttributeType attributeType) {
        return this.isEmptyOrNull(metric) && this.isEmptyOrNull(field) && attributeType == null;
    }

    private Metric getMetricFromString(String metric) {
        try {
            return Metric.valueOf(metric);
        } catch (final Exception e) {
            this.setWarning("Métrica Inválida");
            return Metric.COUNT;
        }
    }

    private void calculateMetrics() {
        switch (this.metric) {
            case COUNT:
            case SUM:
            case AVG:
                this.calculateSumCountAvg();
                break;
            case MIN:
            case MAX:
                this.calculateMinMax();
                break;
            default:
                break;
        }
    }

    public void setWarning(String warning) {
        this.warning = warning;
        this.metric = Metric.COUNT;
        this.value = (double) this.trackers.size();
    }

    private void calculateSumCountAvg() {
        double sum = 0;
        double count = 0;
        for (final Tracker tracker : this.trackers) {
            final List<AttributeValue> attributes = tracker.getAttributesValue();
            for (final AttributeValue attribute : attributes) {
                if (this.attributeType.equals(attribute.getAttribute().getType())) {
                    final String fieldName = attribute.getAttribute().getTitle();
                    if (fieldName.equals(this.field)) {
                    	this.valueComplement = attribute.getAttribute().getCurrency();
                    	if (!this.isEmptyOrNull(attribute.getValue())) {
                            count++;
                            sum += Double.parseDouble(attribute.getValue());                            
                        }                    	
                    }                    
                }
            }
        }

        if (count > 0) {
            this.setValueBasedOnCalcs(sum, count, 0, 0);
        } else {
        	this.value = 0.0;
        }
    }

    private void calculateMinMax() {
        int count = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (final Tracker tracker : this.trackers) {
            final List<AttributeValue> attributes = tracker.getAttributesValue();
            for (final AttributeValue attribute : attributes) {
                if (this.attributeType.equals(attribute.getAttribute().getType())) {
                    final String fieldName = attribute.getAttribute().getTitle();
                    if (fieldName.equals(this.field)) {
                    	this.valueComplement = attribute.getAttribute().getCurrency();
                    	if (!this.isEmptyOrNull(attribute.getValue())) {
                            count++;
                            final double value = Double.parseDouble(attribute.getValue());
                            if (value < min) {
                                min = value;
                            }

                            if (value > max) {
                                max = value;
                            }                            
                        }
                    }                    
                }
            }
        }

        if (count > 0) {
            this.setValueBasedOnCalcs(0, 0, max, min);
        } else {
        	this.value = 0.0;
        }
    }

    private void setValueBasedOnCalcs(double sum, double count, double max, double min) {
        switch (this.metric) {
            case COUNT:
                this.value = count;
                break;
            case SUM:
                this.value = sum;
                break;
            case AVG:
                if (count > 0) {
                    this.value = sum / count;
                } else {
                    this.value = count;
                }
                break;
            case MIN:
                this.value = min;
                break;
            case MAX:
                this.value = max;
                break;
            default:
                break;
        }
    }

    private boolean checkHasMetricForAttributeType() {

        switch (this.attributeType) {
            case CURRENCY:
            case INTEGER:
            case DECIMAL:
                return this.getMetricsOfNumber().contains(this.metric);
            default:
                break;
        }

        return false;
    }

    private List<Metric> getMetricsOfNumber() {
        return List.of(Metric.COUNT, Metric.SUM, Metric.AVG, Metric.MAX, Metric.MIN);
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.isBlank();
    }

}
