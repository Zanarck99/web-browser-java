package javaapplication4;

import java.net.URL;

/*
 * Representa uma resposta HTTP.
 */
public class HttpResponse {
    // Atributos
    private URL url;
    private String header, body;

    /*
     * Construtor.
     * 
     * @param url URL para a qual a requisição foi feita
     * @param response Resposta HTTP
     * 
     * @return Objeto HttpResponse
     */
    public HttpResponse(URL url, String response) {
        this.url = url;

        // Separa o cabeçalho do corpo da resposta
        String[] responseParts = response.split("\r\n\r\n", 2);

        this.header = responseParts[0];

        // Se houver corpo na resposta, guarda-o
        if (responseParts.length > 1) {
            this.body = responseParts[1];
        } else {
            this.body = "<h1>Resposta sem corpo</h1>";
        }
    }

    /*
     * Retorna o código de status da resposta.
     * 
     * @return Código de status da resposta
     */
    public int getStatusCodeResponse() {
        // O código de status está na primeira linha do cabeçalho, a partir do 9º caractere
        return Integer.parseInt(this.header.split("\r\n")[0].substring(9, 12));
    }

    // Getters E Setters
    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
