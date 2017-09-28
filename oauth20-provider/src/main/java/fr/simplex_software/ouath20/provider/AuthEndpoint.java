package fr.simplex_software.ouath20.provider;

import java.net.*;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.as.issuer.*;
import org.apache.oltu.oauth2.as.request.*;
import org.apache.oltu.oauth2.as.response.*;
import org.apache.oltu.oauth2.common.*;
import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.*;
import org.apache.oltu.oauth2.common.message.types.*;

@Path("/auth")
public class AuthEndpoint
{
  @GET
  public Response authorize(@Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException, OAuthProblemException
  {
    OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
    OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
    OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
    if (oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE).equals(ResponseType.CODE.toString()))
      builder.setCode(oauthIssuerImpl.authorizationCode());
    else if (oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE).equals(ResponseType.TOKEN.toString()))
    {
      builder.setAccessToken(oauthIssuerImpl.accessToken());
      builder.setExpiresIn(3600l);
    }
    final OAuthResponse response = builder.location(oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI)).buildQueryMessage();
    return Response.status(response.getResponseStatus()).location(new URI(response.getLocationUri())).build();
  }
}
