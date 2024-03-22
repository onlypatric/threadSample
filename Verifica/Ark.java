public class Ark extends Thread {
    public int conta1;
    public int conta0;
    int[][] mx;
    int[] vet;

    public Ark(String nome, int[][] mx, int[] vet) {
        super(nome);
        this.mx = mx;
        this.vet = vet;
    }

    // metodo run eseguito dai 3 thread ed in base al nome eseguino un ramo dell'if
    public void run() {
        int conta0 = 0;
        int conta1 = 0;
        if (Thread.currentThread().getName().equals("ark1")) {
            for (int i = 0; i < mx.length; i++)
                for (int j = 0; j < mx[0].length; j++)
                    if (mx[i][j] == 1)
                        conta1++;
            vet[0] = conta1;
        } else if (Thread.currentThread().getName().equals("ark2")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
            for (int i = 0; i < mx.length; i++)
                for (int j = 0; j < mx[0].length; j++) 
                    if (mx[i][j] == 0)
                        conta0++;
            vet[1] = conta0;
        } else {
            System.out.println("CELLA 0 " + vet[0]);
            System.out.println("CELLA 1 " + vet[1]);
        }
    }
}
