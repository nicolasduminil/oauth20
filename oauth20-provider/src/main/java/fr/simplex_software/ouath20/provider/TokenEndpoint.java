package fr.simplex_software.ouath20.provider;

import javax.ejb.*;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.as.issuer.*;
import org.apache.oltu.oauth2.as.request.*;
import org.apache.oltu.oauth2.as.response.*;
import org.apache.oltu.oauth2.common.error.*;
import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.*;
import org.apache.oltu.oauth2.common.message.types.*;
import org.slf4j.*;

@Path("/token")
public class TokenEndpoint
{
  private static Logger slf4jLogger = LoggerFactory.getLogger(TokenEndpoint.class);

  @EJB
  private CacheDAO cache;

  @POST
  @Consumes("application/x-www-form-urlencoded")
  @Produces("application/json")
  public Response authorize(@Context HttpServletRequest request) throws OAuthProblemException, OAuthSystemException
  {
    slf4jLogger.debug("*** QueryParams: {}", request.getQueryString());
    OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
    OAuthResponse oauthResponse = null;
    OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
    boolean isClientIdValid = cache.containsKey(oauthRequest.getClientId());
    boolean isAuthorizationCodeValid = oauthRequest.getGrantType().equals(GrantType.AUTHORIZATION_CODE.toString())
        && cache.getAuthorizationCode(oauthRequest.getClientId()).equals(oauthRequest.getCode());
    boolean isUserNameOrPasswordValid = oauthRequest.getGrantType().equals(GrantType.PASSWORD.toString()) && cache.getPassword(oauthRequest.getClientId()) != null
        && cache.getPassword(oauthRequest.getClientId()).equals(oauthRequest.getPassword()) && cache.getUserName(oauthRequest.getClientId()) != null
        && cache.getUserName(oauthRequest.getClientId()).equals(oauthRequest.getUsername());
    boolean isGrantTypeRefreshToken = oauthRequest.getGrantType().equals(GrantType.REFRESH_TOKEN.toString());
    if (isClientIdValid && (isAuthorizationCodeValid || isUserNameOrPasswordValid) && !isGrantTypeRefreshToken)
    {
      String token = oauthIssuerImpl.accessToken();
      oauthResponse = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(token).setExpiresIn("3600").buildJSONMessage();
      cache.put(oauthRequest.getClientId(), new OAuth20CacheItem(oauthRequest.getCode(), oauthRequest.getClientSecret(), token));
    }
    else
      OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("client_id not found").buildJSONMessage();
    return Response.status(oauthResponse.getResponseStatus()).entity(oauthResponse.getBody()).build();
  }

  @GET
  @Consumes("application/x-www-form-urlencoded")
  @Produces("application/json")
  public Response authorizeGet(@Context HttpServletRequest request) throws OAuthSystemException
  {
    OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
    OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(oauthIssuerImpl.accessToken()).setExpiresIn("3600").buildJSONMessage();
    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
  }
}
