////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.net.URI;
import java.net.URL;

public class Website {

  private String name;

  private URL url;

  private URI uri;

  @Deprecated
  private String provider;

  public Website() {
  }

  public String getName() {
    return this.name;
  }

  public URL getUrl() {
    return this.url;
  }

  public URI getUri() {
    return this.uri;
  }

  @Deprecated
  public String getProvider() {
    return this.provider;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setUrl(final URL url) {
    this.url = url;
  }

  public void setUri(final URI uri) {
    this.uri = uri;
  }

  @Deprecated
  public void setProvider(final String provider) {
    this.provider = provider;
  }
}
