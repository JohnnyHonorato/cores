import {AuthConfig} from 'angular-oauth2-oidc';
import {environment} from '../../../environments/environment';

export const authConfig: AuthConfig = {
    issuer: environment.sso.serverUrl.concat(environment.sso.issuer),
    redirectUri: environment.sso.redirectUri,
    clientId: environment.sso.clientId,
    scope: environment.sso.scope,
    tokenEndpoint: environment.sso.serverUrl.concat(environment.sso.tokenEndpoint),
    userinfoEndpoint: environment.sso.serverUrl.concat(environment.sso.userinfoEndpoint),
    showDebugInformation: environment.sso.showDebugInformation,
    loginUrl: environment.sso.serverUrl.concat(environment.sso.loginEndpoint),
    logoutUrl: environment.sso.serverUrl.concat(environment.sso.logoutEndpoint),
    requireHttps: environment.sso.requireHttps,
    clearHashAfterLogin: true,
    responseType: environment.sso.responseType
};
