package org.kupcimat

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Log(timestamp: String, value: Int)

object LogJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val logFormat = jsonFormat2(Log)
}
