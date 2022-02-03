
export enum FilterType {
    SIMPLE_SELECT = 'select',
    PAGINATED_SELECT = 'paginated-select',
    DATE_PICKER = 'date-picker',
}

export const MetricsType = [
    { value: 'SUM', label: 'tracker-manager.metrics.sum' },
    { value: 'MAX', label: 'tracker-manager.metrics.max' },
    { value: 'MIN', label: 'tracker-manager.metrics.min' },
    { value: 'AVG', label: 'tracker-manager.metrics.avg' }
];
