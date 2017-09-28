package fr.simplex_software.ouath20.provider;

import java.net.*;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.oltu.oauth2.common.exception.*;
import org.slf4j.*;

@Path("/redirect")
public class RedirectURIEndpoint
{
  private static Logger slf4jLogger = LoggerFactory.getLogger(RedirectURIEndpoint.class);
  
  @GET
  public Response authorize(@Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException
  {
    slf4jLogger.debug("### Request Params: {}", request.getParameterMap());
    return Response.ok().build();
  }
}
