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

    public static void simulateLongTask(String msg,long nbMs){
        try {
            System.out.println(">>(begin)" + msg + " / by " + Thread.currentThread().getName());
            Thread.sleep(nbMs);
            System.out.println("<<(end)" + msg + " / by " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("** interrupted **");
        }
    }

    public static void main(String[] args)  throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req =
                HttpRequest.newBuilder(URI.create("https://catfact.ninja/fact"))
                        .header("User-Agent","Java")
                        .GET()
                        .build();

        //en tp , ajouter au moins une etape intermédiaire (ex de json en java et/ou soit transformer .fact en majuscule , ).
        /*
        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenAccept(resp -> {
                    System.out.println("recuperation réponse asynchrone / interpreted by " + Thread.currentThread().getName());
                    System.out.println("reponse status:" + resp.statusCode());
                    System.out.println("reponse uri:" + resp.uri().toString());
                    System.out.println("reponse type:" + resp.headers().map().get("Content-Type"));
                    System.out.println("reponse text size:" + resp.body().length());
                    System.out.println("response body:" + resp.body());
                })
                .join(); // pour attendre la fin de l'execution asynchrone avant de terminer le main
                *
         */

        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(resp -> {
                    System.out.println("recuperation réponse asynchrone / interpreted by " + Thread.currentThread().getName());
                    System.out.println("reponse status:" + resp.statusCode());
                    System.out.println("reponse uri:" + resp.uri().toString());
                    System.out.println("reponse type:" + resp.headers().map().get("Content-Type"));
                    System.out.println("reponse text size:" + resp.body().length());
                    System.out.println("response json body:" + resp.body());
                    return resp.body();
                })
                .thenApply(jsonBody-> { simulateLongTask("traitement long 1" , 2000); return parseCatFactJsonStringAsRecord(jsonBody);})
                .thenApply( catFactAsDto -> { simulateLongTask("traitement long 2" , 2000); return new Dto.CatFact(catFactAsDto.fact().toUpperCase(),catFactAsDto.length());})
                .thenAccept( transformedCatFact -> {System.out.println("catFct (uppercase):" + transformedCatFact);} )
                .join();
    }
}
