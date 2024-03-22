import java.util.Random;
public class MainApp {
    public static int[][] mat = new int[10][10];
    public static int[] vet = new int[2];

    public static void main(String[] args) {
        Random r = new Random();
        Thread a1 = new Ark("ark1", mat, vet);
        Thread a2 = new Ark("ark2", mat, vet);
        Thread a3 = new Ark("ark3", mat, vet);
        boolean ris;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                ris = r.nextBoolean();
                if (ris)
                    mat[i][j] = 1;
                else mat[i][j] = 0;
            }
        a1.start();
        a2.start();
        try {
            a1.join();
            a2.join();
        } catch (InterruptedException e) {
        }
        a3.start();
        try {
            a3.join();
        } catch (InterruptedException e) {
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                System.out.print(" " + mat[i][j]);
            System.out.println();
        }
    }
}
