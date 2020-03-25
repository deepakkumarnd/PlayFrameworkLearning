package dao

import java.util

import com.datastax.oss.driver.api.core.cql.{ResultSet, Row}
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import models.Post
import scala.concurrent.{ExecutionContext, Future}
import scala.compat.java8.FutureConverters._
import scala.jdk.CollectionConverters._

@Singleton
class PostDao @Inject()(config: Configuration)(implicit ec: ExecutionContext) extends BaseDao(config) {
  def all: Seq[Post] = {
    val results: ResultSet = db.execute("SELECT * from posts LIMIT 10")
    toPosts(results, List())
  }

  def getAllPosts: Future[Seq[Post]] = {
    db.executeAsync("SELECT * from posts LIMIT 10").toScala.map { rs =>
      rs.currentPage.iterator.asScala.map { row =>
        Post(row.getUuid(0), row.getString("title"), row.getString("content"))
      }.toSeq
    }
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
}
