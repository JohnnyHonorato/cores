import {Component, HostBinding, Input, OnInit} from '@angular/core';

@Component({
    selector: 'business-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    @Input() @HostBinding('class.expanded') expanded = false;

    constructor() {
    }

    ngOnInit(): void {
    }

}
