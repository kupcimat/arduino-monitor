package org.kupcimat

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import spray.httpx.SprayJsonSupport
import spray.json._

/**
 * Standard log format
 */
case class Log(timestamp: Timestamp, value: Int)

object JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {

  implicit object TimestampJsonFormat extends RootJsonFormat[Timestamp] {
    def write(timestamp: Timestamp): JsValue = {
      val isoDateTime = timestamp.toLocalDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
      JsString(isoDateTime)
    }

    def read(value: JsValue): Timestamp = value match {
      case JsString(v) =>
        val isoDateTime = LocalDateTime.parse(v, DateTimeFormatter.ISO_DATE_TIME)
        Timestamp.valueOf(isoDateTime)
      case _ => throw new DeserializationException("Cannot parse timestamp value")
    }
  }

  implicit val logFormat = jsonFormat2(Log)
}
