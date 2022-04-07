package com.travelport.articleupdate.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@SuppressWarnings("restriction")
@XmlRootElement(name = "ArticleUpdateData")
public class ArticleUpdateData {
  List<ArticleStatus> articleStatusList;

  public List<ArticleStatus> getArticleStatusList() {
    return articleStatusList;
  }

  public void setArticleStatusList(List<ArticleStatus> articleStatusList) {
    this.articleStatusList = articleStatusList;
  }
}
