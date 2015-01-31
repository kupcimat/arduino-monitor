package org.kupcimat

import java.sql.DriverManager

import akka.actor.Actor
import org.kupcimat.LogJsonSupport._
import spray.http._
import spray.routing._

class LogServiceActor extends Actor with LogService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  def receive = runRoute(handledExceptionsRoute)
}

trait LogService extends HttpService {

  // TODO
  Class.forName("org.h2.Driver")
  val logDao = new LogDao(DriverManager.getConnection("jdbc:h2:mem:test"))

  val exceptionHandler = ExceptionHandler {
    case e: Exception => complete(StatusCodes.InternalServerError, e.getMessage)
  }

  val logRoute: Route =
    path("log") {
      get {
        complete(logDao.getAllLogs)
      } ~
      post {
        entity(as[Log]) { log =>
          logDao.saveLog(log)
          (1 to log.value).foreach(_ => print('|'))
          println(s" ${log.value} %")
          complete(StatusCodes.Created)
        }
      }
    } ~
    path("") {
      getFromResource("web/index.html")
    } ~ {
      getFromResourceDirectory("web")
    }

  val handledExceptionsRoute =
    handleExceptions(exceptionHandler) {
      logRoute
    }
}
