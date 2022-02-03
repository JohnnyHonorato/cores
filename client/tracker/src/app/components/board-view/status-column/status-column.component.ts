import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TrackerService} from '@shared/services/tracker.service';
import {BaseComponent} from 'common';

@Component({
    selector: 'tracker-status-column',
    templateUrl: './status-column.component.html',
    styleUrls: ['./status-column.component.scss']
})
export class StatusColumnComponent extends BaseComponent implements OnInit {

    @Input() status: any;

    @Input() valueComplement: any;

    @Input() boardName: string;

    @Output() filterCard: EventEmitter<any> = new EventEmitter<any>();

    loading = true;

    isExpanded = true;

    metric = 0;

    metricName = '';

    attributeType = '';

    modal = {
        open: false,
        selectedStatusId: null,
        tracker: null
    };

    dueDateOrder: 'ASC' | 'DESC' = 'ASC';

    constructor(
        private trackerService: TrackerService,
        private route: ActivatedRoute
    ) {
        super();
    }

    ngOnInit(): void {
        this.setItemsByStatus();
    }

    filter(filterItem) {
        this.filterCard.emit(filterItem);
    }

    setItemsByStatus() {
        this.loading = true;
        this.trackerService.getTrackersByStatusId(this.status.id, this.dueDateOrder).subscribe((result: any) => {
            this.attributeType = this.trackerService.metric.attributeType;
            this.status.items = result.trackers;
            this.metric = result.value;
            this.getMetricName(result.metric, result.field);
            if (result.warning) {
                this.notification.warning(`${this.translate('tracker.status.title').toUpperCase()} : ${this.status.name} - ${result.warning}`);
                this.attributeType = this.trackerService.countMetric;
            }
            this.loading = false;
        });
    }

    getMetricName(name, field) {
        if (name === this.trackerService.countMetric) {
            this.attributeType = this.trackerService.countMetric;
            this.metricName = this.translate(`tracker-manager.metrics.${name.toLowerCase()}`);
        } else {
            this.metricName = `${this.translate(`tracker-manager.metrics.${name.toLowerCase()}`)} ${this.translate('tracker-manager.metrics.of')} ${field}`;
        }
    }

    createCard() {
        this.route.params.subscribe(
            (params) => {
                this.navigate([`/tracker/${params.id}/status/${this.status.id}/card/create`]);
            }
        );
    }

    toggleOrder() {
        if (this.dueDateOrder === 'ASC') {
            this.dueDateOrder = 'DESC';
        } else {
            this.dueDateOrder = 'ASC';
        }
        this.setItemsByStatus();
    }

    orderByTitle(by: string) {
        const prefix = this.translateService.instant('tracker.status.order.prefix');
        const orderBy = this.translateService.instant(`tracker.status.order.by.${by}`);
        const order = this.translateService.instant('tracker.status.order.' + this.dueDateOrder.toLowerCase());
        return `${prefix} ${orderBy} ${order}`;
    }

    toggleStatusColumn() {
        this.isExpanded = !this.isExpanded;
    }
}
