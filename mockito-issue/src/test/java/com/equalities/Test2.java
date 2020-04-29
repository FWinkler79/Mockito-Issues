package com.equalities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Test2 {
  
  @Mock
  Token token;
  
  @Mock
  OAuth2TokenClient tokenClient;
  
  @Test
  void testFunctionOverloading() throws MalformedURLException {
    
    when(tokenClient.fetchToken(any(URL.class), anyString(), anyString(),(Duration) ArgumentMatchers.isNull())).thenReturn(token);
    
    TokenProvider provider = new TokenProvider(tokenClient);
    provider.getToken(URI.create("http://test.com").toURL(), "username", "password");
  }
}