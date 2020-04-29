package com.equalities;

import java.net.URL;

public class TokenProvider {

  private OAuth2TokenClient tokenClient;
  
  public TokenProvider(OAuth2TokenClient tokenClient) {
    this.tokenClient = tokenClient;
  }
  
  public Token getToken(URL url, String username, String password) {
    return tokenClient.fetchToken(url, username, password, null);
  }
}
