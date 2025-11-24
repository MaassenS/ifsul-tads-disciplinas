package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClienteServidor implements Runnable {
    private final Socket socket;
    private final List<ClienteServidor> clientes;
    private PrintWriter saida;
    private String nome;

    public ClienteServidor(Socket socket, List<ClienteServidor> clientes) {
        this.socket = socket;
        this.clientes = clientes;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            saida = new PrintWriter(socket.getOutputStream(), true);

            saida.println("=== BEM-VINDO AO CHAT ===");
            saida.println("Digite seu nome:");
            nome = entrada.readLine();

            if (nome == null || nome.trim().isEmpty()) {
                nome = "Anônimo_" + socket.getPort();
            }

            broadcast("✓ [" + nome + "] entrou no chat");

            String mensagem;
            while ((mensagem = entrada.readLine()) != null) {
                if (mensagem.startsWith("/")) {
                    processarComando(mensagem);
                    if (mensagem.equals("/sair")) {
                        break;
                    }
                } else if (!mensagem.trim().isEmpty()) {
                    broadcast("[" + nome + "]: " + mensagem);
                }
            }
        } catch (IOException e) {
            System.err.println("✗ Erro com cliente " + (nome != null ? nome : "desconhecido") + ": " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    private void desconectar() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar socket: " + e.getMessage());
        }
        synchronized (clientes) {
            clientes.remove(this);
        }
        if (nome != null) {
            broadcast("✗ [" + nome + "] saiu do chat");
        }
    }

    private void processarComando(String comando) {
        if (comando.equals("/listar")) {
            saida.println("\n=== USUÁRIOS CONECTADOS ===");
            synchronized (clientes) {
                for (ClienteServidor c : clientes) {
                    saida.println("  🟢 " + c.nome);
                }
            }
            saida.println("===========================\n");
        } else if (comando.equals("/sair")) {
            saida.println("👋 Até logo, " + nome + "!");
        } else {
            saida.println("⚠️ Comando não reconhecido. Comandos: /listar, /sair");
        }
    }

    private void broadcast(String mensagem) {
        System.out.println(mensagem);
        synchronized (clientes) {
            for (ClienteServidor c : clientes) {
                if (c.saida != null) {
                    c.saida.println(mensagem);
                }
            }
        }
    }
}
