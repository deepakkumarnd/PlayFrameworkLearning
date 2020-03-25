package dao

import com.datastax.oss.driver.api.core.{CqlIdentifier, CqlSession}
import config.CassandraConfig
import javax.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class BaseDao @Inject() (config: Configuration) {

  val db: CqlSession = {
    val dbConfig = config.get[CassandraConfig]("databaseConfig")

    CqlSession
      .builder()
      .withAuthCredentials(dbConfig.username, dbConfig.password)
      .withKeyspace(CqlIdentifier.fromCql("eva"))
      .build()
  }
}
