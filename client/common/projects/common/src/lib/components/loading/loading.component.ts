import {Component, Input} from '@angular/core';

@Component({
    selector: 'common-loading',
    templateUrl: './loading.component.html',
    styleUrls: ['./loading.component.scss']
})
export class LoadingComponent {

    @Input()
    public size: 'small' | 'middle' | 'large' = 'middle';

    @Input()
    public loading: boolean;

    @Input()
    public message: string;
}
