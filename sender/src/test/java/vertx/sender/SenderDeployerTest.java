package vertx.sender;

import static vertx.common.Constants.DEFAULT_HTTP_PORT;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class SenderDeployerTest {

    private Vertx vertx;

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(SenderVerticle.class.getName(), testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test
    public void sendingMessageTest(TestContext testContext) {

        final Async async = testContext.async();
        final WebClient client = WebClient.create(vertx);
        final String pathParam = "hello";

        client.post(DEFAULT_HTTP_PORT, "localhost", "/sendForAll/" + pathParam)
            .sendJsonObject(null, req -> {
                if (req.succeeded()) {
                    testContext.assertTrue(req.result().bodyAsString().contains(pathParam));
                    async.complete();
                }
            });
    }
}
