package org.kupcimat

import java.sql.{Connection, Statement}

class LogDao(connection: Connection) {

  def saveLog(log: Log): Unit = withStatement {
    statement => statement.executeUpdate(s"INSERT INTO log VALUES ('${log.timestamp}', ${log.value})")
  }

  def getAllLogs: List[Log] = withStatement { statement =>
    val result = statement.executeQuery("SELECT * FROM log")
    // TODO find more elegant solution?
    var logs = List[Log]()
    while (result.next()) {
      logs = Log(result.getString("timestamp"), result.getInt("value")) :: logs
    }

    logs
  }

  def withStatement[T](execution: Statement => T): T = {
    val statement = connection.createStatement()
    val result = execution(statement)
    statement.close()
    result
  }
}
