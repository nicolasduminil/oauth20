package fr.simplex_software.oauth20.it;

import java.io.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RegistrationResponseDTO implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  private String clientSecret;
  private String issuedAt;
  private String clientId;
  private int expiresIn;
  
  public RegistrationResponseDTO()
  {
  }

  public RegistrationResponseDTO(String clientSecret, String issuedAt, String clientId, int expiresIn)
  {
    this.clientSecret = clientSecret;
    this.issuedAt = issuedAt;
    this.clientId = clientId;
    this.expiresIn = expiresIn;
  }


  @XmlAttribute(name="client_secret")
  public String getClientSecret()
  {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret)
  {
    this.clientSecret = clientSecret;
  }

  @XmlAttribute(name="issued_at")
  public String getIssuedAt()
  {
    return issuedAt;
  }

  public void setIssuedAt(String issuedAt)
  {
    this.issuedAt = issuedAt;
  }


  @XmlAttribute(name="client_id")
  public String getClientId()
  {
    return clientId;
  }

  public void setClientId(String clientId)
  {
    this.clientId = clientId;
  }

  @XmlAttribute(name="expires_in")
  public int getExpiresIn()
  {
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn)
  {
    this.expiresIn = expiresIn;
  }
}
