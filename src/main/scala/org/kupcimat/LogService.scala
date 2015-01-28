package org.kupcimat

import java.sql.DriverManager

import akka.actor.Actor
import org.kupcimat.LogJsonSupport._
import spray.http._
import spray.routing._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with LogService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(handledExceptionsRoute)
}


// this trait defines our service behavior independently from the service actor
trait LogService extends HttpService {

  // TODO
  Class.forName("org.h2.Driver")
  val connection = DriverManager.getConnection("jdbc:h2:mem:test")
  val logDao = new LogDao(connection)

  val exceptionsHandler = ExceptionHandler {
    case e: IllegalArgumentException => complete(StatusCodes.BadRequest, e.getMessage)
  }

  val logRoute: Route =
    path("log") {
      post {
        entity(as[Log]) {
          log => {
            // TODO save log
            logDao.saveLog(log)
            println(log.timestamp)
            (1 to log.value).foreach(_ => print('|'))
            println(s" ${log.value} %")
            complete(StatusCodes.Created)
          }
        }
      }
    } ~
    path("log" / IntNumber) { id =>
      get {
        // TODO retrieve log
        complete(logDao.getAllLogs)
      }
    }

  val handledExceptionsRoute =
    handleExceptions(exceptionsHandler) {
      logRoute
    }
}
