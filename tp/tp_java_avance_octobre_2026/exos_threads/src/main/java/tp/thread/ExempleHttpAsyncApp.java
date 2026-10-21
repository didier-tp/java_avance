package tp.thread;

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExempleHttpAsyncApp {

    public static Dto.CatFact parseCatFactJsonStringAsRecord(String catFactAsJsonString){
        JsonMapper jsonMapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
        return jsonMapper.readValue(catFactAsJsonString,Dto.CatFact.class);
    }
    public static void main(String[] args)  throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req =
                HttpRequest.newBuilder(URI.create("https://catfact.ninja/fact"))
                        .header("User-Agent","Java")
                        .GET()
                        .build();

        //en tp , ajouter au moins une etape intermédiaire (ex de json en java et/ou soit transformer .fact en majuscule , ).

        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(resp -> {
                    System.out.println("recuperation réponse asynchrone / interpreted by " + Thread.currentThread().getName());
                    System.out.println("reponse status:" + resp.statusCode());
                    System.out.println("reponse uri:" + resp.uri().toString());
                    System.out.println("reponse type:" + resp.headers().map().get("Content-Type"));
                    System.out.println("reponse text size:" + resp.body().length());
                    return resp.body();
                })
                //.thenApply(jsonBodyAsString -> jsonBodyAsString.toUpperCase() )
                .thenApply(jsonBodyAsString -> parseCatFactJsonStringAsRecord(jsonBodyAsString) )
                .thenApply(bodyAsCatFactRecord -> new Dto.CatFact(bodyAsCatFactRecord.fact().toUpperCase(),bodyAsCatFactRecord.length()) )
                .thenAccept(body -> { System.out.println("response body:" + body);})
                .join(); // pour attendre la fin de l'execution asynchrone avant de terminer le main
    }
}
