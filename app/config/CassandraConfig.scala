package config

import com.typesafe.config.Config
import play.api.ConfigLoader

// How to create a custom configuration

case class CassandraConfig(
                            username: String,
                            password: String
                          )
object CassandraConfig {
  implicit val configLoader: ConfigLoader[CassandraConfig] = new ConfigLoader[CassandraConfig] {
    override def load(rootConfig: Config, path: String): CassandraConfig = {
      val config = rootConfig.getConfig("databaseConfig.cassandra")
      CassandraConfig(
        config.getString("username"),
        config.getString("password")
      )
    }
  }
}