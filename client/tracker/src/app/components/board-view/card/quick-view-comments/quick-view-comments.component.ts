import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {BaseListComponent} from 'common';
import {TrackerURL} from '@shared/util/url.domain';
import {QuickViewService} from '@shared/services/quick-view.service';

@Component({
    selector: 'tracker-quick-view-comments',
    templateUrl: './quick-view-comments.component.html',
    styleUrls: ['./quick-view-comments.component.scss']
})
export class QuickViewCommentsComponent extends BaseListComponent implements OnInit {

    trackerId;

    comment;

    comments = [];

    isDisplayed = false;

    elementHeight = 0;

    @ViewChild('dropDownMenu') dropdown: ElementRef;

    @Input() coordinatesCard: any;

    @Input() set setTrackerId(id) {
        this.trackerId = id;
        this.listItems();
    }

    @Output() closeQuickView = new EventEmitter<any>();

    @Output() changeTotalComments = new EventEmitter<any>();

    constructor(private quickViewService: QuickViewService) {
        super();
    }

    ngOnInit() {
        super.ngOnInit();
    }

    getRouterURL(): string {
        return '';
    }

    protected postResult(): void {
        if (this.currentPage === 1) {
            this.comments = [...this.items];
        } else {
            this.comments = [...this.comments, ...this.items];
        }
        this.isDisplayed = true;
    }

    getServiceURL() {
        return `${TrackerURL.BASE}/${this.trackerId}/comments`;
    }

    save() {
        this.getService().insert(this.getServiceURL(), {
            id: undefined,
            text: this.comment,
            tracker: {id: this.trackerId}
        })
            .subscribe(() => {
                    this.notification.insertSuccess();
                    this.currentPage = 1;
                    this.comments = [];
                    this.listItems();
                    this.comment = '';
                    this.changeTotalComments.emit({id: this.trackerId});
                }, (error) => {
                    this.notification.error(error.error ? error.error.message : error.message);
                }
            );
    }

    onScroll() {
        if (this.currentPage < this.totalPages) {
            this.currentPage = this.currentPage + 1;
            this.onChangePaginator(this.currentPage);
        }
    }

    getSimpleUsername(user) {
        return user.split(' ')[0] + ' ' + user.split(' ')[1];
    }

    onChangeDisplay() {
        this.isDisplayed = false;
        this.closeQuickView.emit();
    }

    getTop() {
        return this.quickViewService.getTop(this.coordinatesCard);
    }

    getLeft() {
        return this.quickViewService.getLeft(this.coordinatesCard);
    }

    getRight() {
        return this.quickViewService.getRight(this.coordinatesCard);
    }

    getBottom() {
        return this.quickViewService.getBottom(this.coordinatesCard, this.dropdown);
    }
}
