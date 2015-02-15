package org.kupcimat

import java.sql.Timestamp

import org.kupcimat.JsonSupport._
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class LogServiceTest extends Specification with Specs2RouteTest with LogService {

  def actorRefFactory = system

  // TODO make proper tests with mocked db
  "LogService" should {

    "return all logs with GET request" in {
      Get("/logs") ~> logRoute ~> check {
        status mustEqual StatusCodes.OK
      }
    }

    "create new log with POST request" in {
      Post("/logs", Log(new Timestamp(0L), 42)) ~> logRoute ~> check {
        status mustEqual StatusCodes.Created
      }
    }

    "delete all logs with DELETE request" in {
      Delete("/logs") ~> logRoute ~> check {
        status mustEqual StatusCodes.NoContent
      }
    }
  }
}
