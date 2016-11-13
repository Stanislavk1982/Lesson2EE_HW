package com.stas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class ServerCustomSocket {

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            ExecutorService service = Executors.newFixedThreadPool(2);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Future<String> submit = service.submit(new ConnectFromClient(clientSocket));
            }
        }
    }
}

class ConnectFromClient implements Callable<String> {
    Socket clientSocket;
    private int i = 0;

    public ConnectFromClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String call() throws Exception {
        BufferedReader reader = null;
        PrintWriter printWriter = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            printWriter = new PrintWriter(clientSocket.getOutputStream());
            printWriter.println("hello from server");
            printWriter.flush();
            String msqFromClient;
            while (!"stop".equals(msqFromClient = reader.readLine())) {
                System.out.println("msq from client= " + msqFromClient);
                String serverMsq = String.valueOf(i++);
                printWriter.println(serverMsq);
                printWriter.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Thread.currentThread().getName();
    }


}