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

object LogJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val logFormat = jsonFormat2(Log)
  implicit val cellFormat = jsonFormat1(Cell)
  implicit val rowFormat = jsonFormat1(Row)
  implicit val columnFormat = jsonFormat3(Column)
  implicit val dataTableFormat = jsonFormat2(DataTable)
}
