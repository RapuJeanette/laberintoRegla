/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laberintoregla;

import java.util.LinkedList;

/**
 *
 * @author Janet
 */
public class LaberintoRegla {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int a = 3, b = 3;
        int m[][] = new int[a][b];
        laberinto_Torres(m, 0, 0, a - 1, b - 1, 1);
        System.out.println("Soluciones: " + c);
    }

    public static int c = 0;

    public static boolean posValida(int m[][], int i, int j) {
        return i >= 0 && i < m.length
                && j >= 0 && j < m[i].length && m[i][j] == 0;
    }

    public static void mostrar(int m[][]) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static Regla elegirReglaA(LinkedList<Regla> L1, int m[][], int ifin, int jfin) {
        return L1.removeFirst();
    }

    public static void laberinto(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1) {
            mostrar(m);
        }
        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto(m, R.fil, R.col, i1, j1,
                    paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static int distancia(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2)
                + Math.pow(y1 - y2, 2));
    }

    public static LinkedList<Regla> reglasAplicables(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        if (posValida(m, i, j - 1)) {
            L1.add(new Regla(i, j - 1));
        }
        if (posValida(m, i - 1, j)) {
            L1.add(new Regla(i - 1, j));
        }
        if (posValida(m, i, j + 1)) {
            L1.add(new Regla(i, j + 1));
        }
        if (posValida(m, i + 1, j)) {
            L1.add(new Regla(i + 1, j));
        }
        return L1;
    }

    private static int[][] copiarMatriz(int[][] m) {
        int m2[][] = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m2[i][j] = m[i][j];
            }
        }
        return m2;
    }

    public static Regla elegirReglaB(LinkedList<Regla> L1, int m[][], int ifin, int jfin) {
        int men = Integer.MAX_VALUE, posMen = 0;
        for (int i = 0; i < L1.size(); i++) {
            Regla regla = L1.get(i);
            int dist = distancia(regla.fil, regla.col, ifin,
                    jfin);
            if (dist < men) {
                men = dist;
                posMen = i;
            }
        }
        return L1.remove(posMen);
    }

    public static void laberintoB(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberintoB(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static boolean todasVisitadas(int m[][]) {
        boolean res = true;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == 0) {
                    res = false;
                }
            }
        }
        return res;
    }

    public static void laberintoC(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberintoC(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static void ejercicio1D(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int mayor = 0;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        mayor = laberintoMayor(ListMatrix, m, i, j, ifin,
                jfin, paso, mayor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == mayor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    private static int laberintoMayor(LinkedList<int[][]> ListMatrix, int[][] m, int i, int j, int ifin, int jfin,
            int paso, int mayor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso > mayor) {
                mayor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, ifin, jfin);
            mayor = laberintoMayor(ListMatrix, m, R.fil,
                    R.col, ifin, jfin, paso + 1, mayor);
            m[R.fil][R.col] = 0;
        }
        return mayor;
    }

    public static void ejercicio1E(int m[][], int i, int j,
            int ifin, int jfin, int paso) {
        int menor = Integer.MAX_VALUE;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        menor = laberintoMenor(ListMatrix, m, i, j, ifin,
                jfin, paso, menor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == menor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    public static int laberintoMenor(LinkedList<int[][]> ListMatrix, int m[][], int i, int j, int ifin, int jfin,
            int paso, int menor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso < menor) {
                menor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, ifin, jfin);
            menor = laberintoMenor(ListMatrix, m, R.fil,
                    R.col, ifin, jfin, paso + 1, menor);
            m[R.fil][R.col] = 0;
        }
        return menor;
    }

    //Torres ---------------------------------------------------------------------------
    public static void laberinto_Torres(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;
        LinkedList<Regla> L1 = reglasAplicables_Torres(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Torres(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Torres(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int a = i, b = j;
        while (posValida(m, i, b - 1)) {
            b--;
            L1.add(new Regla(i, b));
        }
        j = b;
        while (posValida(m, a - 1, j)) {
            a--;
            L1.add(new Regla(a, j));
        }
        i = a;
        while (posValida(m, i, b + 1)) {
            b++;
            L1.add(new Regla(i, b));
        }
        j = b;
        while (posValida(m, a + 1, j)) {
            a++;
            L1.add(new Regla(a, j));
        }
        i = a;
        return L1;
    }
//Alfil --------------------------------------------------------------------------

    public static void laberinto_Alfil(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;
        LinkedList<Regla> L1 = reglasAplicables_Alfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Alfil(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Alfil(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int a = i, b = j;
        while (posValida(m, a + 1, b + 1)) {
            b++;
            a++;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        while (posValida(m, a - 1, b - 1)) {
            b--;
            a--;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        while (posValida(m, a - 1, b + 1)) {
            b++;
            a--;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        while (posValida(m, a + 1, b - 1)) {
            b--;
            a++;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        return L1;
    }
//Dama ----------------------------------------------------------------------------

    public static void laberinto_Dama(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;
        LinkedList<Regla> L1 = reglasAplicables_Dama(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Dama(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Dama(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        L1 = reglasAplicables_Alfil(m, i, j);
        L1 = reglasAplicables_Torres(m, i, j);
        return L1;
    }
// b----------------------------------------------------------------------------
    //Torre -----------------------------------------------------------------------------

    public static void torreB(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            torreB(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesTorre(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int k = 1;
        while (posValida(m, i, j - k)) {
            L1.add(new Regla(i, j - k));
            k++;
        }
        k = 1;
        while (posValida(m, i - k, j)) {
            L1.add(new Regla(i - k, j));
            k++;
        }
        k = 1;
        while (posValida(m, i, j + k)) {
            L1.add(new Regla(i, j + k));
            k++;
        }
        k = 1;
        while (posValida(m, i + k, j)) {
            L1.add(new Regla(i + k, j));
            k++;
        }
        return L1;
    }
//Alfil ------------------------------------------------------------------------------

    public static void alfilB(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            alfilB(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    /*   public static LinkedList<Regla> reglasAplicablesAlfil(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int a = 1;
        int b = 1;
        while (posValida(m, i - a, j - b)) {
            L1.add(new Regla(i - a, j - b));
            a++;
            b++;
        }
        a = 1;
        b = 1;
        while (posValida(m, i - a, j + b)) {
            L1.add(new Regla(i - a, j + b));
            a++;
            b++;
        }
        a = 1;
        b = 1;
        while (posValida(m, i + a, j + b)) {
            L1.add(new Regla(i + a, j + b));
            a++;
            b++;
        }
        a = 1;
        b = 1;
        while (posValida(m, i + a, j - b)) {
            L1.add(new Regla(i + a, j - b));
            a++;
            b++;
        }
        return L1;
    }*/
//Dama ----------------------------------------------------------------------------
    public static void damaB(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesDama(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            damaB(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesDama(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int a = 1;
        int b = 1;
        while (posValida(m, i, j - b)) {
            L1.add(new Regla(i, j - b));
            b++;
        }
        b = 1;
        while (posValida(m, i - a, j - b)) {
            L1.add(new Regla(i - a, j - b));
            a++;
            b++;
        }
        a = 1;
        while (posValida(m, i - a, j)) {
            L1.add(new Regla(i - a, j));
            a++;
        }
        a = 1;
        b = 1;
        while (posValida(m, i - a, j + b)) {
            L1.add(new Regla(i - a, j + b));
            a++;
            b++;
        }
        b = 1;
        while (posValida(m, i, j + b)) {
            L1.add(new Regla(i, j + b));
            b++;
        }
        a = 1;
        b = 1;
        while (posValida(m, i + a, j + b)) {
            L1.add(new Regla(i + a, j + b));
            a++;
            b++;
        }
        a = 1;
        while (posValida(m, i + a, j)) {
            L1.add(new Regla(i + a, j));
            a++;
        }
        a = 1;
        b = 1;
        while (posValida(m, i + a, j - b)) {
            L1.add(new Regla(i + a, j - b));
            a++;
            b++;
        }
        return L1;
    }

    //c----------------------------------------------------------------
//Torre ----------------------------------------------------------------------------
    public static void laberintoTorreC(int m[][], int i, int j,
            int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberintoTorreC(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesTorr(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        return L1;
    }

//Alfil ------------------------------------------------------------------------------
    public static void laberintoAlfilC(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst(); // Cambio para obtener el primer elemento de la lista
            m[R.fil][R.col] = paso;
            laberintoAlfilC(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static int laberintoAlfil(int m[][], int i, int j, int i1, int j1, int paso, int min) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
            if (paso < min || min == 0) {
                min = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst(); // Cambio para obtener el primer elemento de la lista
            m[R.fil][R.col] = paso;
            min = laberintoAlfil(m, R.fil, R.col, i1, j1, paso + 1, min);
            m[R.fil][R.col] = 0;
        }
        return min;
    }

    public static LinkedList<Regla> reglasAplicablesAlfil(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1, l = i - 1; k < m.length && l >= 0; k++, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1, l = i + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j - 1, l = i + 1; k >= 0 && l < m[0].length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }

    public static void laberintoDamaC(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesDama(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberintoDamaC(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesDam(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1, l = i - 1; k < m.length && l >= 0; k++, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j + 1, l = i + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j - 1, l = i + 1; k >= 0 && l < m[0].length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }
    //d-------------------------------------------------------------------

    //-------------------------------------TORRE-----------------------------------------
    public static void torreD(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int mayor = 0;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        mayor = laberintoMayorTorreD(ListMatrix, m, i, j, ifin, jfin, paso, mayor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == mayor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    private static int laberintoMayorTorreD(LinkedList<int[][]> ListMatrix, int[][] m, int i, int j,
            int ifin, int jfin, int paso, int mayor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso > mayor) {
                mayor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, ifin, jfin);
            mayor = laberintoMayorTorreD(ListMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, mayor);
            m[R.fil][R.col] = 0;
        }
        return mayor;
    }

    public static LinkedList<Regla> reglasAplicablesTor(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        return L1;
    }
//------------------------ALFIL----------------------------------------------

    public static void alfilD(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int mayor = 0;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        mayor = laberintoMayorAlfilD(ListMatrix, m, i, j, ifin, jfin, paso, mayor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == mayor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    private static int laberintoMayorAlfilD(LinkedList<int[][]> ListMatrix, int[][] m, int i, int j,
            int ifin, int jfin, int paso, int mayor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso > mayor) {
                mayor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, ifin, jfin);
            mayor = laberintoMayorAlfilD(ListMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, mayor);
            m[R.fil][R.col] = 0;
        }
        return mayor;
    }

    public static LinkedList<Regla> reglasAplicablesAlfi(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1, l = j + 1; k >= 0 && l <= m.length; k--, l++) {
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = i + 1, l = j + 1; k <= m[0].length && l <= m.length; k++, l++) {
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = i + 1, l = j - 1; k <= m[0].length && l >= 0; k++, l--) {
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        return L1;
    }
//------------------------Dama----------------------------------------------

    public static void damaD(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int mayor = 0;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        mayor = laberintoMayorDamaD(ListMatrix, m, i, j, ifin, jfin, paso, mayor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == mayor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    private static int laberintoMayorDamaD(LinkedList<int[][]> ListMatrix, int[][] m, int i, int j,
            int ifin, int jfin, int paso, int mayor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso > mayor) {
                mayor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesDama(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, ifin, jfin);
            mayor = laberintoMayorDamaD(ListMatrix, m, R.fil, R.col, ifin, jfin, paso + 1,
                    mayor);
            m[R.fil][R.col] = 0;
        }
        return mayor;
    }

    public static LinkedList<Regla> reglasAplicablesDa(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        for (int k = j - 1; k >= 0; k--) { //IZQUIERDA
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) { //IZQUIERDA HACIA ARRIBA
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) { //ARRIBA
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = i - 1, l = j + 1; k >= 0 && l <= m.length; k--, l++) { //DERECHA HACIA ARIBA
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = j + 1; k < m.length; k++) {//DERECHA
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1, l = j + 1; k <= m[0].length && l <= m.length; k++, l++) { //ABAJO DERECHA
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {//ABAJO
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = i + 1, l = j - 1; k <= m[0].length && l >= 0; k++, l--) { //ABAJO IZQUIERDA
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        return L1;
    }

    //e----------------------------------------------------------
    //Torre ----------------------------------------------------------------------------
    public static void laberintoTorreMinimaLongitud(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int min = Integer.MAX_VALUE;
        LinkedList<int[][]> listMatrix = new LinkedList<>();
        int menor = laberintoTorre(listMatrix, m, i, j, ifin, jfin, paso, min);
        for (int k = 0; k < listMatrix.size(); k++) {
            if (listMatrix.get(k)[ifin][jfin] == menor) {
                c++;
                mostrar(listMatrix.get(k));
            }
        }
    }

    public static int laberintoTorre(LinkedList<int[][]> listMatrix, int m[][], int i, int j, int ifin,
            int jfin, int paso, int min) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            listMatrix.add(m2);
            if (paso < min) {
                min = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, ifin, jfin);
            min = laberintoTorre(listMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, min);
            m[R.fil][R.col] = 0;
        }
        return min;
    }

    public static LinkedList<Regla> reglasAplicablesTorres(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        return L1;
    }
//Alfil ------------------------------------------------------------------------------

    public static void laberintoAlfilMinimaLongitud(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int min = Integer.MAX_VALUE;
        LinkedList<int[][]> listMatrix = new LinkedList<>();
        int menor = laberintoAlfil(listMatrix, m, i, j, ifin, jfin, paso, min);
        for (int k = 0; k < listMatrix.size(); k++) {
            if (listMatrix.get(k)[ifin][jfin] == menor) {
                c++;
                mostrar(listMatrix.get(k));
            }
        }
    }

    public static int laberintoAlfil(LinkedList<int[][]> listMatrix, int m[][], int i, int j, int ifin, int jfin, int paso, int min) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            listMatrix.add(m2);
            if (paso < min) {
                min = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, ifin, jfin);
            min = laberintoAlfil(listMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, min);
            m[R.fil][R.col] = 0;
        }
        return min;
    }

    public static LinkedList<Regla> reglasAplicablesAlfils(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1, l = i - 1; k < m.length && l >= 0; k++, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1, l = i + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j - 1, l = i + 1; k >= 0 && l < m[0].length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }
//Dama ------------------------------------------------------------------------------

    public static void laberintoDamaMinimaLongitud(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int min = Integer.MAX_VALUE;
        LinkedList<int[][]> listMatrix = new LinkedList<>();
        int menor = laberintoDama(listMatrix, m, i, j, ifin, jfin, paso, min);
        for (int k = 0; k < listMatrix.size(); k++) {
            if (listMatrix.get(k)[ifin][jfin] == menor) {
                c++;
                mostrar(listMatrix.get(k));
            }
        }
    }

    public static int laberintoDama(LinkedList<int[][]> listMatrix, int m[][], int i, int j, int ifin,
            int jfin, int paso, int min) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            listMatrix.add(m2);
            if (paso < min) {
                min = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesDama(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, ifin, jfin);
            min = laberintoDama(listMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, min);
            m[R.fil][R.col] = 0;
        }
        return min;
    }

    public static LinkedList<Regla> reglasAplicablesDamas(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1, l = i - 1; k < m.length && l >= 0; k++, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j + 1, l = i + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j - 1, l = i + 1; k >= 0 && l < m[0].length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }

    // Torres
    public static void laberinto_Torres_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;

        LinkedList<Regla> L1 = reglasAplicables_Torres_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Torres_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Torres_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        int a = i, b = j;
        while (posValida(m, i, b + 1)) {
            b++;
            L1.add(new Regla(i, b));
        }
        j = b;
        while (posValida(m, a + 1, j)) {
            a++;
            L1.add(new Regla(a, j));
        }
        i = a;
        while (posValida(m, i, b - 1)) {
            b--;
            L1.add(new Regla(i, b));
        }
        j = b;
        while (posValida(m, a - 1, j)) {
            a--;
            L1.add(new Regla(a, j));
        }
        i = a;
        return L1;
    }

// Alfil
    public static void laberinto_Alfil_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;

        LinkedList<Regla> L1 = reglasAplicables_Alfil_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Alfil_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Alfil_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        int a = i, b = j;
        while (posValida(m, a + 1, b + 1)) {
            b++;
            a++;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        while (posValida(m, a - 1, b - 1)) {
            b--;
            a--;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        while (posValida(m, a - 1, b + 1)) {
            b++;
            a--;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        while (posValida(m, a + 1, b - 1)) {
            b--;
            a++;
            L1.add(new Regla(a, b));
        }
        j = b;
        i = a;
        return L1;
    }

// Dama
    public static void laberinto_Dama_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;

        LinkedList<Regla> L1 = reglasAplicables_Dama_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Dama_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Dama_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = reglasAplicables_Torres_Antihorario(m, i, j);
        LinkedList<Regla> L2 = reglasAplicables_Alfil_Antihorario(m, i, j);
        L1.addAll(L2);
        return L1;
    }

    // Rey
    public static void laberinto_Rey_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;

        LinkedList<Regla> L1 = reglasAplicables_Rey_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Rey_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Rey_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if (posValida(m, a, b)) {
                    L1.add(new Regla(a, b));
                }
            }
        }
        return L1;
    }
// Caballo

    public static void laberinto_Caballo_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        mostrar(m);
        c++;

        LinkedList<Regla> L1 = reglasAplicables_Caballo_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberinto_Caballo_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicables_Caballo_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        int a, b;

        a = i - 2;
        b = j - 1;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        a = i - 1;
        b = j - 2;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        a = i + 1;
        b = j - 2;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        a = i + 2;
        b = j - 1;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        a = i + 2;
        b = j + 1;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        a = i + 1;
        b = j + 2;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        a = i - 1;
        b = j + 2;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        a = i - 2;
        b = j + 1;
        if (posValida(m, a, b)) {
            L1.add(new Regla(a, b));
        }

        return L1;
    }

    //b abtihorario
    public static void caballoB_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesCaballo_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            caballoB_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesCaballo_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int[][] movimientos = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        for (int[] mov : movimientos) {
            int x = i + mov[0];
            int y = j + mov[1];
            if (posValida(m, x, y)) {
                L1.add(new Regla(x, y));
            }
        }
        return L1;
    }

    //rey
    public static void reyB_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesRey_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            reyB_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesRey_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int[][] movimientos = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] mov : movimientos) {
            int x = i + mov[0];
            int y = j + mov[1];
            if (posValida(m, x, y)) {
                L1.add(new Regla(x, y));
            }
        }
        return L1;
    }

    //c
    public static void laberintoTorreC_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesTorre_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberintoTorreC_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesTorre_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        return L1;
    }

    //alfil
    public static void laberintoAlfilC_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfil_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst();
            m[R.fil][R.col] = paso;
            laberintoAlfilC_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesAlfil_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j + 1, l = i + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j - 1, l = i + 1; k >= 0 && l < m[0].length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }

    //dama
    public static void laberintoDamaC_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesDama_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            laberintoDamaC_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesDama_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j + 1, l = i + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = i + 1, l = j - 1; k < m[0].length && l >= 0; k++, l--) {
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = i - 1, l = j + 1; k >= 0 && l < m.length; k--, l++) {
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        return L1;
    }

    //caballo
    public static void caballoC_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesCaballoC_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            caballoC_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesCaballoC_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int[][] movimientos = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        for (int[] mov : movimientos) {
            int x = i + mov[0];
            int y = j + mov[1];
            if (posValida(m, x, y)) {
                L1.add(new Regla(x, y));
            }
        }
        return L1;
    }

    //rey
    public static void reyC_Antihorario(int m[][], int i, int j, int i1, int j1, int paso) {
        m[i][j] = paso;
        if (i == i1 && j == j1 && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesReyC_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaB(L1, m, i1, j1);
            m[R.fil][R.col] = paso;
            reyC_Antihorario(m, R.fil, R.col, i1, j1, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesReyC_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int[][] movimientos = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] mov : movimientos) {
            int x = i + mov[0];
            int y = j + mov[1];
            if (posValida(m, x, y)) {
                L1.add(new Regla(x, y));
            }
        }
        return L1;
    }

    //d
    //torre
    public static void torreD_Antihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int mayor = 0;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        mayor = laberintoMayorTorreD_Antihorario(ListMatrix, m, i, j, ifin, jfin, paso, mayor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == mayor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    private static int laberintoMayorTorreD_Antihorario(LinkedList<int[][]> ListMatrix, int[][] m, int i, int j,
            int ifin, int jfin, int paso, int mayor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso > mayor) {
                mayor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesTorreD_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaA(L1, m, ifin, jfin);
            mayor = laberintoMayorTorreD_Antihorario(ListMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, mayor);
            m[R.fil][R.col] = 0;
        }
        return mayor;
    }

    public static LinkedList<Regla> reglasAplicablesTorreD_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        return L1;
    }

    //alfil
    public static void alfilD_Antihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int mayor = 0;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        mayor = laberintoMayorAlfilD_Antihorario(ListMatrix, m, i, j, ifin, jfin, paso, mayor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == mayor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    private static int laberintoMayorAlfilD_Antihorario(LinkedList<int[][]> ListMatrix, int[][] m, int i, int j,
            int ifin, int jfin, int paso, int mayor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso > mayor) {
                mayor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfilD_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst();
            mayor = laberintoMayorAlfilD_Antihorario(ListMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, mayor);
            m[R.fil][R.col] = 0;
        }
        return mayor;
    }

    public static LinkedList<Regla> reglasAplicablesAlfilD_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = i - 1, l = j - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = j - 1, l = i + 1; k >= 0 && l <= m.length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1, l = j + 1; k <= m[0].length && l <= m.length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1, l = i - 1; k <= m[0].length && l >= 0; k++, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }

    //dama
    public static void damaD_Antihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int mayor = 0;
        LinkedList<int[][]> ListMatrix = new LinkedList<>();
        mayor = laberintoMayorDamaD_Antihorario(ListMatrix, m, i, j, ifin, jfin, paso, mayor);
        for (int k = 0; k < ListMatrix.size(); k++) {
            int matriz[][] = ListMatrix.get(k);
            if (matriz[ifin][jfin] == mayor) {
                c++;
                mostrar(matriz);
            }
        }
    }

    private static int laberintoMayorDamaD_Antihorario(LinkedList<int[][]> ListMatrix, int[][] m, int i, int j,
            int ifin, int jfin, int paso, int mayor) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            ListMatrix.add(m2);
            if (paso > mayor) {
                mayor = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesDamaD_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst();
            mayor = laberintoMayorDamaD_Antihorario(ListMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, mayor);
            m[R.fil][R.col] = 0;
        }
        return mayor;
    }

    public static LinkedList<Regla> reglasAplicablesDamaD_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j - 1; k >= 0; k--) { //IZQUIERDA
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) { //IZQUIERDA HACIA ARRIBA
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) { //ARRIBA
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = i - 1, l = j + 1; k >= 0 && l <= m.length; k--, l++) { //DERECHA HACIA ARRIBA
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = j + 1; k < m.length; k++) {//DERECHA
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1, l = j + 1; k <= m[0].length && l <= m.length; k++, l++) { //ABAJO DERECHA
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {//ABAJO
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = i + 1, l = j - 1; k <= m[0].length && l >= 0; k++, l--) { //ABAJO IZQUIERDA
            if (posValida(m, k, l)) {
                L1.add(new Regla(k, l));
            }
        }
        return L1;
    }

    //caballo
    public static void caballoD_Antihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        m[i][j] = paso;
        if (i == ifin && j == jfin && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesCaballoD_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst();
            m[R.fil][R.col] = paso;
            caballoD_Antihorario(m, R.fil, R.col, ifin, jfin, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesCaballoD_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int[][] movimientos = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        for (int[] mov : movimientos) {
            int x = i + mov[0];
            int y = j + mov[1];
            if (posValida(m, x, y)) {
                L1.add(new Regla(x, y));
            }
        }
        return L1;
    }

    //rey
    public static void reyD_Antihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        m[i][j] = paso;
        if (i == ifin && j == jfin && !todasVisitadas(m)) {
            mostrar(m);
            c++;
        }
        LinkedList<Regla> L1 = reglasAplicablesReyD_Antihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst();
            m[R.fil][R.col] = paso;
            reyD_Antihorario(m, R.fil, R.col, ifin, jfin, paso + 1);
            m[R.fil][R.col] = 0;
        }
    }

    public static LinkedList<Regla> reglasAplicablesReyD_Antihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        int[][] movimientos = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] mov : movimientos) {
            int x = i + mov[0];
            int y = j + mov[1];
            if (posValida(m, x, y)) {
                L1.add(new Regla(x, y));
            }
        }
        return L1;
    }

    //e
    //torre
    public static Regla elegirReglaAntihorario(LinkedList<Regla> L1, int[][] m, int ifin, int jfin) {
        int min = Integer.MAX_VALUE;
        Regla seleccionada = null;

        for (Regla regla : L1) {
            int fila = regla.fil;
            int columna = regla.col;

            // Encuentra la regla que minimiza la distancia euclidiana hacia el destino
            int distancia = (int) Math.sqrt(Math.pow(ifin - fila, 2) + Math.pow(jfin - columna, 2));

            if (distancia < min) {
                min = distancia;
                seleccionada = regla;
            }
        }

        return seleccionada;
    }

    public static void laberintoTorreMinimaLongitudAntihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int min = Integer.MAX_VALUE;
        LinkedList<int[][]> listMatrix = new LinkedList<>();
        int menor = laberintoTorreAntihorario(listMatrix, m, i, j, ifin, jfin, paso, min);
        for (int k = 0; k < listMatrix.size(); k++) {
            if (listMatrix.get(k)[ifin][jfin] == menor) {
                c++;
                mostrar(listMatrix.get(k));
            }
        }
    }

    public static int laberintoTorreAntihorario(LinkedList<int[][]> listMatrix, int m[][], int i, int j, int ifin,
            int jfin, int paso, int min) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            listMatrix.add(m2);
            if (paso < min) {
                min = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesTorreAntihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaAntihorario(L1, m, ifin, jfin);
            min = laberintoTorreAntihorario(listMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, min);
            m[R.fil][R.col] = 0;
        }
        return min;
    }

    public static LinkedList<Regla> reglasAplicablesTorreAntihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = i + 1; k < m.length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1; k < m[0].length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        return L1;
    }

    //alfil
    public static void laberintoAlfilMinimaLongitudAntihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int min = Integer.MAX_VALUE;
        LinkedList<int[][]> listMatrix = new LinkedList<>();
        int menor = laberintoAlfilAntihorario(listMatrix, m, i, j, ifin, jfin, paso, min);
        for (int k = 0; k < listMatrix.size(); k++) {
            if (listMatrix.get(k)[ifin][jfin] == menor) {
                c++;
                mostrar(listMatrix.get(k));
            }
        }
    }

    public static int laberintoAlfilAntihorario(LinkedList<int[][]> listMatrix, int m[][], int i, int j, int ifin,
            int jfin, int paso, int min) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            listMatrix.add(m2);
            if (paso < min) {
                min = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfilAntihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaAntihorario(L1, m, ifin, jfin);
            min = laberintoAlfilAntihorario(listMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, min);
            m[R.fil][R.col] = 0;
        }
        return min;
    }

    public static LinkedList<Regla> reglasAplicablesAlfilAntihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = i - 1, l = j - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1, l = j + 1; k >= 0 && l < m[0].length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1, l = j + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1, l = j - 1; k < m.length && l >= 0; k++, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }

    //dama
    public static void laberintoDamaMinimaLongitudAntihorario(int m[][], int i, int j, int ifin, int jfin, int paso) {
        int min = Integer.MAX_VALUE;
        LinkedList<int[][]> listMatrix = new LinkedList<>();
        int menor = laberintoDamaAntihorario(listMatrix, m, i, j, ifin, jfin, paso, min);
        for (int k = 0; k < listMatrix.size(); k++) {
            if (listMatrix.get(k)[ifin][jfin] == menor) {
                c++;
                mostrar(listMatrix.get(k));
            }
        }
    }

    public static int laberintoDamaAntihorario(LinkedList<int[][]> listMatrix, int m[][], int i, int j, int ifin,
            int jfin, int paso, int min) {
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            int m2[][] = copiarMatriz(m);
            listMatrix.add(m2);
            if (paso < min) {
                min = paso;
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesDamaAntihorario(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = elegirReglaAntihorario(L1, m, ifin, jfin);
            min = laberintoDamaAntihorario(listMatrix, m, R.fil, R.col, ifin, jfin, paso + 1, min);
            m[R.fil][R.col] = 0;
        }
        return min;
    }

    public static LinkedList<Regla> reglasAplicablesDamaAntihorario(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList<>();
        for (int k = j - 1; k >= 0; k--) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i - 1; k >= 0; k--) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j + 1, l = i - 1; k < m.length && l >= 0; k++, l--) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = j + 1; k < m.length; k++) {
            if (posValida(m, i, k)) {
                L1.add(new Regla(i, k));
            }
        }
        for (int k = j + 1, l = i + 1; k < m.length && l < m[0].length; k++, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        for (int k = i + 1; k < m[0].length; k++) {
            if (posValida(m, k, j)) {
                L1.add(new Regla(k, j));
            }
        }
        for (int k = j - 1, l = i + 1; k >= 0 && l < m[0].length; k--, l++) {
            if (posValida(m, l, k)) {
                L1.add(new Regla(l, k));
            }
        }
        return L1;
    }

    //caballo
    public static Regla elegirReglaAntihorarioCaballo(LinkedList<Regla> L1, int[][] m, int ifin, int jfin) {
        int min = Integer.MAX_VALUE;
        Regla seleccionada = null;

        for (Regla regla : L1) {
            int fila = regla.fil;
            int columna = regla.col;

            // Encuentra la regla que minimiza la distancia euclidiana hacia el destino
            int distancia = (int) Math.sqrt(Math.pow(ifin - fila, 2) + Math.pow(jfin - columna, 2));

            if (distancia < min) {
                min = distancia;
                seleccionada = regla;
            }
        }

        return seleccionada;
    }

    //rey
    public static Regla elegirReglaAntihorarioRey(LinkedList<Regla> L1, int[][] m, int ifin, int jfin) {
        int min = Integer.MAX_VALUE;
        Regla seleccionada = null;

        for (Regla regla : L1) {
            int fila = regla.fil;
            int columna = regla.col;

            // Encuentra la regla que minimiza la distancia euclidiana hacia el destino
            int distancia = (int) Math.sqrt(Math.pow(ifin - fila, 2) + Math.pow(jfin - columna, 2));

            if (distancia < min) {
                min = distancia;
                seleccionada = regla;
            }
        }

        return seleccionada;
    }

}
