'use strict';
import {environment} from '@environments/environment';

export const SERVER_URL = environment.url;

const BUSINESS_API = 'business/v1';
const CORE_API = 'core/v1';

export namespace LeadURL {
    export const BASE = `${BUSINESS_API}/leads`;
}

export namespace BusinessOpportunityURL {
    export const BASE = `${BUSINESS_API}/business-opportunity`;
}

export namespace OrganizationURL {
    export const BASE = `${BUSINESS_API}/organizations`;
}

export namespace AccountURL {
    export const BASE = `${CORE_API}/accounts`;
}

export namespace UserURL {
    export const BASE = `${CORE_API}/users`;
}

export namespace PeopleURL {
    export const BASE = `${CORE_API}/people`;
}

export namespace FileURL {
    export const BASE = `${CORE_API}/file`;
}
