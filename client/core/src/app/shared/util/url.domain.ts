'use strict';
import {environment} from '@environments/environment';

export const SERVER_URL = environment.url;

const CORE_API = 'core/v1';

export namespace AccountURL {
    export const BASE = `${CORE_API}/accounts`;
}

export namespace ModuleURL {
    export const BASE = `${CORE_API}/modules`;
}

export namespace OrganizationURL {
    export const BASE = `${CORE_API}/organizations`;
}

export namespace UserURL {
    export const BASE = `${CORE_API}/users`;
}

export namespace PeopleURL {
    export const BASE = `${CORE_API}/people`;
}

export namespace RoleURL {
    export const BASE = `${CORE_API}/roles`;
}

