/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conectan;


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
    private int maxDepth;
    private int n_filas;
    private int n_columnas;
    private int conecta;

    private int max( int x, int y ){
        if ( x >= y )
            return x;
        else
            return y;
    }

    private int min( int x, int y ){
        if ( x < y )
            return x;
        else
            return y;
    }

    /**
     *
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int jugada(Grid tablero, int conecta) {
        // ...
        // Calcular la mejor columna posible donde hacer nuestra jugada
        maxDepth = conecta;
        n_columnas = tablero.getColumnas();
        n_filas = tablero.getFilas();
        int matriz[][] = tablero.toArray();
        int columna = minMax(  matriz );
        return tablero.checkWin(tablero.setButton(columna, ConectaN.JUGADOR2), columna, conecta);
    } // jugada

    private int minMax( int matriz[][] ){
        int mejor_mov = -1;
        int max, max_actual;
        max = Integer.MIN_VALUE;
        for (int i = 0; i < n_columnas; i++) {
            if ( !columnaLlena( matriz, i ) ){
                int temp = insertar( matriz, i, ConectaN.JUGADOR2 );
                int isFin = checkWin( matriz, temp, i, conecta );
                max_actual = minValue(matriz, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, isFin );
                matriz[temp][i] = 0;
                if ( max_actual > max ){
                    max = max_actual;
                    mejor_mov = i;
                }
            }
        }
        return mejor_mov;
    }

    private int minValue( int current[][], int depth, int alpha, int beta, int isFin ){
        if ( isFin != 0 ){
            return heuristica( current, isFin );
        } else {
            if ( esEmpate( current ) ){
                return heuristica( current, isFin );
            } else {
                if ( depth > maxDepth ){
                    return heuristica( current, isFin );
                } else {
                    for (int i = 0; i < n_columnas; i++) {
                        if ( !columnaLlena( current, i ) ){
                            int temp = insertar( current, i, ConectaN.JUGADOR2 );
                            isFin = checkWin( current, temp, i, conecta );
                            beta = min(beta, maxValue( current,depth+1, alpha, beta, isFin ));
                            current[temp][i] = 0;
                            if ( alpha >= beta )
                                return alpha;
                        }
                    }
                    return beta;
                }
            }
        }
    }

    private int maxValue( int current[][], int depth, int alpha, int beta, int isFin ){
        if ( isFin != ConectaN.VACIO ){
            return heuristica( current, isFin );
        } else {
            if ( esEmpate( current ) ){
                return heuristica( current, isFin );
            } else {
                if ( depth > maxDepth ){
                    return heuristica( current, isFin );
                } else {
                    for (int i = 0; i < n_columnas; i++) {
                        if ( !columnaLlena( current, i ) ){
                            int temp = insertar( current, i, ConectaN.JUGADOR1 );
                            isFin = checkWin( current, temp, i, conecta );
                            alpha = max( alpha, minValue( current, depth+1, alpha, beta, isFin ));
                            current[temp][i] = 0;
                            if ( alpha >= beta )
                                return beta;
                        }
                    }
                    return alpha;
                }
            }
        }
    }

    private int heuristica( int current[][], int isFin ){
        int costo = 0;
        if ( isFin != 0 ){
            if ( isFin == ConectaN.JUGADOR2 )
                return 10000;
            else
                return -10000;
        }
//        costo = costoU(current, ConectaN.JUGADOR2 ) - costoU(current, ConectaN.JUGADOR1 );
        return costo;
    }

//    private int costoU( int current[][], int jugador ){
//        Grid copia;
//        int puntuacion = 0;
//        for (int i = 0; i < n_columnas; i++) {
//            int temp = copia.getButton( i, jugador );
//            int estado = copia.checkWin( temp, i, conecta );
//            if ( estado == jugador )
//                puntuacion += 10;
//        }
//        return puntuacion;
//    }

    // Comprobar si el tablero se halla en un estado de fin de partida,
    // a partir de la última jugada realizada
    public int checkWin(int boton_int[][], int x, int y, int conecta) {
        /*
         *	x fila
         *	y columna
         */

        //Comprobar vertical
        int ganar1 = 0;
        int ganar2 = 0;
        int ganador = 0;
        boolean salir = false;
        for (int i = 0; (i < n_filas) && !salir; i++) {
            if (boton_int[i][y] != ConectaN.VACIO) {
                if (boton_int[i][y] == ConectaN.JUGADOR1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = ConectaN.JUGADOR1;
                    salir = true;
                }
                if (!salir) {
                    if (boton_int[i][y] == ConectaN.JUGADOR2) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = ConectaN.JUGADOR2;
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
        for (int j = 0; (j < n_columnas) && !salir; j++) {
            if (boton_int[x][j] != ConectaN.VACIO) {
                if (boton_int[x][j] == ConectaN.JUGADOR1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = ConectaN.JUGADOR1;
                    salir = true;
                }
                if (ganador != ConectaN.JUGADOR1) {
                    if (boton_int[x][j] == ConectaN.JUGADOR2) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = ConectaN.JUGADOR2;
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
        while (b < n_columnas && a < n_filas && !salir) {
            if (boton_int[a][b] != ConectaN.VACIO) {
                if (boton_int[a][b] == ConectaN.JUGADOR1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = ConectaN.JUGADOR1;
                    salir = true;
                }
                if (ganador != ConectaN.JUGADOR1) {
                    if (boton_int[a][b] == ConectaN.JUGADOR2) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = ConectaN.JUGADOR2;
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
        while (b < n_columnas - 1 && a > 0) {
            a--;
            b++;
        }
        while (b > -1 && a < n_filas && !salir) {
            if (boton_int[a][b] != ConectaN.VACIO) {
                if (boton_int[a][b] == ConectaN.JUGADOR1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = ConectaN.JUGADOR1;
                    salir = true;
                }
                if (ganador != ConectaN.JUGADOR1) {
                    if (boton_int[a][b] == ConectaN.JUGADOR2) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = ConectaN.JUGADOR2;
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
    } // checkWin

    public int insertar(int m[][],int col, int jugador) {
        int y = n_filas - 1;
        //Ir a la última posición de la columna
        while ((y >= 0) && (m[y][col] != 0)) {
            y--;
        }
        m[y][col] = jugador;
        return y;
    }

    private Boolean columnaLlena( int matriz[][], int col ){
        if ( matriz[0][col] != 0 )
            return true;
        return false;
    }

    private Boolean esEmpate( int matriz[][] ){
        for (int i = 0; i < n_columnas; i++) {
            if ( matriz[0][i] == 0 )
                return false;
        }
        return true;
    }

//
//    private Boolean
} // AlfaBetaPlayer