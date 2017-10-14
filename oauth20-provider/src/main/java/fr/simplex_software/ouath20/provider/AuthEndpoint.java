package fr.simplex_software.ouath20.provider;

import java.net.*;

import javax.ejb.*;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.commons.lang3.builder.*;
import org.apache.oltu.oauth2.as.issuer.*;
import org.apache.oltu.oauth2.as.request.*;
import org.apache.oltu.oauth2.as.response.*;
import org.apache.oltu.oauth2.common.*;
import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.*;
import org.apache.oltu.oauth2.common.message.types.*;
import org.slf4j.*;

@Stateless
@Path("/auth")
public class AuthEndpoint
{
  private static Logger slf4jLogger = LoggerFactory.getLogger(AuthEndpoint.class);
  @EJB
  private CacheDAO cache;
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response authorize(@Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException, OAuthProblemException
  {
    slf4jLogger.debug("*** QueryParams: {}", request.getQueryString());
    OAuth20CacheItem cacheItem = null;
    OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
    OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
    OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
    if (oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE).equals(ResponseType.CODE.toString()))
    {
      String authorizationCode = oauthIssuerImpl.authorizationCode();
      cacheItem = new OAuth20CacheItem (authorizationCode, oauthRequest.getClientSecret());
      cache.put(oauthRequest.getClientId(), cacheItem);
      builder.setCode(authorizationCode);
    }
    else if (oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE).equals(ResponseType.TOKEN.toString()))
    {
      String accessToken = oauthIssuerImpl.accessToken();
      cacheItem = new OAuth20CacheItem (oauthRequest.getParam(OAuth.OAUTH_CODE), oauthRequest.getClientSecret(), accessToken);
      cache.put(oauthRequest.getClientId(), cacheItem);
      builder.setAccessToken(accessToken);
      builder.setExpiresIn(3600l);
    }
    final OAuthResponse response = builder.location(oauthRequest.getRedirectURI()).buildQueryMessage();
    slf4jLogger.debug("*** Returning cacheItem: {}", ReflectionToStringBuilder.toString(cacheItem));
    return Response.status(response.getResponseStatus()).location(new URI(response.getLocationUri())).entity(cacheItem).build();
  }
}
