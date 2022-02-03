import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TrackerService} from '@shared/services/tracker.service';
import {BaseListComponent} from 'common';
import {DragulaService} from 'ng2-dragula';
import {TrackerURL} from '@shared/util/url.domain';
import {Subscription} from 'rxjs';

@Component({
    selector: 'tracker-board-view',
    templateUrl: './board-view.component.html',
    styleUrls: ['./board-view.component.scss']
})
export class BoardViewComponent extends BaseListComponent implements OnInit, OnDestroy {

    trackerModelId: any;

    trackerModel: any;

    statusList: any = [];

    loading = false;

    filter;

    tagsFilter = [];

    membersFilter = [];

    subs = new Subscription();

    isOpenFilter = false;

    boardName: string;

    selectedFilter = false;

    appliedFilter = false;

    constructor(
        private dragulaService: DragulaService,
        private trackerService: TrackerService,
        private route: ActivatedRoute
    ) {
        super();
    }

    ngOnInit(): void {
        this.setIdTrackerModel();
        this.loadTrackerModel();
        this.startDragulaLibraryCardSwitchingEvents();
    }

    ngOnDestroy() {
        this.subs.unsubscribe();
        this.trackerService.filter = {};
        this.trackerService.tags = [];
    }

    getServiceURL(): string {
        return `${TrackerURL.BASE}`;
    }

    getRouterURL(): string {
        return 'tracker';
    }

    setIdTrackerModel() {
        this.route.params.subscribe((params) => {
            this.trackerModelId = params.id;
        });
    }

    loadTrackerModel() {
        this.loading = true;
        this.trackerService.getOneTrackerModel(this.trackerModelId)
            .subscribe(result => {
                    this.trackerModel = result;
                    this.statusList = this.trackerModel.status;
                    this.loading = false;
                },
                error => {
                    this.notification.error(error.error ? error.error.message : error.message);
                    this.loading = false;
                });
    }

    startDragulaLibraryCardSwitchingEvents() {
        this.subs.add(
            this.dragulaService.dropModel().subscribe(
                ({target, source, item, sourceModel, targetModel, sourceIndex, targetIndex}) => {
                    if (source.id !== target.id) {
                        const statusId = +target.getAttribute('id');
                        const transitionModel = this.getTransitionModel(+source.id, +target.id);
                        this.postCardStatusChange(item, statusId, transitionModel, sourceModel, sourceIndex, targetModel, targetIndex);
                    }
                })
        );
    }

    private postCardStatusChange(
        item, statusId: number, transitionModel: any, sourceModel: any[], sourceIndex: number, targetModel: any[], targetIndex: number) {
        this.trackerService.putCardStatusByIdTrackerAndIdStatus(item.id, statusId, transitionModel)
            .subscribe((result: any) => {
                    item.numberComments += 1;
                    item.transition = result.transition;
                }, (result) => {
                    this.dragulaService.find('CARDS').drake.cancel(true);
                    sourceModel.splice(sourceIndex, 0, ...targetModel.splice(targetIndex, 1));
                    this.notification.error(result.error.message);
                }
            );
    }

    getTransitionModel(sourceStatusId, targetStatusId) {
        return {
            trackerModel: this.trackerModel,
            source: {id: sourceStatusId},
            target: {id: targetStatusId}
        };
    }

    setSelectedFilter(value) {
        this.trackerService.filter = {};
        this.trackerService.tags = [];
        this.selectedFilter = true;
        if (value === null) {
            this.filter = null;
            this.tagsFilter = [];
            this.membersFilter = [];
            this.selectedFilter = false;
            this.appliedFilter = false;
        } else {
            this.filter = value;
            this.membersFilter = value.assignees;
            if (value.tags.length > 0) {
                value.tags.forEach(element => {
                    this.addTagInFilter(element);
                });
            }
            if (value.assignees.length > 0) {
                this.trackerService.filter.memberId = value.assignees[0].peopleId;
            }
        }
        this.listItems();
    }

    filterCard(filterItem) {
        this.appliedFilter = true;
        if (filterItem?.color) {
            this.addTagInFilter(filterItem);
        } else {
            this.membersFilter = [];
            this.membersFilter.push(filterItem);
            this.trackerService.filter.memberId = filterItem.peopleId;
        }
        this.setFilter();
        this.listItems();
    }

    private addTagInFilter(filterItem) {
        if (!this.tagsFilter.find(t => t.id === filterItem.id)) {
            this.tagsFilter.push(filterItem);
            this.trackerService.tags.push(filterItem.id);
        }
    }

    setFilter() {
        this.filter = {
            tags: this.tagsFilter ? this.tagsFilter : [],
            assignees: this.membersFilter ? this.membersFilter : []
        };
    }

    openFilter() {
        this.isOpenFilter = !this.isOpenFilter;
    }

    getHeightStatusColumn() {
        if (!this.isOpenFilter && !this.selectedFilter && !this.appliedFilter) {
            return 'no-filters';
        } else if ((this.isOpenFilter && !this.selectedFilter && !this.appliedFilter) ||
            (!this.isOpenFilter && this.selectedFilter && !this.appliedFilter) ||
            (!this.isOpenFilter && !this.selectedFilter && this.appliedFilter)) {
            return 'list-filters';
        } else {
            return 'edit-filters';
        }
    }

    removeItemsFilter(filterItem) {
        if (filterItem?.color) {
            const indexTag = this.tagsFilter.indexOf(filterItem);
            this.tagsFilter.splice(indexTag, 1);
            const indexTagsService = this.trackerService.tags.indexOf(filterItem.id);
            this.trackerService.tags.splice(indexTagsService, 1);
        } else {
            const indexMember = this.membersFilter.indexOf(filterItem);
            this.membersFilter.splice(indexMember, 1);
            delete this.trackerService.filter.memberId;
        }
        this.setFilter();
        this.listItems();
    }
}
