package org.kupcimat

import java.sql.{Connection, DriverManager, Statement}

import scala.collection.mutable.ListBuffer

class LogDao(connection: Connection) {

  def saveLog(log: Log): Unit = withStatement { statement =>
    statement.executeUpdate(s"INSERT INTO log VALUES ('${log.timestamp}', ${log.value})")
  }

  def getAllLogs: List[Log] = withStatement { statement =>
    val result = statement.executeQuery("SELECT * FROM log ORDER BY timestamp DESC")
    val logs = ListBuffer[Log]()
    while (result.next()) {
      logs += Log(result.getTimestamp("timestamp"), result.getInt("value"))
    }
    logs.toList
  }

  def deleteAllLogs(): Unit = withStatement { statement =>
    statement.executeUpdate("DELETE FROM log")
  }

  def withStatement[T](execution: Statement => T): T = {
    val statement = connection.createStatement()
    val result = execution(statement)
    statement.close()
    result
  }
}

object LogDao {
  def create: LogDao = {
    // TODO create property file
    // Class.forName("org.h2.Driver")
    new LogDao(DriverManager.getConnection("jdbc:h2:~/arduino-monitor;INIT=runscript from 'db/create.sql'"))
  }
}
