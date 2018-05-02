/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conectan;

import java.util.ArrayList;
import java.util.List;




/**
 *
 * @author José María Serrano
 * @version 1.3 Departamento de Informática. Universidad de Jáen
 *
 * Inteligencia Artificial. 2º Curso. Grado en Ingeniería Informática
 *
 * Clase AlfaBetaPlayer para representar al jugador CPU que usa la poda Alfa
 * Beta
 *
 * Esta clase es la que tenemos que implementar y completar
 *
 */
public class AlfaBetaPlayer extends Player {
    
    /**
     *
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int jugada(Grid tablero, int conecta) {
        int columna;
        // ...
        // Calcular la mejor columna posible donde hacer nuestra jugada
        
        //Pintar Ficha (sustituir 'columna' por el valor adecuado)
        //Pintar Ficha
        
        int matriz[][] = tablero.toArray();
        
        /*Creamos una matriz nueva y nos copie la nuestra paso a paso*/
        
        int matrizA[][] = MatrizCopia(matriz, tablero.getColumnas(), tablero.getFilas());
        
        /*Creamos la primera lista de sucesores tras el primer movimiento del jugador humano que siempre empieza la partida*/
        
        List<Nodo> sucesores = sucesores(matrizA,tablero.getColumnas(), tablero.getFilas());
        int i = 0;
        
        int auxInt=Integer.MAX_VALUE;
        
        /*Nodo auxiliar para guardar el mejor nodo que se retorna tras la llamada a la función alfa beta*/
        
        Nodo auxNodo=new Nodo();
        
        /*Empezamos a llamar a la función alfa beta con la primera lista de sucesores que hemos generado más arriba*/
        
        while (i<sucesores.size()){

            int a = alfabeta(matrizA,false,tablero.getColumnas(), tablero.getFilas(),conecta,sucesores.get(i),Integer.MIN_VALUE,Integer.MAX_VALUE);
           
            if(auxInt>a){
                auxInt=a;
                auxNodo.setXY(sucesores.get(i).getX(), sucesores.get(i).getY());
                
            }
            ++i;
        }  

