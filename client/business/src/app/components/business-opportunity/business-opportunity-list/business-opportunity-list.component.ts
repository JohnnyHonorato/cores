import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'business-opportunity-list',
  templateUrl: './business-opportunity-list.component.html',
  styleUrls: ['./business-opportunity-list.component.css']
})
export class BusinessOpportunityListComponent implements OnInit {

  public headersColumns = [
    { title: 'common.title', sortable: 'title', col: 'col-md-7', value: 'title' }
  ];

  public entity = 'business_opportunity';

  constructor() { }

  ngOnInit(): void {}

}
