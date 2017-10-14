package fr.simplex_software.ouath20.provider;

import javax.ejb.*;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import org.apache.oltu.oauth2.common.*;
import org.apache.oltu.oauth2.common.exception.*;
import org.apache.oltu.oauth2.common.message.types.*;
import org.apache.oltu.oauth2.rs.request.*;
import org.slf4j.*;

@Path("/resource_body")
public class ResourceBodyEndpoint
{
  private static Logger slf4jLogger = LoggerFactory.getLogger(ResourceBodyEndpoint.class);
  @EJB
  private CacheDAO cache;
  
  @POST
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/html")
  public Response get(@Context HttpServletRequest request) throws OAuthSystemException, OAuthProblemException
  {
    OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
    return cache.getAccessToken(request.getParameter("client_id")).equals(oauthRequest.getAccessToken())? Response.ok().build()
        : Response.status(Status.UNAUTHORIZED).header(OAuth.HeaderType.WWW_AUTHENTICATE, OAuth.HeaderType.WWW_AUTHENTICATE).build();
  }
}
