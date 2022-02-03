import {AfterViewInit, Directive, ElementRef, EventEmitter, Output, Renderer2} from '@angular/core';

@Directive({
    selector: '[commonSortHeader]'
})
export class SortHeaderDirective implements AfterViewInit {

    @Output()
    sortChanged = new EventEmitter<any>();

    protected headers: any[];

    constructor(private elem: ElementRef, private renderer: Renderer2) {
    }

    ngAfterViewInit(): void {
        this.initHeaders();
    }

    protected initHeaders(): void {
        this.headers = this.elem.nativeElement.querySelectorAll('th[sortable]');
        this.iterateInAllHeaders();
    }

    protected iterateInAllHeaders(): void {
        this.headers.forEach((header: any) => {
            this.addClassInHeader(header, 'sorting');
            this.addOnClickInHeader(header);
        });
    }

    protected addClassInHeader(header: any, clazz: string): void {
        this.renderer.addClass(header, clazz);
    }

    protected addOnClickInHeader(header: any): void {
        this.renderer.listen(header, 'click', (evt: any) => {

            const column = header.getAttribute('sortable');
            const clazzs: string[] = header.getAttribute('class') ? header.getAttribute('class').split(' ') : [];
            let sort;

            this.removeClazzsForAllHeaders('sorting', 'sorting_desc');
            this.addClazzSortingForAllHeaders(header);

            if (clazzs.includes('sorting') || clazzs.includes('sorting_desc')) {
                this.addClassInHeader(header, 'sorting_asc');
                sort = 'asc';
            } else {
                this.addClassInHeader(header, 'sorting_desc');
                sort = 'desc';
            }

            this.sortChanged.emit({column, sort});

        });
    }

    protected removeClazzsForAllHeaders(...clazzs: string[]): void {
        this.headers.forEach((header: any) => {
            clazzs.forEach((clazz: string) => {
                this.renderer.removeClass(header, clazz);
            });
        });
    }

    protected addClazzSortingForAllHeaders(header: any): void {
        this.headers.forEach((h: any) => {
            if (h !== header) {
                this.renderer.addClass(h, 'sorting');
            }
        });
    }

}
