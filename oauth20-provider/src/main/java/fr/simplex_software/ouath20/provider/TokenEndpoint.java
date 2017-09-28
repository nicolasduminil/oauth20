package fr.simplex_software.ouath20.provider;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.as.issuer.*;
import org.apache.oltu.oauth2.as.request.*;
import org.apache.oltu.oauth2.as.response.*;
import org.apache.oltu.oauth2.common.*;
import org.apache.oltu.oauth2.common.error.*;
import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.*;
import org.apache.oltu.oauth2.common.message.types.*;

@Path("/token")
public class TokenEndpoint
{
  @POST
  @Consumes("application/x-www-form-urlencoded")
  @Produces("application/json")
  public Response authorize(@Context HttpServletRequest request) throws OAuthProblemException, OAuthSystemException
  {
    OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
    OAuthResponse oauthResponse = null;
    OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
    boolean isClientIdInvalid = !Constants.CLIENT_ID.equals(oauthRequest.getParam(OAuth.OAUTH_CLIENT_ID));
    boolean isAuthorizationCodeInvalid = oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())
        && !Constants.AUTHORIZATION_CODE.equals(oauthRequest.getParam(OAuth.OAUTH_CODE));
    boolean isUserNameOrPasswordInvalid = oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.PASSWORD.toString())
        && (!Constants.PASSWORD.equals(oauthRequest.getPassword()) || !Constants.USERNAME.equals(oauthRequest.getUsername()));
    boolean isGrantTypeRefreshToken = oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString());
    oauthResponse = isClientIdInvalid || isAuthorizationCodeInvalid || isUserNameOrPasswordInvalid || isGrantTypeRefreshToken
        ? OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("client_id not found").buildJSONMessage()
        : OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(oauthIssuerImpl.accessToken()).setExpiresIn("3600").buildJSONMessage();
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
