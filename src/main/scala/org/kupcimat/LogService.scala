package org.kupcimat

import akka.actor.Actor
import org.kupcimat.JsonSupport._
import spray.http._
import spray.routing._

class LogServiceActor extends Actor with LogService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  def receive = runRoute(handledExceptionsRoute)
}

trait LogService extends HttpService {

  val logDao = LogDao.create
  val exceptionHandler = ExceptionHandler {
    case e: Exception => complete(StatusCodes.InternalServerError, e.getMessage)
  }

  val logRoute: Route =
    path("logs") {
      get {
        complete(logDao.getAllLogs)
      } ~
      post {
        entity(as[Log]) { log =>
          complete {
            logDao.saveLog(log)
            StatusCodes.Created
          }
        }
      } ~
      delete {
        complete {
          logDao.deleteAllLogs()
          StatusCodes.NoContent
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
