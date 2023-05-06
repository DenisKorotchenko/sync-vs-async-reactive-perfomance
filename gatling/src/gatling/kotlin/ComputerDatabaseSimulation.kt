import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.core.CoreDsl.rampUsers
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import java.time.Duration

class ComputerDatabaseSimulation : Simulation() {

  val example =
    /*exec(
      http("Add")
        .post("/data/add/10")
    )
      .pause(1)
      //.feed(feeder)
      .*/exec(
        http("Find")
          .get("difference/0/50/50")
          .requestTimeout(Duration.ofSeconds(3))
//          .check(
//            css("a:contains('#{searchComputerName}')", "href").saveAs("computerUrl")
//          )
      )
      //.pause(1)

  // repeat is a loop resolved at RUNTIME
//  val browse =
//    // Note how we force the counter name, so we can reuse it
//    CoreDsl.repeat(4, "i").on(
//      exec(
//        http("Page #{i}").get("/computers?p=#{i}")
//      ).pause(1)
//    )

  // Note we should be using a feeder here
  // let's demonstrate how we can retry: let's make the request fail randomly and retry a given
  // number of times
//
//  val edit =
//    // let's try at max 2 times
//    tryMax(2).on(
//      exec(
//        http("Form")
//          .get("/computers/new")
//      )
//        .pause(1)
//        .exec(
//          http("Post")
//            .post("/computers")
//            .formParam("name", "Beautiful Computer")
//            .formParam("introduced", "2012-05-30")
//            .formParam("discontinued", "")
//            .formParam("company", "37")
//            .check(
//              status().shouldBe { session ->
//                // we do a check on a condition that's been customized with
//                // a lambda. It will be evaluated every time a user executes
//                // the request
//                200 + ThreadLocalRandom.current().nextInt(2)
//              }
//            )
//        )
//    )
//      // if the chain didn't finally succeed, have the user exit the whole scenario
//      .exitHereIfFailed()

  private val httpProtocol =
    http.baseUrl("http://teststand:8080/test-stand/")
      .acceptHeader("*/*")
      //.acceptLanguageHeader("en-US,en;q=0.5")
      //.acceptEncodingHeader("gzip, deflate")
//      .userAgentHeader(
//        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
//      )

  val users = scenario("Users").exec(example)
  //val admins = scenario("Admins").exec(search, browse, edit)

  init {
    setUp(
      users.injectOpen(rampUsers(60 * 5 * 100).during(60 * 5)),
    ).protocols(httpProtocol)
  }
}
