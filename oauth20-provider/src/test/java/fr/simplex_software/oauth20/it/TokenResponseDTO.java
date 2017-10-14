package fr.simplex_software.oauth20.it;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TokenResponseDTO
{
  private String accessToken;
  private String tokenId;
  private int expiresIn;

  public TokenResponseDTO()
  {
  }

  public TokenResponseDTO(String accessToken, String tokenId, int expiresIn)
  {
    this.accessToken = accessToken;
    this.tokenId = tokenId;
    this.expiresIn = expiresIn;
  }

  @XmlAttribute(name="access_token")
  public String getAccessToken()
  {
    return accessToken;
  }

  public void setAccessToken(String accessToken)
  {
    this.accessToken = accessToken;
  }

  @XmlAttribute(name="token_id")
  public String getTokenId()
  {
    return tokenId;
  }

  public void setTokenId(String tokenId)
  {
    this.tokenId = tokenId;
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
