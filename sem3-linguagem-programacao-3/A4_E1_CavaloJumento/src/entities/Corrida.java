package entities;

public class Corrida {

    public static void executar() throws InterruptedException {
        System.out.println("CORRIDA: CAVALO vs JUMENTO\n");
        System.out.println("Primeiro a atingir 100m ganha!\n");

        Animal animalCavalo = new Animal("Cavalo", 200);
        Animal animalJumento = new Animal("Jumento", 300);

        Thread cavalo = new Thread(animalCavalo, "Cavalo");
        Thread jumento = new Thread(animalJumento, "Jumento");

        long inicio = System.currentTimeMillis();

        cavalo.start();
        jumento.start();

        // Espera ate que UMA thread termine (primeiro a chegar)
        while (cavalo.isAlive() && jumento.isAlive()) {
            Thread.sleep(50); // Polling
        }

        // Identifica quem chegou primeiro e interrompe
        Thread vencedor;
        Thread perdedor;

        if (!cavalo.isAlive()) {
            vencedor = cavalo;
            perdedor = jumento;
            System.out.println("\nCavalo chegou primeiro! Aguardando Jumento completar...\n");
        } else {
            vencedor = jumento;
            perdedor = cavalo;
            System.out.println("\nJumento chegou primeiro! Aguardando Cavalo completar...\n");
        }

        // para de exibir progresso do bixo q venceu
        vencedor.interrupt();

        // perdedor completa a corrida
        perdedor.join();

        long fim = System.currentTimeMillis();

        // Resultados finais
        System.out.println("\n═════════════════════════════════");
        System.out.println("RESULTADOS FINAIS:");
        System.out.println("═════════════════════════════════");
        System.out.printf("%-10s - Tempo: %dms%n", animalCavalo.getNome(), animalCavalo.getTempoChegada());
        System.out.printf("%-10s - Tempo: %dms%n", animalJumento.getNome(), animalJumento.getTempoChegada());
        System.out.println("═════════════════════════════════");

        // Determinar vencedor pelo tempo
        if (animalCavalo.getTempoChegada() < animalJumento.getTempoChegada()) {
            System.out.println("🏆 CAVALO VENCEU! 🏆");
        } else {
            System.out.println("🏆 JUMENTO VENCEU! 🏆");
        }

        System.out.println("\nTempo total da corrida: " + (fim - inicio) + "ms");
    }
}
