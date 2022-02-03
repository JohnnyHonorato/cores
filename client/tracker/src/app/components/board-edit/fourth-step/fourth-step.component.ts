import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
    selector: 'tracker-fourth-step',
    templateUrl: './fourth-step.component.html',
    styleUrls: ['./fourth-step.component.scss']
})
export class FourthStepComponent {

    attributesList;

    @Input() set attributes(list) {
        if (list) {
            this.attributesList = list.filter((item) => {
                return !item.deleted;
            });
        }
    }

    @Output() saveStep: EventEmitter<any> = new EventEmitter<any>();
    @Output() backStep: EventEmitter<any> = new EventEmitter<any>();

    public mockTags = [
        {name: 'BACK', color: '#4F52BF'},
        {name: 'AWS', color: '#11A279'}
    ];

    mockMembers = [
        {id: 1, name: 'Raphael Lima'},
        {id: 2, name: 'Leonardo Dantas'}
    ];

    mockLinks = [
        {id: 1, title: 'Card 1'},
        {id: 2, title: 'Card Novo'}
    ];

    constructor() {
    }

    save() {
        this.saveStep.emit();
    }

    back() {
        this.backStep.emit();
    }


}
