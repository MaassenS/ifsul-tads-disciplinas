package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorChat {
    private static final int PORTA = 12345;
    private static final List<ClienteServidor> clientes = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   SERVIDOR DE CHAT INICIADO");
        System.out.println("=================================");
        System.out.println("Aguardando conexões na porta " + PORTA + "...\n");

        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            while (true) {
                try {
                    Socket socketCliente = servidor.accept();
                    String ipCliente = socketCliente.getInetAddress().getHostAddress();
                    System.out.println("✓ Nova conexão recebida: " + ipCliente);

                    ClienteServidor cliente = new ClienteServidor(socketCliente, clientes);
                    synchronized (clientes) {
                        clientes.add(cliente);
                    }

                    Thread thread = new Thread(cliente);
                    thread.start();
                } catch (IOException e) {
                    System.err.println("Erro ao aceitar cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("✗ Erro ao iniciar servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
