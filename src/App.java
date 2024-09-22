import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(1000)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClient(clientSocket);
                }
            }
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String inputLine = bufferedReader.readLine();

            RequestLine requestLine = RequestLine.parseRequestLine(inputLine);

            System.out.println(requestLine.method);

            OutputStream outputStream = clientSocket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            Instant instant = Instant.now();
            ZoneId z = ZoneId.of("America/Montreal");
            ZonedDateTime zdt = instant.atZone(z);

            bufferedWriter.write("HTTP/1.1 200 OK\r\n" + "Date: " + zdt);

            clientSocket.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
