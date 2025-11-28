package cliente;

import java.io.*;
import java.net.*;

public class ClienteChat {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORTA);
            System.out.println("✓ Conectado ao servidor de chat!\n");

            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            BufferedReader teclado = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            Thread receptorThread = new Thread(() -> {
                try {
                    String msgServidor;
                    while ((msgServidor = entrada.readLine()) != null) {
                        System.out.println(msgServidor);
                    }
                } catch (IOException e) {
                    System.out.println("\n✗ Conexão com servidor encerrada.");
                }
            });
            receptorThread.setDaemon(true);
            receptorThread.start();

            String mensagem;
            while ((mensagem = teclado.readLine()) != null) {
                saida.println(mensagem);
                if (mensagem.trim().equals("/sair")) {
                    break;
                }
            }

            socket.close();
            System.out.println("\n✓ Desconectado do servidor");

        } catch (IOException e) {
            System.err.println("✗ Erro ao conectar ao servidor: " + e.getMessage());
            System.err.println("Certifique-se de que o servidor está rodando.");
        }
    }
}
