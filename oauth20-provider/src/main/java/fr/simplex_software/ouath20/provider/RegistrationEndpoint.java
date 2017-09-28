package fr.simplex_software.ouath20.provider;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.*;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.*;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.*;

@Path("/register")
public class RegistrationEndpoint
{
  public static final Long EXPIRES_IN = 987654321l;
  public static final String CLIENT_ID = "someclientid";
  public static final String ISSUED_AT = "0123456789";
  public static final String CLIENT_SECRET = "someclientsecret";

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public Response register(@Context HttpServletRequest request) throws OAuthSystemException, OAuthProblemException
  {
    OAuthServerRegistrationRequest oauthRequest = new OAuthServerRegistrationRequest(new JSONHttpServletRequestWrapper(request));
    oauthRequest.getType();
    oauthRequest.discover();
    oauthRequest.getClientName();
    oauthRequest.getClientUrl();
    oauthRequest.getClientDescription();
    oauthRequest.getRedirectURI();
    OAuthResponse response = OAuthServerRegistrationResponse.status(HttpServletResponse.SC_OK).setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).setIssuedAt(ISSUED_AT).setExpiresIn(EXPIRES_IN)
        .buildJSONMessage();
    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
  }
}
