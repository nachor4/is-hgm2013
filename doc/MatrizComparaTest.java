import java.util.Arrays;

public class MatrizComparaTest {

    public static void main(String args[]) {
      
       //Declaracion de Matrices
        int[][] m1 = new int[][] {{1,2,3,4},{5,6,7,8}};
        int[][] m2 = new int[][] {{1,2,3,4},{5,6,7,8}};
        int[][] m3 = new int[][] {{0,2,0,4},{5,0,7,0}};
      
        //Arrays.deepEquals() compara Matrices y retorna TRUE si ambas son iguales
      
        //m1 y m2 son iguales porque ambas contienen los mismos elementos
        boolean result = Arrays.deepEquals(m1, m2);
        System.out.println("Comparando Matriz m1: " + Arrays.toString(m1)
                            + " y Matriz m2: " + Arrays.toString(m2));
        System.out.println("\nLas Matrices m1 y m2 son iguales : " + result);
      
        //m2 y m3 no son iguales
        result = Arrays.deepEquals(m2, m3);
        System.out.println("\n\nComparando Matriz m2: " + Arrays.toString(m2)
                            + " y Matriz m3: " + Arrays.toString(m3));
        System.out.println("\nLas Matrices m2 y m3 son iguales : " + result);
 
    } 
  
}
