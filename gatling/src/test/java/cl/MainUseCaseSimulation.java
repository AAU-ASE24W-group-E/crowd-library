package cl;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://docs.gatling.io/tutorials/recorder/">Gatling quickstart tutorial</a>
 *   <li><a href="https://docs.gatling.io/tutorials/advanced/">Gatling advanced tutorial</a>
 * </ul>
 */
public class MainUseCaseSimulation extends Simulation {
    static final String BASE_URL = "http://localhost";

    static final int OWNERS_COUNT = 5;
    static final int READERS_COUNT = 100;
    static final int DURATION_SECONDS = 30;


    FeederBuilder<String> searchFeeder = csv("search.csv").shuffle().circular();
    FeederBuilder<String> booksFeeder = csv("books.csv").shuffle().circular();

    ChainBuilder register = exec(
            http("Register")
                    .post("/user")
                    .body(StringBody("""
                            {
                            "email": "#{username}@test.me",
                            "username": "#{username}",
                            "password": "somePazSw0rd",
                            "role": "USER"
                            }
                            """))
                    .check(status().in(200, 409)), // suppress status because it will be duplicated in repeated tests
            pause(1),
            http("Login")
                    .post("/user/login")
                    .body(StringBody("""
                            {
                            "username": "#{username}",
                            "password": "somePazSw0rd"
                            }
                            """))
                    .check(jsonPath("$.token").saveAs("token"),
                            jsonPath("$.user.id").saveAs("uId")),
            pause(1),
            exec(session -> session
                    .set("lat", 46.61 + ThreadLocalRandom.current().nextDouble(0.01))
                    .set("lon", 14.48 + ThreadLocalRandom.current().nextDouble(0.01))),
            http("UpdateAddress")
                    .post("/user/#{uId}/address")
                    .body(StringBody("""
                            {
                            "latitude": #{lat},
                            "longitude": #{lon}
                            }
                            """))
    );
    ChainBuilder registerReader = exec(
            exec(session -> session.set("username", "reader-" + UUID.randomUUID())),
            register);
    ChainBuilder registerOwner = exec(
            exec(session -> session.set("username", "owner" + session.userId())),
            register);

    ChainBuilder search = repeat(searchFeeder.recordsCount()).on(
            feed(searchFeeder),
            http("Search")
                    .get("/available-book?quickSearch=#{searchCriterion}&latitude=#{lat}&longitude=#{lon}&distance=30&lendable=true")
                    .check(status().is(200),
                            jsonPath("$.results").ofList().saveAs("availableBooks")),
            doIf(s -> !s.getList("availableBooks").isEmpty()).then(
                    exec(session -> {
                        var uId = session.getString("uId");
                        var books = session.getList("availableBooks");
                        var book = books.stream().filter(b -> !b.toString().contains(uId)).findAny();
                        return book.map(o -> session.set("availableBook", o)).orElse(session);
                    })
            ),
            pause(1)
    );

    ChainBuilder requestBook = exec(
            exitHereIf(s -> !s.contains("availableBook")),
            http("SendLendingRequest")
                    .post("/lendings")
                    .body(StringBody("""
                            {
                            "bookId": "#{availableBook.book.id}",
                            "readerId": "#{uId}",
                            "ownerId": "#{availableBook.owner.id}",
                            "status": "READER_CREATED_REQUEST"
                            }
                            """))
                    .asJson()
                    .check(status().is(200),
                            jsonPath("$.id").saveAs("lendingId")),
            pause(1),
            http("GetLendingRequest")
                    .get("/lendings/readers/#{uId}")
                    .check(status().is(200))
    );

    ChainBuilder addBooks = repeat(booksFeeder.recordsCount()/OWNERS_COUNT).on(
            feed(booksFeeder),
            http("ImportBook")
                    .put("/book/isbn/#{isbn}")
                    .check(status().is(200),
                            jsonPath("$.id").saveAs("bookId")),
            pause(1),
            http("AddBook")
                    .post("/book-owner/#{uId}/book/#{bookId}")
                    .check(status().is(200)),
            pause(1),
            http("GetOwnBooks")
                    .get("/book-owner/#{uId}/book")
                            .check(status().is(200),
                                    jsonPath("$[?(@.book.id == '#{bookId}')]").exists()),
            pause(1),
            http("SetBookStatus")
                    .put("/book-owner/#{uId}/book/#{bookId}")
                    .body(StringBody("""
                            {
                            "status": "AVAILABLE",
                            "lendable": true
                            }
                            """))
                    .asJson()
                    .check(status().is(204)),
            pause(1)
    );

    HttpProtocolBuilder httpProtocol =
            http.baseUrl(BASE_URL)
                    .acceptHeader("application/json")
                    .contentTypeHeader("application/json");

    ScenarioBuilder owners = scenario("Owners").exec(registerOwner, addBooks);
    ScenarioBuilder users = scenario("Users").exec(registerReader, search, requestBook);

    {
        setUp(
                owners.injectOpen(atOnceUsers(OWNERS_COUNT)),
                users.injectOpen(rampUsers(READERS_COUNT).during(DURATION_SECONDS))
        ).protocols(httpProtocol);
    }
}
