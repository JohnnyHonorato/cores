import {Injectable} from '@angular/core';
import {TrackerModelURL, TrackerURL} from '@shared/util/url.domain';
import {CrudService} from 'common';

@Injectable()
export class TrackerService extends CrudService {

    public filter: any = {};
    public tags: any = [];
    public metric: any = {};
    public countMetric = 'COUNT';

    constructor() {
        super();
    }

    putCardStatusByIdTrackerAndIdStatus(trackerId: number, statusId: number, transitionModel: any) {
        return this.put(`${TrackerURL.BASE}/${trackerId}/status/${statusId}`, transitionModel);
    }

    getOneTrackerModel(idTrackerModel) {
        return this.get(`${TrackerModelURL.BASE}/${idTrackerModel}`);
    }

    getTrackersByStatusId(statusId: number, dueDateOrder: string) {
        this.filter.tags = this.tags;
        const filterJsonString = encodeURI(JSON.stringify(this.filter));
        const metricsJsonString = encodeURI(JSON.stringify(this.metric));
        return this.get(`tracker/v1/trackers?statusId=${statusId}&dueDateOrder=${dueDateOrder}&filter=${filterJsonString}&metrics=${metricsJsonString}`);
    }

    updatePartialTracker(tracker) {
        return this.patch(`${TrackerURL.BASE}/${tracker.id}`, tracker);
    }

}
