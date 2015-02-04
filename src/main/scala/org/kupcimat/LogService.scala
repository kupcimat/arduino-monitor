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

  // TODO create property file
  Class.forName("org.h2.Driver")
  val logDao = new LogDao(DriverManager.getConnection("jdbc:h2:~/arduino-monitor;INIT=runscript from 'db/create.sql'"))

  val exceptionHandler = ExceptionHandler {
    case e: Exception => complete(StatusCodes.InternalServerError, e.getMessage)
  }

  val logRoute: Route =
    path("logs") {
      get {
        parameter('type ! 'dataTable) {
          complete(convertToDataTable(logDao.getAllLogs))
        } ~
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

  // TODO move somewhere
  def convertToDataTable(logs: List[Log]): DataTable = {
    val data = logs.zipWithIndex.map {
      case (log, index) => Row(List(Cell(index), Cell(log.value)))
    }
    DataTable(
      List(
        Column("A", "Time", "number"),
        Column("B", "Value", "number")),
      data)
  }
}
