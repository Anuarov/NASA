package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod?api_key=uENqDahRV1tgmpAzsxUZClNEJmBjbtGm1g8PdJng"
                +"&date=2024-07-21";

        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = client.execute(request);

        ObjectMapper mapper = new ObjectMapper();
        NASAAnswer answer = mapper.readValue(response.getEntity().getContent(), NASAAnswer.class);

        String imageUrl = answer.url;
        String[] urlSeperated = imageUrl.split("/");
        String fileName = urlSeperated[urlSeperated.length-1];

        HttpGet imageRequest = new HttpGet(answer.url);
        CloseableHttpResponse image = client.execute(imageRequest);

        FileOutputStream fos = new FileOutputStream("Nasa/"+fileName);
        image.getEntity().writeTo(fos);

//        Scanner scan = new Scanner(response.getEntity().getContent());
//        String imageInfo = scan.nextLine();
//        System.out.println(imageInfo);
    }
}