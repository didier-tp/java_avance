package tp.thread;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExempleHttpAsyncApp {

    public static void main(String[] args)  throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req =
                HttpRequest.newBuilder(URI.create("https://catfact.ninja/fact"))
                        .header("User-Agent","Java")
                        .GET()
                        .build();

        //en tp , ajouter une etape intermédiaire (soit transformer en majuscule , soit de json en java).

        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(resp -> {
                    System.out.println("recuperation réponse asynchrone / interpreted by " + Thread.currentThread().getName());
                    System.out.println("reponse status:" + resp.statusCode());
                    System.out.println("reponse uri:" + resp.uri().toString());
                    System.out.println("reponse type:" + resp.headers().map().get("Content-Type"));
                    System.out.println("reponse text size:" + resp.body().length());
                    return resp.body().toUpperCase();
                })
                .thenAccept(bodyEnMaj -> { System.out.println("en majuscule:" + bodyEnMaj);})
                .join(); // pour attendre la fin de l'execution asynchrone avant de terminer le main
    }
}
