import com.google.gson.Gson;
import models.Adress;
import models.AdressRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    private static String formatOfResponse = "json";

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        welcomeMessage();

        boolean continueLoop = true;
        FileWriter writer = new FileWriter("endereco.json");

        while (continueLoop) {
            String optionSelected = displayMenu();

            if (optionSelected.equalsIgnoreCase("sair")) {
                continueLoop = false;
                break;
            }

            if (!(optionSelected.getBytes(StandardCharsets.UTF_8).length == 8)) {
                System.out.println("Check if the CEP number is correct");
                exit(1);
            }

            Adress adress = getResponseFromJSON(optionSelected);
            writer.write(String.valueOf(adress));

        }

        writer.close();
    }

    private static Adress getResponseFromJSON(String CEP) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://viacep.com.br/ws/" + CEP + "/" + formatOfResponse + "/")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Gson gson = new Gson();
        AdressRecord adressRecord = gson.fromJson(response.body(), AdressRecord.class);
        return new Adress(adressRecord);
    }

    private static String displayMenu() {
        System.out.print("""
                Digite o CEP para buscar o endereço: """);
        Scanner scanner = new Scanner(System.in);
        String CEP = scanner.nextLine();
        return CEP;
    }

    private static void welcomeMessage() {
        System.out.print("""
                Bem vindo ao buscador de endereço
                """);
    }
}