        return tablero.checkWin(tablero.setButton(auxNodo.getY(), ConectaN.JUGADOR2), auxNodo.getY(), conecta);

    } // jugada
    
    public int[][] MatrizCopia(int[][]matriz, int ancho, int alto){
        int copia[][] = new int[alto][ancho];
        for(int i=0; i<alto;i++){
            System.arraycopy(matriz[i], 0, copia[i], 0, ancho);
        }
        return copia;
    }
    
    /*Clase que guarda la posición x e y de una casilla. La usamos para generar sucesores y expandir nodos*/
    
    public class Nodo{
        int y;
        int x;
        List<Nodo> sucesores;
        
    public Nodo(){
        this.x=0;
        this.y=0;
    }
    
    
    public Nodo(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setXY(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    }

    // Éste es el método que debemos modificar, para que incluya la poda Alfa-beta

    
    //Método que aplica la poda alfa beta
    
    public int alfabeta(int grid[][], boolean jugador,int ancho, int alto, int conecta, Nodo n, int alfa, int beta){
        
        int numPos;
        int contP;
        boolean contrario;
        int mejorVal=Integer.MIN_VALUE;
        int aux;
        int a,b;
        boolean poda;
        
            if(tableroLleno(grid,ancho)){
                return checkWinMatrix(grid,ancho,alto,n.getX(), n.getY(), conecta);
            }else{
                
                //Si hay que acabar la recursión, devolvemos la valoración de la posición por la función
                //de evaluación actual
                
                if(nodoGanador(grid,ancho,alto,jugador,n,conecta)== 1){
                    return 1;
                    }else{
                        contrario = jugadorContrario(jugador);
                        
                        //Obtenemos las posiciones donde puede poner el jugador
                        
                        List<Nodo> lista = sucesores(grid,ancho,alto);
                        numPos=lista.size();
                        mejorVal=Integer.MIN_VALUE;
                        contP=0;
                        poda=false;
                        a=alfa;
                        b=beta;
                        do{
                            /*Simulamos la colocacion de una ficha en una posición y llamamos recursivamente a la función
                            alfa beta */
                            
                            ponerBoton(grid, lista.get(contP).getX(),lista.get(contP).getY(), jugador);
                            aux=alfabeta(grid,contrario,ancho,alto,conecta,lista.get(contP),b,a);
                            
                            if(aux>mejorVal){
                                //Si encontramos un movimiento mejor, nos lo quedamos
                                mejorVal=aux;
                                
                                a=(a>mejorVal)?a:mejorVal;
                            }
                            if(beta<=mejorVal)
                                poda=true;
                            contP++;
                        }while((contP<numPos)&&!poda);
                        return mejorVal;
                    }
                }
                
    }//alfabeta
    
    public boolean jugadorContrario(boolean jugador){
        if(jugador==true)
            return false;
        else
            return true;
    }
    
    public List<Nodo> sucesores(int[][] grid, int ancho, int alto){
        List<Nodo> sucesores = new ArrayList<>();
        for (int i = 0 ; i<alto ; ++i){
            int j=ancho-1;
            boolean espacioBlanco=false;
            while (j>=0 && !espacioBlanco){
                if(grid[j][i] == 0){
                    espacioBlanco=true;
                    Nodo n;
                    n = new Nodo(j,i);
                    sucesores.add(n);
                }else
                    --j;
            }
        }
        return sucesores;
    }
    

    public int nodoGanador(int grid[][], int ancho, int alto, boolean jugador, Nodo n, int conecta){
        return checkWinMatrix(grid,ancho,alto,n.getX(), n.getY(), conecta);
    }

    public void ponerBoton(int tab[][], int x, int y, boolean jugador){
        if(jugador==true){
            tab[x][y]=1;
        }else{
            tab[x][y]=-1;
        }
    }
    
    public int checkWinMatrix(int grid[][], int ancho, int alto, int x, int y, int conecta){
        
        //Comprobar vertical
        
        int ganar1 = 0;
        int ganar2 = 0;
        int ganador = 0;
        boolean salir = false;
        for (int j = 0; (j < alto) && !salir; j++) {
            if (grid[j][y] != 0) {
                if (grid[j][y] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (!salir) {
                    if (grid[j][y] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
        }
        // Comprobar horizontal
        ganar1 = 0;
        ganar2 = 0;
        for (int i = 0; (i < ancho) && !salir; i++) {
            if (grid[x][i] != 0) {
                if (grid[x][i] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (ganador != 1) {
                    if (grid[x][i] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
        }
        // Comprobar oblicuo. De izquierda a derecha
        ganar1 = 0;
        ganar2 = 0;
        int a = x;
        int b = y;
        while (b > 0 && a > 0) {
            a--;
            b--;
        }
        while (b < ancho && a < alto && !salir) {
            if (grid[a][b] != 0) {
                if (grid[a][b] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (ganador != 1) {
                    if (grid[a][b] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
            a++;
            b++;
        }
        // Comprobar oblicuo de derecha a izquierda 
        ganar1 = 0;
        ganar2 = 0;
        a = x;
        b = y;
        //buscar posición de la esquina
        while (b < ancho - 1 && a > 0) {
            a--;
            b++;
        }
        while (b > -1 && a < alto && !salir) {
            if (grid[a][b] != 0) {
                if (grid[a][b] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (ganador != 1) {
                    if (grid[a][b] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
            a++;
            b--;
        }

        return ganador;
    }
    
    public boolean tableroLleno(int grid[][], int ancho){
        int i = 0;
        while(i<ancho){
            if(grid[0][i]==0){
                return false;
            }else{
                ++i;
            }
        }
        return true;
    } 
} // AlfaBetaPlayer
