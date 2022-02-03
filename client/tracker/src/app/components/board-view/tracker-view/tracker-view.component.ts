import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TrackerService} from '@shared/services/tracker.service';
import {TrackerModelURL, TrackerURL} from '@shared/util/url.domain';
import {BaseItemComponent} from 'common';
import {Subject} from 'rxjs';
import {CommentComponent} from './comment/comment.component';

@Component({
    selector: 'tracker-tracker-view',
    templateUrl: './tracker-view.component.html',
    styleUrls: ['./tracker-view.component.scss']
})
export class TrackerViewComponent extends BaseItemComponent implements OnInit {

    @ViewChild(CommentComponent) commentComponent: CommentComponent;

    modal = {
        open: false,
        tracker: null,
        selectedStatusId: null
    };

    trackerModel;

    members;

    loadTrackerModel;

    removeChecklistItemSubject: Subject<void> = new Subject<void>();

    constructor(private route: ActivatedRoute, private trackerService: TrackerService) {
        super();
        this.router.routeReuseStrategy.shouldReuseRoute = () => {
            return false;
        };
    }

    ngOnInit() {
        super.ngOnInit();
        this.getTrackerModel();
    }

    getServiceURL(): string {
        return TrackerURL.BASE;
    }

    getActivatedRoute(): ActivatedRoute {
        return this.route;
    }

    getRouterURL(): string {
        return `/tracker/${this.getTrackerIdKey()}`;
    }

    protected postGetItem(): void {
        this.members = this.item.assignees;
    }

    getTrackerModel() {
        this.loadTrackerModel = false;
        this.trackerService.get(`${TrackerModelURL.BASE}/${this.getTrackerIdKey()}`).subscribe(result => {
            this.trackerModel = result;
            this.loadTrackerModel = true;
        });
    }

    protected getItemIdKey(): string {
        return 'idCard';
    }

    getTrackerIdKey(): string {
        return this.getParam('id');
    }

    reloadComments() {
        this.commentComponent.listItems();
    }

    toEdit() {
        this.route.params.subscribe(
            (params) => {
                this.navigate([`/${this.getRouterURL()}/status/${params.statusId}/card/edit/${this.item.id}`]);
            });
    }

    navigateToLinkedCard(value) {
        this.router.navigate([`tracker/${value.trackerModel.id}/status/${value.status.id}/card/view/${value.id}`]);
    }
}
