package org.kupcimat

import org.kupcimat.LogJsonSupport._
import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._

class MyServiceSpec extends Specification with Specs2RouteTest with LogService {

  def actorRefFactory = system
  
  "LogService" should {

    "save sensor log for POST request" in {
      Post("/log", Log("2015-01-27", 42)) ~> logRoute ~> check {
        status mustEqual StatusCodes.Created
      }
    }

    "return all sensor logs for GET request" in {
      Get("/log/1") ~> logRoute ~> check {
        status mustEqual StatusCodes.OK
      }
    }
  }
}
