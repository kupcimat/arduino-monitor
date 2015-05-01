package org.kupcimat

import java.sql.{Connection, ResultSet, Statement, Timestamp}

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope


class LogDaoTest extends Specification with Mockito {

  "LogDao" should {

    "save log" in new EmptyDatabase {
      logDao.saveLog(Log(new Timestamp(0), 42))
      there was one(statement).executeUpdate(s"INSERT INTO log VALUES ('${new Timestamp(0)}', 42)")
    }

    "delete all logs" in new EmptyDatabase {
      logDao.deleteAllLogs()
      there was one(statement).executeUpdate("DELETE FROM log")
    }

    "get all logs" in new DatabaseWithData {
      logDao.getAllLogs mustEqual List(Log(new Timestamp(0), 42), Log(new Timestamp(1), 43))
    }
  }

  trait EmptyDatabase extends Scope {
    val statement = mock[Statement]
    val connection = mock[Connection]
    connection.createStatement() returns statement
    val logDao = new LogDao(connection)
  }

  trait DatabaseWithData extends EmptyDatabase {
    val resultSet = mock[ResultSet]
    resultSet.next() returns true thenReturn true thenReturn false
    resultSet.getTimestamp("timestamp") returns new Timestamp(0) thenReturn new Timestamp(1)
    resultSet.getInt("value") returns 42 thenReturn 43
    statement.executeQuery("SELECT * FROM log ORDER BY timestamp DESC") returns resultSet
  }

}
