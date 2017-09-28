package fr.simplex_software.ouath20.provider;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.common.*;
import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.types.*;
import org.apache.oltu.oauth2.rs.request.*;

@Path("/resource_body")
public class ResourceBodyEndpoint
{
  @POST
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/html")
  public Response get(@Context HttpServletRequest request) throws OAuthSystemException, OAuthProblemException
  {
    Response.Status status = null;
    OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
    switch (oauthRequest.getAccessToken())
    {
    case Constants.ACCESS_TOKEN_VALID:
      status = Response.Status.OK;
      break;
    case Constants.ACCESS_TOKEN_INSUFFICIENT:
      status = Response.Status.FORBIDDEN;
      break;
    default:
      status = Response.Status.UNAUTHORIZED;
      break;
    }
    return status.equals(Response.Status.OK) ? Response.status(status).entity(oauthRequest.getAccessToken()).build()
        : Response.status(status).header(OAuth.HeaderType.WWW_AUTHENTICATE, OAuth.HeaderType.WWW_AUTHENTICATE).build();
  }
}
