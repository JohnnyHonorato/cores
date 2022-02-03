import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {Breadcrumbs} from '@shared/constants/constants';
import {TRACKER_BREADCRUMBS} from '@shared/util/tracker-breadcrumb';
interface IBreadCrumb {
    label: string;
    url: string;
}

@Component({
    selector: 'business-breadcrumb',
    templateUrl: './breadcrumb.component.html',
    styleUrls: ['./breadcrumb.component.scss']
})
export class BreadcrumbComponent implements OnInit {

    public breadcrumbs: IBreadCrumb[];

    public title;

    public icon;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        this.breadcrumbs = this.buildBreadCrumb(this.activatedRoute.root);
    }

    ngOnInit(): void {
        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd)
        ).subscribe(() => {
            this.breadcrumbs = this.buildBreadCrumb(this.activatedRoute.root);
        });
    }

    buildTrackerBreadCrumb(value: any[]) {
        let actual;
        actual = TRACKER_BREADCRUMBS;
        value.shift();
        const ids = [];
        const newBreadCrumb = [];
        for (const item of value) {
            if (parseInt(item, 10)) {
                ids.push(parseInt(item, 10));
                actual = actual[Breadcrumbs.ID];
            } else {
                actual = actual[item];
            }
        }
        let idIndex = 0;
        for (const breadcrumb of actual[Breadcrumbs.BASE]) {
            const urlParts = breadcrumb.url.split('/');
            for (let index = 0; index < urlParts.length; index++) {
                if (urlParts[index].startsWith(':')) {
                    urlParts[index] = ids[idIndex++];
                }
            }
            idIndex = 0;
            newBreadCrumb.push({label: breadcrumb.label, url: urlParts.join('/')});
        }
        return newBreadCrumb;
    }

    buildBreadCrumb(route: ActivatedRoute, url: string = '', breadcrumbs: IBreadCrumb[] = []) {
        if (route.children[0]?.snapshot?.routeConfig.path === 'tracker') {
            return this.buildTrackerBreadCrumb(this.router.url.split('/'));
        }
        this.setPageTitle(route);
        const label = route.routeConfig && route.routeConfig.data ? route.routeConfig.data.breadcrumb : '';
        let path = route.routeConfig && route.routeConfig.data ? route.routeConfig.path : '';
        const lastRoutePart = path.split('/').pop();
        const isDynamicRoute = lastRoutePart.startsWith(':');
        if (isDynamicRoute && !!route.snapshot) {
            const paramName = lastRoutePart.split(':')[1];
            path = path.replace(lastRoutePart, route.snapshot.params[paramName]);
        }
        const nextUrl = this.getNextUrl(path, url);
        const breadcrumb: IBreadCrumb = this.getIBreadCrumb(label, nextUrl);
        const newBreadcrumbs = breadcrumb.label ? [...breadcrumbs, breadcrumb] : [...breadcrumbs];
        if (route.firstChild) {
            return this.buildBreadCrumb(route.firstChild, nextUrl, newBreadcrumbs);
        }
        return newBreadcrumbs;
    }

    getIBreadCrumb(label, nextUrl) {
        return {
            label,
            url: `${nextUrl}`
        };
    }

    getNextUrl(path, url) {
        return path ? `${url}/${path}` : url;
    }

    setPageTitle(route: ActivatedRoute) {
        if (route.routeConfig && route.routeConfig.data && route.routeConfig.data.title && route.routeConfig.data.icon) {
            this.title = route.routeConfig.data.title;
            this.icon = route.routeConfig.data.icon + ' mr-1';
        }
    }

}
