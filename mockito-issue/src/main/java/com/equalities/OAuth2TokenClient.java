package com.equalities;

import java.net.URL;
import java.time.Duration;

public interface OAuth2TokenClient {
  Token fetchToken(URL tokenUrl, String user, String password);
  Token fetchToken(URL tokenUrl, String user, String password, Duration tokenValidity);
}
