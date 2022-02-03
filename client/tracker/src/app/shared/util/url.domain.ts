'use strict';


import {environment} from '@environments/environment';

export const SERVER_URL = environment.url;

const TRACKER_API = 'tracker/v1';

export namespace TrackerURL {
    export const BASE = `${TRACKER_API}/trackers`;
}

export namespace TrackerModelURL {
    export const BASE = `${TRACKER_API}/tracker-models`;
}

export namespace PeopleURL {
    export const BASE = `${TRACKER_API}/people`;
    export const BY_ORGANIZATION = `${TRACKER_API}/people/organizations`;
}

export namespace ChecklistURL {
    export const BASE = `${TRACKER_API}/checklists`;
}

export namespace OrganizationURL {
    export const BASE = `${TRACKER_API}/organizations`;
}

export namespace filePathURL {
    export const TRACKER = `${TRACKER_API}/file-path/trackers`;
}
