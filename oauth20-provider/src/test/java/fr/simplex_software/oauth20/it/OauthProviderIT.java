package fr.simplex_software.oauth20.it;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import org.junit.*;

public class OauthProviderIT
{
  private static Client client;

  @BeforeClass
  public static void initClient() throws Exception
  {
    client = ClientBuilder.newClient();
  }

  @AfterClass
  public static void closeClient() throws Exception
  {
    client.close();
    client = null;
  }
  
  @Test
  public void testRegister()
  {
    RegistrationDTO dto = new RegistrationDTO ("pull", "test-api", "localhost:8080/oauth/oauth20", "example API", "localhost:8080/oauth/oauth20");
    client.target("http://localhost:8080/ouath/oauth20/register").request().post(Entity.entity(dto, MediaType.APPLICATION_JSON));
  }
}
