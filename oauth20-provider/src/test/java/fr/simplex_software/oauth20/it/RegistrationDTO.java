package fr.simplex_software.oauth20.it;

import java.io.*;

import javax.xml.bind.annotation.*;

//@XmlRootElement
public class RegistrationDTO implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String type;
  private String clientName;
  private String clientURL;
  private String clientDescription;
  private String redirectURL;

  public RegistrationDTO()
  {
  }

  public RegistrationDTO(String type, String clientName, String clientURL, String clientDescription, String redirectURL)
  {
    this.type = type;
    this.clientName = clientName;
    this.clientURL = clientURL;
    this.clientDescription = clientDescription;
    this.redirectURL = redirectURL;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getClientName()
  {
    return clientName;
  }

  public void setClientName(String clientName)
  {
    this.clientName = clientName;
  }

  public String getClientURL()
  {
    return clientURL;
  }

  public void setClientURL(String clientURL)
  {
    this.clientURL = clientURL;
  }

  public String getClientDescription()
  {
    return clientDescription;
  }

  public void setClientDescription(String clientDescription)
  {
    this.clientDescription = clientDescription;
  }

  public String getRedirectURL()
  {
    return redirectURL;
  }

  public void setRedirectURL(String redirectURL)
  {
    this.redirectURL = redirectURL;
  }
}
