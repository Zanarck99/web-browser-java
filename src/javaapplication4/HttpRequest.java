package javaapplication4;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.net.Socket;
import java.net.URL;

public class HttpRequest {
    /*
     * Faz uma requisição GET para uma URL e retorna a resposta.
     * Lida com redirecionamentos HTTP 301 e 302.
     * 
     * @param url URL para a qual a requisição será feita
     * 
     * @return Resposta da requisição
     */
    public static HttpResponse makeRequestPage(URL url) throws IOException, UnknownHostException {
        if (url.getProtocol().equals("https")) {
            url = new URL("http://" + url.getHost() + url.getPath()); // Tenta forçar o protocolo HTTP
        }

        // Resolve o host para um endereço IP
        InetAddress IpAddr = InetAddress.getByName(url.getHost());

        // Se a URL não tiver um path, adiciona "/" (página inicial)
        String path = url.getPath().equals("") ? "/" : url.getPath();

        // Cria socket de conexão
        int port = 80;
        Socket socket = new Socket(IpAddr, port);

        // Cria buffers de leitura e escrita para a requisição GET
        PrintWriter request = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Manda a requisição para o servidor
        request.printf("GET %s HTTP/1.1\r\n", path);
        request.printf("Host: %s\r\n", url.getHost());
        request.println("User-Agent: Java/1.0");
        request.println("Connection: Close");
        request.println();

        // Lê a resposta
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line + "\r\n");
        }

        // Guarda a resposta em um objeto HttpResponse
        HttpResponse httpResponse = new HttpResponse(url, response.toString());

        // Fecha os recursos
        socket.close();
        reader.close();
        request.close();

        // Guarda o registro da requisição
        Session.getResponseRecord()
                .append(String.format("URL:\n%s\n\nResponse Header:\n%s\n\n", url.toString(),
                        httpResponse.getHeader()));

        // Se a resposta for um redirecionamento, faz uma nova requisição
        int statusCode = httpResponse.getStatusCodeResponse();
        if (statusCode == 301 || statusCode == 302) {
            // Localiza a URL de redirecionamento na header
            int locationIndex = httpResponse.getHeader().indexOf("Location: ") + 10;
            // Extrai a URL de redirecionamento
            URL newUrl = new URL(httpResponse.getHeader().substring(locationIndex,
                    httpResponse.getHeader().indexOf("\r\n", locationIndex)));

            // Se o redirecionamento for para uma página HTTPS, retorna a resposta
            if (newUrl.getProtocol().equals("https")) {
                httpResponse.setBody("<h1>Redirecionamento para HTTPS não suportado.</h1>");

                return httpResponse;
            }

            // Faz uma nova requisição com a URL de redirecionamento
            return makeRequestPage(newUrl);
        }

        // Caso contrário, retorna a resposta
        return httpResponse;
    }

    /*
     * Polimorfismo de sobrecarga para a função padrão de requisição.
     * Normaliza a URL e chama a função principal.
     * 
     * @param url URL para a qual a requisição será feita
     * 
     * @return Resposta da requisição
     */
    public static HttpResponse makeRequestPage(String url) throws IOException, UnknownHostException {
        URL urlObj;

        // Adiciona o protocolo HTTP caso não esteja presente
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            urlObj = new URL("http://" + url);
        }

        // Chama a função principal
        return makeRequestPage(urlObj);
    }
}
