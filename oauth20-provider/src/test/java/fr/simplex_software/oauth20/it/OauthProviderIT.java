package fr.simplex_software.oauth20.it;

import static org.junit.Assert.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import org.junit.*;
import org.slf4j.*;

import fr.simplex_software.ouath20.provider.*;

public class OauthProviderIT
{
  private static Client client;
  private static WebTarget target;
  private static Logger slf4jLogger = LoggerFactory.getLogger(OauthProviderIT.class);
  
  @BeforeClass
  public static void initClient() throws Exception
  {
    client = ClientBuilder.newClient();
    target = client.target("http://localhost:80/oauth/oauth20/");
  }

  @AfterClass
  public static void closeClient() throws Exception
  {
    target = null;
    client.close();
    client = null;
  }

  @Test
  public void testRegister() throws Exception
  {
    RegistrationDTO dto = new RegistrationDTO("pull", "test-api", "localhost:8080/oauth/oauth20", "example API", "localhost:8080/oauth/oauth20");
    Response resp = target.path("register").request().post(Entity.entity(dto, MediaType.APPLICATION_JSON));
    assertNotNull(resp);
    Assert.assertEquals(200, resp.getStatus());
    RegistrationResponseDTO rdto = resp.readEntity(RegistrationResponseDTO.class);
    resp.close();
    assertNotNull(rdto);
    assertNotNull(rdto.getClientId());
    assertNotNull(rdto.getClientSecret());
    resp = target.path("auth").queryParam("redirect_uri", "/redirect").queryParam("uri", "uri").queryParam("state", "state").queryParam("scope", "read_ekicclaim").queryParam("response_type", "code")
        .queryParam("client_id", rdto.getClientId()).request().get();
    assertNotNull(resp);
    Assert.assertEquals(302, resp.getStatus());
    OAuth20CacheItem cacheItem = resp.readEntity(OAuth20CacheItem.class);
    resp.close();
    assertNotNull (cacheItem);
    assertNotNull(cacheItem.getAuthorizationCode());
    resp = target.path("token").queryParam("redirect_uri", "/redirect").queryParam("grant_type", "authorization_code").queryParam("code", cacheItem.getAuthorizationCode())
        .queryParam("client_id", rdto.getClientId()).queryParam("client_secret", rdto.getClientSecret()).request().accept(MediaType.APPLICATION_JSON).post(Entity.entity("", MediaType.APPLICATION_FORM_URLENCODED));
    assertNotNull(resp);
    Assert.assertEquals(200, resp.getStatus());
    TokenResponseDTO tresp = resp.readEntity(TokenResponseDTO.class);
    resp.close();
    resp = target.path("resource_body").queryParam("client_id", rdto.getClientId()).queryParam("access_token", tresp.getAccessToken()).request().accept(MediaType.TEXT_HTML).post(Entity.entity("", MediaType.APPLICATION_FORM_URLENCODED));
    assertEquals(200, resp.getStatus());
    resp.close();
  }
}
