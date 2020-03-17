package dao

import java.util

import com.datastax.oss.driver.api.core.cql.{ResultSet, Row}
import com.datastax.oss.driver.api.core.{CqlIdentifier, CqlSession}
import config.CassandraConfig
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import models.Post

@Singleton
class PostDao @Inject()(config: Configuration) {
  def all: Seq[Post] = {
    val results: ResultSet = db.execute("SELECT * from posts LIMIT 10")
    toPosts(results, List())
  }

  def toPosts(results: ResultSet, acc: List[Post]): Seq[Post] = {
    val it: util.Iterator[Row] = results.iterator()

    def post(row: Row): Post = {
      Post(row.getUuid(0), row.getString("title"), row.getString("content"))
    }

    def loop(it: util.Iterator[Row], acc: List[Post]): Vector[Post] =
      if(it.hasNext()) loop(it, post(it.next()) :: acc) else acc.toVector

    loop(results.iterator(), List())
  }

  private def db: CqlSession = {
    val dbConfig = config.get[CassandraConfig]("databaseConfig")

    CqlSession
      .builder()
      .withAuthCredentials(dbConfig.username, dbConfig.password)
      .withKeyspace(CqlIdentifier.fromCql("eva"))
      .build()
  }
}
