package main;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class ApiConversor {
    private static final String API_KEY = cargarApiKey();

    private static String cargarApiKey() {
        try {
            Properties props = new Properties();
            // Carga el archivo desde la carpeta resources marcada como Resources Root
            InputStream input = ApiConversor.class.getClassLoader().getResourceAsStream("config.properties");

            if (input == null) {
                throw new IOException("Archivo config.properties no encontrado en resources.");
            }

            props.load(input);
            return props.getProperty("API_KEY");
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la API_KEY desde config.properties", e);
        }
    }

    public static double obtenerTasa(String origen, String destino)
            throws IOException, InterruptedException {

        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + origen;

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(respuesta.body()).getAsJsonObject();

        if (!json.has("conversion_rates")) {
            throw new IOException("Error en la respuesta de la API: " + respuesta.body());
        }

        JsonObject tasas = json.getAsJsonObject("conversion_rates");

        if (!tasas.has(destino)) {
            throw new IOException("No se encontró la tasa para la moneda destino: " + destino);
        }

        return tasas.get(destino).getAsDouble();
    }
}
