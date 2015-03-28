package org.kupcimat

import com.typesafe.config.ConfigFactory

object AppConfig {

  private lazy val config = ConfigFactory.load().getConfig("arduino-monitor")

  object HttpConfig {
    private lazy val httpConfig = config.getConfig("http")
    lazy val host = httpConfig.getString("host")
    lazy val port = httpConfig.getInt("port")
  }

  object DbConfig {
    private lazy val dbConfig = config.getConfig("db")
    lazy val jdbcString = dbConfig.getString("jdbc-string")
  }
}
