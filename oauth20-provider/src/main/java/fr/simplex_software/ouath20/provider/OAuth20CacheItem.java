package fr.simplex_software.ouath20.provider;

public class OAuth20CacheItem
{
  private String authorizationCode;
  private String clientSecret;
  private String accessToken;
  private String userName;
  private String password;

  public OAuth20CacheItem()
  {
  }

  public OAuth20CacheItem(String clientSecret)
  {
    this.clientSecret = clientSecret;
  }

  public OAuth20CacheItem(String authorizationCode, String clientSecret)
  {
    this (clientSecret);
    this.authorizationCode = authorizationCode;
  }

  public OAuth20CacheItem(String authorizationCode, String clientSecret, String accessToken)
  {
    this (authorizationCode, clientSecret);
    this.accessToken = accessToken;
  }

  public OAuth20CacheItem(String authorizationCode, String clientSecret, String accessToken, String userName, String password)
  {
    this (authorizationCode, clientSecret, accessToken);
    this.userName = userName;
    this.password = password;
  }

  public String getClientSecret()
  {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret)
  {
    this.clientSecret = clientSecret;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public void setAuthorizationCode(String authorizationCode)
  {
    this.authorizationCode = authorizationCode;
  }

  public String getAccessToken()
  {
    return accessToken;
  }

  public void setAccessToken(String accessToken)
  {
    this.accessToken = accessToken;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }
}
