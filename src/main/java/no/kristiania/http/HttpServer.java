package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {


    private final ServerSocket serverSocket;
    private Path rootDirectory;
    private List<String> roles = new ArrayList<>();

    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);

        new Thread(this::handleClients).start();
    }

    //handle more clients
    private void handleClients() {
        try {
            while (true) {
                handleClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //handle more clients stengt. Har extracted metoden under

    private void handleClient() throws IOException {
        Socket clientSocket = serverSocket.accept();

        String[] requestLine = HttpClient.readLine(clientSocket).split(" ");
        String requestTarget = requestLine[1];

        int questionPos = requestTarget.indexOf('?');
        String fileTarget;
        String query = null;
        if (questionPos != -1) {
            fileTarget = requestTarget.substring(0, questionPos);
            query = requestTarget.substring(questionPos+1);
        } else {
            fileTarget = requestTarget;
        }

        if (fileTarget.equals("/hello")) {
            String yourName = "world";
            if (query != null){
                yourName = query.split("=")[1];
            }
            String responseText = "<p>Hello " + yourName + "</p>";

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + responseText.length() + "\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    responseText;
            clientSocket.getOutputStream().write(response.getBytes());
    } else {
        if (rootDirectory != null && Files.exists(rootDirectory.resolve(fileTarget.substring(1)))) {
            String responseText = Files.readString(rootDirectory.resolve(fileTarget.substring(1)));

            String contentType = "text/plain";
            if(requestTarget.endsWith(".html")) {
                contentType = "text/html";
            }
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + responseText.length() + "\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    responseText;
            clientSocket.getOutputStream().write(response.getBytes());
            return;
        }



        String responseText = "File not found: " + requestTarget;

        String response = "HTTP/1.1 404 Not found\r\n" +
                "Content-Length: " + responseText.length() + "\r\n" +
                "\r\n" +
                responseText;
        clientSocket.getOutputStream().write(response.getBytes());
    }
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(1997); //localhost:1997/hello
        httpServer.setRoot(Paths.get("."));
        ////Sette opp en webserver
        ////Kommenterer ut dette siden jeg har lagd en serverSocket aka website.
//        ServerSocket serverSocket = new ServerSocket(1997);
//
//        Socket clientSocket = serverSocket.accept();
//
//        String html = "Hello World";
//        String contentType = "text/html";
//
//        String response = "HTTP/1.1 200 Det gikk bra aka OK fra sia\r\n" +
//                "Content-Length: " + html.getBytes().length + "\r\n" +
//                "Content-Type: " + contentType + "\r\n" +
//                "Connection: close\r\n" +
//                "\r\n" +
//                html;

        //clientSocket.getOutputStream().write(response.getBytes());
        ////Kommenterer ut dette siden jeg har lagd en serverSocket.
        ////Sette opp en webserver finito

//        ServerSocket serverSocket = new ServerSocket(8080); //Localhost:8080
//
//        Socket clientSocket = serverSocket.accept();
//
//
//        String requestLine = HttpClient.readLine(clientSocket);
//
//        System.out.println(requestLine);
//
//        String headerLine;
//        while (!(headerLine = HttpClient.readLine(clientSocket)).isBlank()) {
//            System.out.println(headerLine);
//        } //18-20 Skriver ut headerLinen til porten.
//
//        String response = "<h1>Hello world</h1>";
//
//        clientSocket.getOutputStream().write((
//                "HTTP/1.1 200 OK\r\n" +
//                        "Content-Length: " + response.length() + "\r\n" +
//                        "Connection: close \r\n" +
//                        "\r\n" +
//                        response
//        ).getBytes());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setRoot(Path rootDirectory) {

        this.rootDirectory = rootDirectory;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
