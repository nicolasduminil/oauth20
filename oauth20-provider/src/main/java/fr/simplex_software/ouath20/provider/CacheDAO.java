package fr.simplex_software.ouath20.provider;

import java.util.Map.*;

import javax.annotation.*;
import javax.ejb.*;

import org.apache.commons.lang3.builder.*;
import org.infinispan.*;
import org.infinispan.manager.*;
import org.slf4j.*;

@Singleton
@Startup
public class CacheDAO
{
  public static final String ACCESS_TOKEN_VALID = "access_token_valid";
  public static final String ACCESS_TOKEN_EXPIRED = "access_token_expired";
  public static final String ACCESS_TOKEN_INSUFFICIENT = "access_token_insufficient";
  private static Logger slf4jLogger = LoggerFactory.getLogger(CacheDAO.class);

  @Resource(lookup = "java:jboss/infinispan/oauth20-container")
  private CacheContainer container;
  private static Cache<String, OAuth20CacheItem> cache;

  @PostConstruct
  public void start()
  {
    slf4jLogger.debug("*** Loading the cache from the cache container");
    cache = container.getCache("clientid");
    slf4jLogger.debug("*** Cache loaded");
  }
  
  @PreDestroy
  public void stop()
  {
    slf4jLogger.debug("*** Destroying the cache and the cache container");
    cache.clear();
    cache = null;
    container = null;
    slf4jLogger.debug("*** Cache and cache container destroyed");    
  }

  public void put(String key, OAuth20CacheItem value)
  {
    cache.put(key, value);
  }

  public OAuth20CacheItem get(String key)
  {
    return cache.get(key);
  }

  public boolean containsKey(String key)
  {
    return cache.containsKey(key);
  }

  public String getAuthorizationCode(String clientId)
  {
    return cache.get(clientId).getAuthorizationCode();
  }

  public String getUserName(String clientId)
  {
    return cache.get(clientId).getUserName();
  }

  public String getPassword(String clientId)
  {
    return cache.get(clientId).getPassword();
  }
  
  public String getAccessToken (String clientId)
  {
    return cache.get(clientId).getAccessToken();
  }
  
  public void printContent()
  {
    for (Entry<String, OAuth20CacheItem> es : cache.entrySet())
      slf4jLogger.info("*** entry: {}, value: {}", es.getKey(), ReflectionToStringBuilder.toString(es.getValue()));
  }
}
