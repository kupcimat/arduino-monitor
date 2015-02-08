package org.kupcimat

import org.kupcimat.JsonSupport._
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class LogServiceTest extends Specification with Specs2RouteTest with LogService {

  def actorRefFactory = system

  // TODO make proper tests with mocked db
  "LogService" should {

    "return all logs in standard json format with GET request" in {
      Get("/logs") ~> logRoute ~> check {
        status mustEqual StatusCodes.OK
      }
    }

    "return all logs in DataTable json format with GET request" in {
      Get("/logs?type=dataTable") ~> logRoute ~> check {
        status mustEqual StatusCodes.OK
      }
    }

    "create new log with POST request" in {
      Post("/logs", Log("2015-05-25", 42)) ~> logRoute ~> check {
        status mustEqual StatusCodes.Created
      }
    }
  }
}
