import { Directive, ElementRef, EventEmitter, HostListener, Output } from '@angular/core';

@Directive({
    selector: '[trackerFormattingDecimalField]'
})
export class FormattingDecimalFieldDirective {

    @Output()
    receive: EventEmitter<any> = new EventEmitter();

    constructor(private el: ElementRef) {
    }

    @HostListener('keyup.,') undo(event: KeyboardEvent) {
        if (this.el.nativeElement.value[0] === ',') {
            this.receive.emit(true);
        } else {
            this.receive.emit(false);
        }
    }
}
