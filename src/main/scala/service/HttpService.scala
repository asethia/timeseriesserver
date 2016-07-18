package service

import akka.http.scaladsl.server.Directives

/**
  * This is HTTP REST service for Metric
  *
  * Created by Arun Sethia on 16/07/16.
  */
trait HttpService extends Directives  {
  val serviceRoute = {
    get {
      pathPrefix("metrics") {
        path(("hour" | "min" | "sec") / Segment / Segment) { (metric, value) =>
          //TODO: need to implement logic to store into database via actor system
          complete("ok")
        }
      }
    }
  }
}
