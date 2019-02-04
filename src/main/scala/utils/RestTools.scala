package utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.Source

trait RestTools {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()

  def get(url: String): String = Source.fromURL(url).mkString

  def getRequest(url: String): HttpRequest =
    HttpRequest(
      method = HttpMethods.GET,
      uri = url
    )

  def futureResponse(url: String): Future[HttpResponse] = Http().singleRequest(getRequest(url))

}
