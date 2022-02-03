import {Component, OnInit} from '@angular/core';
import {MovingDirection} from 'angular-archwizard';
import {ActivatedRoute} from '@angular/router';
import {BaseItemComponent} from 'common';
import {TrackerModelURL} from '@shared/util/url.domain';

declare var $: any;

@Component({
    selector: 'tracker-board-edit',
    templateUrl: './board-edit.component.html',
    styleUrls: ['./board-edit.component.css']
})
export class BoardEditComponent extends BaseItemComponent implements OnInit {

    seletor = 'body > app-root > app-board-create > aw-wizard > aw-wizard-navigation-bar > ul > li:nth-child(3)';

    public steps = new Map();

    public board: any = {};

    public submitting = false;

    public stepCount = 0;

    public showChangePosition: true;

    public fourthStepOpen = false;

    constructor(
        private route: ActivatedRoute,
    ) {
        super();
    }

    ngOnInit() {
        super.ngOnInit();
        this.initSteps();
    }

    getRouterURL() {
        return 'tracker';
    }

    getServiceURL() {
        return TrackerModelURL.BASE;
    }

    getActivatedRoute(): ActivatedRoute {
        return this.route;
    }

    setFirstStep($event: any) {
        this.steps.get('first').nextStep = $event.isValid;
        this.board.name = $event.form.value.name;
        this.board.description = $event.form.value.description;
        // TODO: buscar módulo em que o tracker está instalado após US de integração do tracker
        this.board.moduleId = 2;
        this.board.deleted = false;
    }

    setAttachmentTypes($event) {
        this.board.fileTypesRestrictions = $event;
    }

    setSecondStep($event) {
        this.board.attributes = this.fixAttributesPositioning($event);
    }

    fixAttributesPositioning(attributes) {
        attributes.forEach(att => {
            if (att.cols) {
                att.numberOfColumns = att.cols;
                if (att.positionY === undefined || att.positionY === null) {
                    this.getAttributePositionY(attributes, att);
                }
            }
        });
        return attributes;
    }

    getAttributePositionY(listAtt, attribute) {
        const filteredList = this.filterList(listAtt);
        const totalColumns = 12;
        if (filteredList.length === 1) {
            attribute.positionY = 0;
            attribute.positionX = 0;
        } else if (filteredList.length > 1) {
            const lastElement = filteredList[filteredList.length - 2];
            if (!attribute.positionX) {
                const area = lastElement.positionX + lastElement.cols;
                attribute.positionX = this.checkAvailabilityColumnsInRow(totalColumns, area) ? area : 0;
                attribute.positionY = this.checkAvailabilityColumnsInRow(totalColumns, area)
                    ? lastElement.positionY : lastElement.positionY + 1;
            }
        }
    }

    private checkAvailabilityColumnsInRow(totalColumns: number, area) {
        return totalColumns - area >= 3;
    }

    private filterList(listAtt) {
        return listAtt.filter((item) => {
            return !item.deleted;
        });
    }

    save() {
        this.submitting = true;
        if (!this.board.id) {
            this.insert();
        } else {
            this.update();
        }
    }

    changeStep(event) {
        switch (event) {
            case MovingDirection.Forwards:
                this.stepCount++;
                break;
            case MovingDirection.Backwards:
                this.stepCount--;
                break;
        }
    }

    protected postGetItem() {
        this.board = this.item;
        this.initSteps();
    }

    protected update(): void {
        this.service
            .update(this.getServiceURL(), this.item.id, this.board)
            .subscribe(result => {
                    this.notification.updateSuccess();
                    this.submitting = false;
                    this.backToList();
                },
                error => {
                    this.submitting = false;
                    this.notification.error(
                        error.error ? error.error.message : error.message
                    );
                }
            );
    }

    public goToFourthStep() {
        this.fourthStepOpen = true;
        this.stepCount++;
        $(this.seletor).removeClass('current');
        $(this.seletor).addClass('done');
    }

    public backFourthStep() {
        this.fourthStepOpen = false;
        this.stepCount--;
        $(this.seletor).removeClass('done');
        $(this.seletor).addClass('current');
    }

    protected insert() {
        this.service.insert(this.getServiceURL(), this.board).subscribe(
            () => {
                this.notification.insertSuccess();
                this.submitting = false;
                this.backToList();
            },
            error => {
                this.submitting = false;
                this.notification.error(error.error ? error.error.message : error.message);
            }
        );
    }

    private initSteps() {
        this.initFirstStep();
        this.initThirdStep();
    }

    private initFirstStep() {
        if (this.board.name) {
            this.steps.set('first', {nextStep: true});
        } else {
            this.steps.set('first', {nextStep: false});
        }
    }

    private initThirdStep() {
        if (this.board && this.board.status && this.board.status.length >= 2) {
            this.steps.set('third', {nextStep: true});
        } else {
            this.steps.set('third', {nextStep: false});
        }
    }

    setThirdStep($event) {
        if ($event === null) {
            this.steps.set('third', {nextStep: false});
        } else {
            this.board.status = $event;
            this.initThirdStep();
        }
    }

    setIconStepOne() {
        return this.stepCount === 0 ? 'dashboard' : 'done';
    }

    setIconStepTwo() {
        return this.stepCount < 2 ? 'view_day' : 'done';
    }

    setIconStepThree() {
        return this.stepCount < 3 ? 'view_carousel' : 'done';
    }

    changeStatusOrder(visibility) {
        this.showChangePosition = visibility;
    }

    setTransitions($event) {
        this.board.transitions = $event;
    }

}
