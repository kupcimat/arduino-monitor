package org.kupcimat

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Standard log format
 */
case class Log(timestamp: String, value: Int)

/**
 * Google DataTable log format
 */
case class Cell(v: Int)
case class Row(c: List[Cell])
case class Column(id: String, label: String, `type`: String)
case class DataTable(cols: List[Column], rows: List[Row])

object JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val logFormat = jsonFormat2(Log)
  implicit val cellFormat = jsonFormat1(Cell)
  implicit val rowFormat = jsonFormat1(Row)
  implicit val columnFormat = jsonFormat3(Column)
  implicit val dataTableFormat = jsonFormat2(DataTable)
}

object DataTableSupport {
  def logsToDataTable(logs: List[Log]): DataTable = {
    val data = logs.zipWithIndex.map {
      // TODO return timestamps, not indexes
      case (log, index) => Row(List(Cell(index), Cell(log.value)))
    }
    DataTable(
      List(
        Column("A", "Time", "number"),
        Column("B", "Value", "number")),
      data)
  }
}
