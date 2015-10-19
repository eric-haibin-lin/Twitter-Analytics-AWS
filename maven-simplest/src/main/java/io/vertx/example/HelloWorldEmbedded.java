package io.vertx.example;

import io.vertx.core.Vertx;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HelloWorldEmbedded {

  public static void main(String[] args) {
    // Create an HTTP server which simply returns "Hello World!" to each request.
    PhaistosDiscCipher pdc = new PhaistosDiscCipher("8271997208960872478735181815578166723519929177896558845922250595511921395049126920528021164569045773");
    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    Vertx.vertx().createHttpServer().requestHandler(req -> {
        String publicKeyY = req.params().get("key");
        String messageM = req.params().get("message");
	String result = "";
        if (messageM == null || publicKeyY == null || messageM.isEmpty() || publicKeyY.isEmpty()) {
	    result = "Parameters invalid!";
	} else {
            result = pdc.decrypt(messageM, publicKeyY);
	}
        req.response().end("Coding Squirrels,9327-7717-4260\n" + now + "\n" + result + "\n");
    }).listen(80);
  }

}
