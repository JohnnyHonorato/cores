import {Injectable} from '@angular/core';
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import {concat, Observable, of, Subject} from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {TranslateService} from '@ngx-translate/core';
import {SearchService} from './search.service';
import {NotificationService} from './notification.service';

@Injectable()
export class SelectSearchService {

    constructor(
        private searchService: SearchService,
        private translateService: TranslateService,
        private notificationService: NotificationService) {
    }

    startSubject(defaultItems: any[], subject: Subject<string>, url: string, loading: boolean,
                 numItems = null, message = null, column = null, filters = null, sort = null) {
        return concat(
            of(defaultItems), // default items
            subject.pipe(
                debounceTime(200),
                distinctUntilChanged(),
                tap(() => loading = true),
                switchMap(term => this.search(term, url, numItems, message, column, filters, sort).pipe(
                    catchError(() => of([])),
                    tap(() => loading = false)
                    )
                )
            )
        );
    }

    search(term, url, numItems = null, message = null, column = null, filters = null, sort = null): Observable<any[]> {
        const params = {
            currentPage: 1,
            pageSize: numItems ? numItems : 5,
            search: term === null ? '' : term,
            filters: filters === null ? [] : filters,
            sort: {
                order:  sort === null ? '' : sort,
                column: column === null ? '' : column
            }
        };
        return this.searchService.search(url, params)
            .map(result => {
                return result.items;
            })
            .catch((response) => {
                if ((response.status === 403) && message) {
                    this.notificationService.error(this.translateService.instant(message));
                } else {
                    this.notificationService.error(response.error.message);
                }

                return [];
            });
    }

    startTrackerLinkSubject(defaultItems: any[], subject: Subject<string>, url: string, loading: boolean,
                            numItems = null, message = null, column = null, filters = null, sort = null) {
        return concat(
            of(defaultItems), // default items
            subject.pipe(
                debounceTime(200),
                distinctUntilChanged(),
                tap(() => loading = true),
                switchMap(term => this.search(term, this.formatTrackerLinkSubjectUrl(url, term), numItems, message, column, filters, sort).pipe(
                    catchError(() => of([])),
                    tap(() => loading = false)
                )
                )
            )
        );
    }

    private formatTrackerLinkSubjectUrl(url, term) {
        try {
            if (term) {
                return `${url}${(term.match('^[0-9]*$') ? `&trackerId=${term}` : `&trackerTitle=${term}`)}`;
            } else {
                return `${url}&trackerTitle=`;
            }
        } catch (error) {
            return `${url}&trackerTitle=`;
        }

    }

}
