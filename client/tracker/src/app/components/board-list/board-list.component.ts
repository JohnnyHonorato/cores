import {Component, OnInit} from '@angular/core';
import {BaseModelComponent} from 'common';
import {TrackerModelURL} from '@shared/util/url.domain';
// @ts-ignore
import {singleSpaPropsSubject} from '@single-spa/single-spa-props';

declare var $: any;

@Component({
    selector: 'tracker-board-list',
    templateUrl: './board-list.component.html',
    styleUrls: ['./board-list.component.scss']
})
export class BoardListComponent extends BaseModelComponent implements OnInit {

    items: any = [];
    loading = false;
    removedTrackerModel;
    private moduleId: any;

    constructor() {
        super();
        this.listItems();
    }

    ngOnInit(): void {
        super.ngOnInit();
    }

    getServiceURL(): string {
        return TrackerModelURL.BASE;
    }

    getRouterURL(): string {
        throw new Error('Method not implemented.');
    }

    listItems(): void {
        this.loading = true;
        singleSpaPropsSubject.subscribe((props: any) => {
            this.service.get(`${this.getServiceURL()}/module/${props.data.moduleId}`).subscribe(result => {
                this.items = result;
                this.loading = false;
            });
        });
    }

    remove(trackerModel) {
        this.removedTrackerModel = trackerModel;
        $('#modal-delete').modal('toggle');
    }

    deleteTrackerModel() {
        const index = this.items.indexOf(this.removedTrackerModel);
        if (index !== -1) {
            this.service.delete(`${TrackerModelURL.BASE}/${this.removedTrackerModel.id}`).subscribe(result => {
                this.items.splice(index, 1);
                this.notificationService.successText(this.translate('tracker-model.deleted'));
            });
        }
    }

}
