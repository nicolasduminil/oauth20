package fr.simplex_software.ouath20.provider;

import java.util.*;

import javax.ejb.*;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.*;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.*;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.*;
import org.slf4j.*;

@Path("/register")
public class RegistrationEndpoint
{
  public static final Long EXPIRES_IN = 987654321l;
  public static final String CLIENT_ID = UUID.randomUUID().toString();
  public static final String ISSUED_AT = "0123456789";
  public static final String CLIENT_SECRET = UUID.randomUUID().toString();
  private static Logger slf4jLogger = LoggerFactory.getLogger(RegistrationEndpoint.class);
  @EJB
  private CacheDAO cache;

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
    cache.put(CLIENT_ID, new OAuth20CacheItem (CLIENT_SECRET));
    slf4jLogger.debug("*** response.getBody(): {}", response.getBody());
    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
  }
}
