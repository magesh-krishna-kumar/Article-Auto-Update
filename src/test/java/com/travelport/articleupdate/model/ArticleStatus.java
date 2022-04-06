package com.travelport.articleupdate.model;

public class ArticleStatus {

  String articleID;
  public ArticleStatus(String articleID, String articleSummary, String articleLink,
      String articleStatus) {
    super();
    this.articleID = articleID;
    this.articleSummary = articleSummary;
    this.articleLink = articleLink;
    this.articleStatus = articleStatus;
  }
  String articleSummary;
  String articleLink;
  String articleStatus;
  public String getArticleID() {
    return articleID;
  }
  public void setArticleID(String articleID) {
    this.articleID = articleID;
  }
  public String getArticleLink() {
    return articleLink;
  }
  public void setArticleLink(String articleLink) {
    this.articleLink = articleLink;
  }
  public String getArticleStatus() {
    return articleStatus;
  }
  public void setArticleStatus(String articleStatus) {
    this.articleStatus = articleStatus;
  }
  public String getArticleSummary() {
    return articleSummary;
  }
  public void setArticleSummary(String articleSummary) {
    this.articleSummary = articleSummary;
  }
}
