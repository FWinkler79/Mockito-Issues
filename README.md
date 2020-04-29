
# Problem

Mockito can be tricky when you are overloading methods and calling some of them internally with `null` parameters.

Imagine the following scenario:

```java
public interface OAuth2TokenClient {
  //method
  Token fetchToken(URL tokenUrl, String user, String password);

  //overloaded method
  Token fetchToken(URL tokenUrl, String user, String password, Duration tokenValidity);
}
```

Somewhere in your implementation you might be calling ...

```java
public class TokenProvider {

  private OAuth2TokenClient tokenClient;
  
  public TokenProvider(OAuth2TokenClient tokenClient) {
    this.tokenClient = tokenClient;
  }
  
  public Token getToken(URL url, String username, String password) {
    // using null as the last parameter!!!
    return tokenClient.fetchToken(url, username, password, null);
  }
}
```

Now in your test, when you mock `OAuth2TokenClient` you will want to mock the call to `fetchToken(URL tokenUrl, String user, String password, Duration tokenValidity)`. But if you try it like this, it will fail:

```java
@ExtendWith(MockitoExtension.class)
public class Test2 {
  
  @Mock
  Token token;
  
  @Mock
  OAuth2TokenClient tokenClient;
  
  @Test
  void testFunctionOverloading() throws MalformedURLException {
    
    when(tokenClient.fetchToken(any(URL.class), anyString(), anyString(), any(Duration.class))).thenReturn(token);
    
    TokenProvider provider = new TokenProvider(tokenClient);
    provider.getToken(URI.create("http://test.com").toURL(), "username", "password");
  }
}
```
**Fails with:**
> org.mockito.exceptions.misusing.PotentialStubbingProblem: 
> Strict stubbing argument mismatch. Please check:
> - this invocation of 'fetchToken' method:
>    tokenClient.fetchToken(
>    http://test.com,
>    "username",
>    "password",
>    null
> );

Also this will fail:

```java
@ExtendWith(MockitoExtension.class)
public class Test2 {
  
  @Mock
  Token token;
  
  @Mock
  OAuth2TokenClient tokenClient;
  
  @Test
  void testFunctionOverloading() throws MalformedURLException {
    
    when(tokenClient.fetchToken(any(URL.class), anyString(), anyString(), null)).thenReturn(token);
    
    TokenProvider provider = new TokenProvider(tokenClient);
    provider.getToken(URI.create("http://test.com").toURL(), "username", "password");
  }
}
```

The solution here is to use special argument matchers for null values.

# Solution

Mockito uses argument matchers to match the types / arguments that are input to methods that are being stubbed.
So the solution to the problem described above is to use `ArgumentMatchers.isNull()` like this:

```java
@ExtendWith(MockitoExtension.class)
public class Test1 {
  
  @Mock
  Token token;
  
  @Mock
  OAuth2TokenClient tokenClient;
  
  @Test
  void testFunctionOverloading() throws MalformedURLException {
    
    when(tokenClient.fetchToken(any(URL.class), anyString(), anyString(), (Duration) ArgumentMatchers.isNull())).thenReturn(token);
    
    TokenProvider provider = new TokenProvider(tokenClient);
    provider.getToken(URI.create("http://test.com").toURL(), "username", "password");
  }
}
```
See also branch [null-issue-solution](https://github.com/FWinkler79/Mockito-Issues/tree/null-issue-solution).