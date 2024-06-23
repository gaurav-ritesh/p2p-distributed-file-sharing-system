import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class P2PFileSharingSystem {
    private static final int SERVER_PORT = 9090;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        // Start the server to listen for incoming connections
        executorService.submit(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                System.out.println("Server started. Waiting for incoming connections...");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    executorService.submit(() -> handleClient(clientSocket));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Create peers and share files
        Peer peer1 = new Peer("localhost", 8001);
        Peer peer2 = new Peer("localhost", 8002);
        peer1.shareFile("path/to/file1.txt");
        peer2.shareFile("path/to/file2.txt");

        // Download files from peers
        peer1.downloadFile("localhost", 8002, "file2.txt", "downloaded_files/");
        peer2.downloadFile("localhost", 8001, "file1.txt", "downloaded_files/");
    }

    // Handle incoming client connections for file transfer
    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String fileName = reader.readLine();
            String filePath = "shared_files/" + fileName;
            sendFile(clientSocket, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send the requested file to the client
    private static void sendFile(Socket clientSocket, String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             OutputStream outputStream = clientSocket.getOutputStream()) {

            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(new File(filePath).getName());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File sent successfully to " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Peer {
    private final String ipAddress;
    private final int port;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Peer(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    // Share a file with the peer network
    public void shareFile(String filePath) {
        executorService.submit(() -> {
            try (Socket socket = new Socket(ipAddress, port);
                 OutputStream outputStream = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(outputStream, true)) {

                writer.println(new File(filePath).getName());

                try (FileInputStream fileInputStream = new FileInputStream(filePath);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("File shared with " + ipAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Download a file from a remote peer
    public void downloadFile(String remotePeerIP, int remotePeerPort, String fileName, String savePath) {
        executorService.submit(() -> {
            try (Socket socket = new Socket(remotePeerIP, remotePeerPort);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 FileOutputStream fileOutputStream = new FileOutputStream(savePath + fileName);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(fileName);

                String receivedFileName = reader.readLine();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = socket.getInputStream().read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("File '" + receivedFileName + "' downloaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
