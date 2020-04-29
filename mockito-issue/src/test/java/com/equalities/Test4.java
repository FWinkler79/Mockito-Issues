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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Test4 {
  
  @Mock
  Token token;
  
  @Mock
  OAuth2TokenClient tokenClient;
  
  @Test
  void testFunctionOverloading() throws MalformedURLException {
    
    when(tokenClient.fetchToken(any(URL.class), anyString(), anyString(), any(Duration.class))).thenReturn(token);
    
    tokenClient.fetchToken(URI.create("http://test.com").toURL(), "username", "password", null);
  }
}