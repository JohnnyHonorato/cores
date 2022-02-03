import {Injectable} from '@angular/core';
import {OAuthService} from 'angular-oauth2-oidc';
import {authConfig} from '../oauth2/oauth2.config';

@Injectable()
export class SSOService {

  constructor(
    private oauthService: OAuthService
  ) {
    this.configureOauthService();
  }

  private configureOauthService() {
    this.oauthService.configure(authConfig);
    this.oauthService.setStorage(sessionStorage);
    this.oauthService.tryLogin({});
  }

  public obtainAccessToken() {
    this.oauthService.redirectUri = location.origin + location.pathname;
    this.oauthService.initImplicitFlow();
  }

  public getUserInfo(): string {
    const idToken = this.oauthService.getIdToken();
    return typeof idToken.sub !== 'undefined' ? idToken.sub.toString() : '';
  }

  public getAccessToken(): string {
    return this.oauthService.getAccessToken();
  }

  public getIdToken(): string {
    return this.oauthService.getIdToken();
  }

  public getUserClaims(): object {
    return this.oauthService.getIdentityClaims();
  }

  public isLoggedIn(): boolean {
    return this.getAccessToken() !== null && this.getIdToken() !== null;
  }

  public logout(): void {
    this.oauthService.logOut(false);
  }
}
