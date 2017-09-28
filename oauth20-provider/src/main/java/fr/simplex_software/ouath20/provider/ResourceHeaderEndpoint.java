package fr.simplex_software.ouath20.provider;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.common.*;
import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.types.*;
import org.apache.oltu.oauth2.rs.request.*;

@Path("/resource_header")
public class ResourceHeaderEndpoint
{
  @GET
  @Produces("text/html")
  public Response get(@Context HttpServletRequest request) throws OAuthSystemException, OAuthProblemException
  {
    OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
    return oauthRequest.getAccessToken().equals(Constants.ACCESS_TOKEN_VALID) ? Response.status(Response.Status.OK).entity(oauthRequest.getAccessToken()).build()
        : Response.status(Response.Status.UNAUTHORIZED).header(OAuth.HeaderType.WWW_AUTHENTICATE, OAuth.HeaderType.WWW_AUTHENTICATE).build();
  }
}
