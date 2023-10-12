package ru.mirea.orlov.httpcats;
import java.io.*;
import java.net.http.*;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class HttpCatClient {
    public static void main(String[] args) throws IOException,
            InterruptedException {
        File dir = new File("cats");
        if (!dir.exists()) {
            dir.mkdir();
        }
        HttpClient client = HttpClient.newHttpClient();
        ArrayList<CompletableFuture> futures = new ArrayList();
        for (int i = 0; i < 600; i++) {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create("https://http.cat/" + i))
                    .GET()
                    .build();
            System.out.println("Send request to https://http.cat/" + i);
            int finalI = i;
            CompletableFuture ft = client.sendAsync(request,
                            HttpResponse.BodyHandlers.ofByteArray())
                    .thenApply(Function.identity())
                    .thenAccept(response -> saveToFile(response, finalI));
            Thread.sleep(100);
            futures.add(ft);
        }
        System.out.println("Wait for end of jobs");
        futures.forEach(CompletableFuture::join);
        Thread.sleep(5000);
    }
    private static void saveToFile(HttpResponse<byte[]> response, int i) {
        if (response.statusCode() == 404) {
            return;
        }
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream("cats/cat_" + i + ".jpg")
            );
            outputStream.write(response.body());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
