import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';

@Component({
    selector: 'common-delete-confirmation',
    templateUrl: './delete-confirmation.component.html',
    styleUrls: ['./delete-confirmation.component.scss']
})
export class DeleteConfirmationComponent implements OnInit, OnDestroy {

    @Input() target: string;

    @Output()
    protected ok: EventEmitter<boolean> = new EventEmitter<boolean>();

    protected isSubscribed = false;

    constructor() {
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
        this.ok.unsubscribe();
    }

    onOK() {
        this.ok.emit(true);
    }

    subscribe(callback): void {
        if (!this.isSubscribed) {
            this.isSubscribed = !this.isSubscribed;
            this.ok.subscribe(callback);
        }
    }

}
