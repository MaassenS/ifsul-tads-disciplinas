package application;

import entities.Corrida;

public class Main {
    public static void main(String[] args) {
        try {
            Corrida.executar();
        } catch (InterruptedException e) {
            System.err.println("Corrida interrompida: " + e.getMessage());
        }
    }
}
