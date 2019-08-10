package com.qinzi123.happiness.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class BussLeagueCooperate implements Serializable
{
  private static final long serialVersionUID = 872914911354150894L;

  private long id;

  private long bussLeagueId;

  private Timestamp datetime;

  private String purposeOpenId;

  private int agreed = 0;

  public long getId()
  {
    return id;
  }

  public void setId( long id )
  {
    this.id = id;
  }

  public long getBussLeagueId()
  {
    return bussLeagueId;
  }

  public void setBussLeagueId( long bussLeagueId )
  {
    this.bussLeagueId = bussLeagueId;
  }

  public Timestamp getDatetime()
  {
    return datetime;
  }

  public void setDatetime( Timestamp datetime )
  {
    this.datetime = datetime;
  }

  public String getPurposeOpenId()
  {
    return purposeOpenId;
  }

  public void setPurposeOpenId( String purposeOpenId )
  {
    this.purposeOpenId = purposeOpenId;
  }

  public int getAgreed()
  {
    return agreed;
  }

  public void setAgreed( int agreed )
  {
    this.agreed = agreed;
  }

}
