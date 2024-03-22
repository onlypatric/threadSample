import java.util.Random;

/**
 * MainApp - verifica sui Thread e condivisione di dati sincronizzati
 * 
 * contiene una matrice 10x10, che avviando il metodo main verrà riempita di valori random binari (0/1), verrà analizzata da 2 thread,
 * e i risultati verranno mostrati da un terzo thread, la matrice verrà stampata una volta finita l'analisi dal thread padre (main)
 * 
 * @author Patric Pintescul
 * @version 22/03/2024
 */
public class MainApp {

    public static void main(String[] args) {
        int[][] mx = new int[10][10]; // matrice 10x10 di cifre binarie (1/0)

        // NOTA: array condiviso con ark1, ark2 per salvare i risultati
        int[] ris = new int[2]; // index 1 per contatore di "1" nella matrice mx; index 2 per contatore di "0" nella matrice mx

        // riempi matrice
        riempiMatrice(mx);
        Thread ark1 = new Thread(()->{
            for (int[] righe : mx) 
                for(int cella:righe)
                    ris[0]+= cella; // se la cella vale 1 o 0 non ha senso chiamare un "if(cella==1)", 
                    // sommando 0 ris[0] sarà identico all'antecedente valore della cella
        });
        Thread ark2 = new Thread(() -> {
            try {
                Thread.sleep(2000); // per simulare che l'ark2 attende 2 secondi prima di calcolare i risultati della matrice mx
            } catch (InterruptedException e) {} // se arriva un eccezione non viene in alcun modo trattata, viene semplicemente ignorata
            for (int[] righe : mx)
                for (int cella : righe)
                    if(cella==0)ris[1]++; // qui bisogna chiamare ++ perché non vale lo stesso ragionamento di "ark1"
        });
        Thread ark3 = new Thread(()->{
            System.out.printf("Occorrenze di 1: %d\nOccorrenze di 0: %d\n",ris[0],ris[1]);
        });
        // avviamo i thread analizzatori
        ark1.start();
        ark2.start();

        try {
            // attendiamo l'esecuzione dei thread analizzatori
            ark1.join();
            ark2.join();
        } catch (InterruptedException e) {
            System.out.println("Non è possibile unire i Thread al processo padre per attendere la fine dell'esecuzione, il programma attenderà qualche secondo in risposta ausiliaria a questa eccezione");
            try {Thread.sleep(2000);} catch (InterruptedException e1) {}
        }
        ark3.start(); // avviare il Thread ark3 dopo aver terminato l'esecuzione dei Thread ark1 e ark2, in modo da non avere problemi di mancato completamento dell'analisi
        try {ark3.join();} catch (InterruptedException e) {} // attendiamo l'esecuzione di ark3

        stampaMatrice(mx);
    }
    public static void riempiMatrice(int[][] mx){
        Random rnd = new Random(); // per generare boolean randomici (1/0)
        
        for (int i = 0; i < mx.length; i++)
            for (int j = 0; j < mx[0].length; j++)
                mx[i][j] = rnd.nextBoolean()?1:0; // equivale a scrivere "if(rnd.nextBoolean()) mx[i][j]=1; else mx[i][j]=0;"
    }
    public static void stampaMatrice(int[][] mx){

        // stampiamo tutta la matrice generata:
        System.out.println("Matrice generata:");

        // sezione stampa numero di colonna
        System.out.print("|");
        for (int i = 0; i < mx.length; i++) {
            System.out.printf("%2d|", i + 1);
        }
        System.out.println("\n" + "-".repeat(mx.length * 2 + mx.length + 1));

        // sezione stampa celle
        for (int[] riga : mx) {
            System.out.print("|");
            for (int cella : riga) {
                System.out.printf("%2d|", cella);
            }
            System.out.println();
        }
    }
}