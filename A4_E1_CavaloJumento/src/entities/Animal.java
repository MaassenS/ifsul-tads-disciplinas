package entities;

import java.util.Random;

public class Animal implements Runnable {

    private final String nome;
    private final int pausaMs;
    private int distancia = 0;
    private long tempoChegada = 0;
    private static final int META = 100;
    private static final Random random = new Random();

    public Animal(String nome, int pausaMs) {
        this.nome = nome;
        this.pausaMs = pausaMs;
    }
    @Override
    public void run() {
        long inicioThread = System.currentTimeMillis();

        while (distancia < META && !Thread.currentThread().isInterrupted()) {

            // Avançar em passo aleatório (1-10 metros)
            int passo = random.nextInt(10) + 1;
            distancia += passo;

            // Limitar à meta
            if (distancia > META) {
                distancia = META;
            }

            // Exibir progresso
            exibirProgresso();

            // Verificar se chegou à meta
            if (distancia >= META) {
                tempoChegada = System.currentTimeMillis() - inicioThread;
                System.out.println(nome + " completou a corrida!");
                break;
            }

            // Pausa antes do próximo passo
            try {
                Thread.sleep(pausaMs);
            } catch (InterruptedException e) {
                System.out.println("\n" + nome + " foi interrompido!");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void exibirProgresso() {
        int barras = distancia / 5;  // Cada barra = 5 metros
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(": ");

        for (int i = 0; i < 20; i++) {
            sb.append(i < barras ? "=" : "-");
        }
        sb.append("> ").append(distancia).append("m");

        System.out.println(sb);
    }

    public long getTempoChegada() {
        return tempoChegada;
    }

    public String getNome() {
        return nome;
    }
}

