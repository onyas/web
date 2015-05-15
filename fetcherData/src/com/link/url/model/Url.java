package com.link.url.model;

public class Url
{
  private String site;
  private String key;
  private String content;
  private String href;

  public String getHref()
  {
    return this.href;
  }
  public void setHref(String href) {
    this.href = href;
  }

  public String getSite()
  {
    return this.site;
  }
  public void setSite(String site) {
    this.site = site;
  }
  public String getContent() {
    return this.content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getKey() {
    return this.key;
  }
  public void setKey(String key) {
    this.key = key;
  }
}