import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
    selector: 'tracker-assignees',
    templateUrl: './assignees.component.html',
    styleUrls: ['./assignees.component.scss']
})
export class AssigneesComponent {

    @Input()
    set listAssignees(assignees) {
        if (assignees) {
            this.assignees = assignees;
        }
    }

    @Output() filter: EventEmitter<any> = new EventEmitter<any>();

    assignees;

    maxAssigneesToDisplay = 3;

    constructor() {
    }

    filterByMember(event, member) {
        event.stopPropagation();
        this.filter.emit(member);
    }
}
