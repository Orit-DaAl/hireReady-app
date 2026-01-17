export const authConfig = {
  clientId: 'oauth2-pkce-client',
  authorizationEndpoint: 'http://localhost:9191/realms/hireready-oauth2/protocol/openid-connect/auth',
  tokenEndpoint: 'http://localhost:9191/realms/hireready-oauth2/protocol/openid-connect/token',
  redirectUri: 'http://localhost:5173',
  scope: 'openid profile email offline_access',
  postLogoutRedirectUri: 'http://localhost:5173',
  
   logoutEndpoint: 'http://localhost:9191/realms/hireready-oauth2/protocol/openid-connect/logout',

  onRefreshTokenExpire: (event) => event.logIn(),
}