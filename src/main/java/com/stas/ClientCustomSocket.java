package com.stas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientCustomSocket {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String msqFromServer;

        try (Socket clientSocket = new Socket("localhost", 9999)) {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

            while (!"stop".equals(msqFromServer = reader.readLine())) {
                System.out.println("msq from server= " + msqFromServer);
                String serverMsq = scanner.nextLine();
                printWriter.println(serverMsq);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

