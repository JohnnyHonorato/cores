'use strict';
import {environment} from '@environments/environment';

export const SERVER_URL = environment.url;

const CORE_API = 'core/v1';

export namespace AccountURL {
    export const BASE = `${CORE_API}/accounts`;
}

export namespace UserURL {
    export const BASE = `${CORE_API}/users`;
}
