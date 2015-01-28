package org.kupcimat

import java.sql.Connection

class LogDao(connection: Connection) {

  val ddl = connection.createStatement()
  ddl.execute("CREATE TABLE log (timestamp TIMESTAMP, value INT)")
  ddl.close()

  def saveLog(log: Log): Unit = {
    val statement = connection.createStatement()
    statement.execute(s"INSERT INTO log VALUES ('${log.timestamp}', ${log.value})")
    statement.close()
  }

  def getAllLogs: List[Log] = {
    val statement = connection.createStatement()
    val result = statement.executeQuery("SELECT * FROM log")

    // TODO
    var logs = List[Log]()
    while (result.next()) {
      logs = Log(result.getString("timestamp"), result.getInt("value")) :: logs
    }
    result.close()
    statement.close()

    logs
  }
}
