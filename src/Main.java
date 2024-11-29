import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Lebre> lebres = new ArrayList<>();
        List<Lebre> classificacao = Collections.synchronizedList(new ArrayList<>());

        for (int i = 1; i <= 5; i++) {
            lebres.add(new Lebre("Lebre" + i, classificacao));
        }

        System.out.println("Iniciando corrida!");

        for (Lebre lebre : lebres) {
            lebre.start();
        }

        for (Lebre lebre : lebres) {
            lebre.join();
        }

        System.out.println("\n----------------------------------------------------\n");

        for (int i = 0; i < classificacao.size(); i++) {
            System.out.printf("A %s ficou em %dº lugar percorrendo a distância %d metros em %d saltos%n",
                    classificacao.get(i).getName(), i + 1, classificacao.get(i).getDistanciaPercorrida(), classificacao.get(i).getSaltos());
        }
    }
}

class Lebre extends Thread {
    private int distanciaPercorrida;
    private int saltos;
    private final List<Lebre> classificacao;

    public Lebre(String nome, List<Lebre> classificacao) {
        super(nome);
        this.classificacao = classificacao;
    }

    public int getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public int getSaltos() {
        return saltos;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (distanciaPercorrida < 20) {
            int salto = random.nextInt(3) + 1;
            distanciaPercorrida += salto;
            saltos++;
            System.out.printf("%s saltou %d metros! (Total: %d metros)%n", getName(), salto, distanciaPercorrida);

            try {
                Thread.sleep(2000); // Descansa 2 segundos
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }

        synchronized (classificacao) {
            classificacao.add(this);
        }

    }
}
