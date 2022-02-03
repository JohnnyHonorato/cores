import {Component, Input, OnInit} from '@angular/core';
import {TrackerService} from '@shared/services/tracker.service';
import {TrackerURL} from '@shared/util/url.domain';
import {BaseListComponent} from 'common';

@Component({
    selector: 'tracker-comment',
    templateUrl: './comment.component.html',
    styleUrls: ['./comment.component.scss']
})
export class CommentComponent extends BaseListComponent implements OnInit {

    @Input() trackerId;

    public comment;

    public submitting = false;

    public userName;

    constructor(private trackerService: TrackerService) {
        super();
    }

    ngOnInit() {
        this.currentPageSize = 5;
        super.ngOnInit();
        this.resetComment();
        this.userName = JSON.parse(sessionStorage.getItem('id_token_claims_obj')).sub;
    }

    getRouterURL() {
        return '';
    }

    getServiceURL() {
        return `${TrackerURL.BASE}/${this.trackerId}/comments`;
    }

    getService() {
        return this.trackerService;
    }

    edit(comment) {
        this.comment = { ...comment };
    }

    save(): void {
        this.submitting = true;
        if (!this.comment.id) {
            this.getService().insert(this.getServiceURL(), this.comment)
                .subscribe(() => {
                    this.notification.insertSuccess();
                    this.submitting = false;
                    this.listItems();
                    this.resetComment();
                }, (error) => {
                    this.submitting = false;
                    this.notification.error(error.error ? error.error.message : error.message);
                }
                );
        } else {
            this.getService().update(this.getServiceURL(), this.comment.id, this.comment)
                .subscribe((result) => {
                    this.listItems();
                    this.resetComment();
                }, (error) => {
                    this.submitting = false;
                    this.notification.error(error.error ? error.error.message : error.message);
                }
                );
        }
    }

    private resetComment() {
        this.comment = { id: undefined, text: '', tracker: { id: this.trackerId } };
    }

}

