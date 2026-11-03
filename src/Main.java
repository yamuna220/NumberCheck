import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0
        );

        server.createContext("/", exchange -> {
            String result = "";

            if ("POST".equals(exchange.getRequestMethod())) {
                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                );

                String value = URLDecoder.decode(
                        body.split("=")[1],
                        StandardCharsets.UTF_8
                );

                try {
                    int num = Integer.parseInt(value);

                    if (num > 0)
                        result = "Positive Number";
                    else if (num < 0)
                        result = "Negative Number";
                    else
                        result = "Zero";

                } catch (NumberFormatException e) {
                    result = "Please enter a valid integer";
                }
            }

            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Number Checker</title>
                    <style>
                        body {
                            font-family: Arial;
                            background: #f2f4f8;
                            text-align: center;
                            padding-top: 100px;
                        }
                        .container {
                            background: white;
                            padding: 35px;
                            width: 350px;
                            margin: auto;
                            border-radius: 12px;
                        }
                        input, button {
                            padding: 12px;
                            margin: 10px;
                            width: 80%;
                        }
                        button {
                            background: #2457a7;
                            color: white;
                            border: none;
                            cursor: pointer;
                        }
                        h3 { color: #2457a7; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Number Checker</h2>
                        <form method="POST">
                            <input type="number" name="number"
                                   placeholder="Enter an integer" required>
                            <br>
                            <button type="submit">Check Number</button>
                        </form>
                        <h3>%s</h3>
                    </div>
                </body>
                </html>
                """.formatted(result);

            byte[] response = html.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                    "Content-Type", "text/html; charset=UTF-8"
            );
            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        server.start();
        System.out.println("Server running at http://localhost:8081");
    }
}