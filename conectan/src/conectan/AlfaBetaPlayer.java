package conectan;


/**
 * @author Abdallah Abedraba y Sergio Jiménez Moreno
 */
public class AlfaBetaPlayer extends Player {
    private int maxDepth;
    private int n_filas;
    private int n_columnas;
    private int conectan;

    /**
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int jugada(Grid tablero, int conecta) {
        maxDepth = conecta;
        n_columnas = tablero.getColumnas();
        n_filas = tablero.getFilas();
        conectan = conecta;
        int matriz[][] = tablero.toArray();
        int columna = minMax(matriz);
        return tablero.checkWin(tablero.setButton(columna, ConectaN.JUGADOR2), columna, conecta);
    } // jugada

    /**
     * Algoritmo minMax. El estado actual es el estado de la partida en el momento de la jugada (nodo inicial)
     * y se sopesan n_columnas posibilidades a las cuales se aplica a cada una la poda alfa-beta.
     *
     * @param matriz matriz con el estado actual de la partida
     * @return la columna con el mejor movimiento.
     */
    private int minMax(int matriz[][]) {
        int mejorJugada = -1; // mejorJugada será la mejor columna con la que jugar
        int max, maxTemp; // max será el máximo para el nodo inicial de la poda alfa-beta.
        max = Integer.MIN_VALUE; // inicializamos max al menor valor posible
        for (int i = 0; i < n_columnas; i++) {
            if (!columnaLlena(matriz, i)) {
                int x = insertar(matriz, i, ConectaN.JUGADOR2);
                int estadoPartida = checkWin(matriz, x, i, conectan);
                // para el minValue utilizamos los valores +inf y -inf ya que cada jugada debe sopesarse por sí sola
                maxTemp = podaMin(matriz, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, estadoPartida);
                matriz[x][i] = 0;
                if (maxTemp > max) {
                    max = maxTemp;
                    mejorJugada = i;
                }
            }
        }
        return mejorJugada;
    }

    /**
     * Algoritmo Min de la poda Alfa-Beta.
     * En caso de que la partida no haya llegado a un fin sigue creando diferentes posibilidades para pasarlas
     * a la fase podaMax.
     *
     * @param matriz        matriz que representa el estado actual de la partida
     * @param depth         profunidad en el árbol minMax (no debe ser mayor que la profundidad máxima establecida)
     * @param alpha         valor del alfa actual
     * @param beta          valor del beta actual
     * @param estadoPartida estado de la partida. -1 o 1 si alguien gana, 0 si sigue en juego
     * @return el valor mínimo para el nodo Min del árbol
     */
    private int podaMin(int[][] matriz, int depth, int alpha, int beta, int estadoPartida) {
        if (estadoPartida != 0 || esEmpate(matriz) || depth > maxDepth) {
            return puntuacion(matriz, estadoPartida);
        } else {
            for (int i = 0; i < n_columnas; i++) {
                if (!columnaLlena(matriz, i)) {
                    int fila = insertar(matriz, i, ConectaN.JUGADOR1);
                    estadoPartida = checkWin(matriz, fila, i, conectan);
                    beta = menor(beta, podaMax(matriz, depth + 1, alpha, beta, estadoPartida));
                    matriz[fila][i] = 0; //quitamos la jugada
                    if (alpha >= beta)
                        return alpha;
                }
            }
            return beta;
        }
    }

    /**
     * Algoritmo Max de la poda Alfa-Beta.
     * En caso de que la partida no haya llegado a un fin sigue creando diferentes posibilidades para pasarlas
     * a la fase podaMin.
     *
     * @param matriz        matriz que representa el estado actual de la partida
     * @param depth         profunidad en el árbol minMax (no debe ser mayor que la profundidad máxima establecida)
     * @param alpha         valor del alfa actual
     * @param beta          valor del beta actual
     * @param estadoPartida estado de la partida. -1 o 1 si alguien gana, 0 si sigue en juego
     * @return el valor mínimo para el nodo Min del árbol
     */
    private int podaMax(int[][] matriz, int depth, int alpha, int beta, int estadoPartida) {
        if (estadoPartida != 0 || esEmpate(matriz) || depth > maxDepth) {
            return puntuacion(matriz, estadoPartida);
        } else {
            for (int i = 0; i < n_columnas; i++) {
                if (!columnaLlena(matriz, i)) {
                    int fila = insertar(matriz, i, ConectaN.JUGADOR2);
                    estadoPartida = checkWin(matriz, fila, i, conectan);
                    alpha = mayor(alpha, podaMin(matriz, depth + 1, alpha, beta, estadoPartida));
                    matriz[fila][i] = 0;
                    if (alpha >= beta)
                        return beta;
                }
            }
            return alpha;
        }
    }

    /**
     * Función que asigna una puntuación a un nodo del árbol
     *
     * @param matriz        matriz que tiene la situación actual del juego
     * @param estadoPartida estado de terminación de la partida
     * @return  400 en caso de que gane, -400 en caso de que gane el
     *          contrincante y en caso de que no haya llegado a un fin de
     *          partida le asigna un valor atendiendo al beneficio propio.
     *          El valor es positivo si es beneficioso, negativo en caso contrario.
     */
    private int puntuacion(int matriz[][], int estadoPartida) {
        if (estadoPartida == ConectaN.JUGADOR2) {
            return 400;
        } else if (estadoPartida == ConectaN.JUGADOR1) {
            return -400;
        } else { // caso de que la partida no haya acabado
            return utilidad(matriz, ConectaN.JUGADOR2) - utilidad(matriz, ConectaN.JUGADOR1);
        }
    }

    /**
     * Función que asigna una puntuación a cada jugada posible
     * @param matriz matriz de la situación actual en el nodo
     * @param jugador jugador al que se le calculará el beneficio
     *                de su jugada
     * @return puntuación asignada atendiendo al beneficio
     */
    private int utilidad(int matriz[][], int jugador) {
        int puntuacion = 0;
        for (int i = 0; i < n_columnas; i++) {
            if (!columnaLlena(matriz, i)) {
                int fila = insertar(matriz, i, jugador);
                int estado = checkWin(matriz, fila, i, conectan);
                if (estado == jugador)
                    puntuacion += 10;
                matriz[fila][i] = 0;
            }
        }
        return puntuacion;
    }

    /*----FUNCIONES AUXILIARES----*/

    /**
     * Devuelve el máximo entre dos valores
     *
     * @param x primer valor
     * @param y segundo valor
     * @return el máximo de los dos valores
     */
    private int mayor(int x, int y) {
        if (x >= y)
            return x;
        else
            return y;
    }

    /**
     * Devuelve el mínimo entre dos valores
     *
     * @param x primer valor
     * @param y segundo valor
     * @return el mínimo de los dos valores
     */
    private int menor(int x, int y) {
        if (x < y)
            return x;
        else
            return y;
    }

    /**
     * Encuentra la fila libre en la columna col e introduce la ficha jugador
     * OJO: llamar sólo cuando se ha comprobado que la fila no está llena
     *
     * @param m       matriz del juego en la que se inserta la ficha
     * @param col     columna en la que se introduce la ficha
     * @param jugador tipo de ficha que se va a introducir
     * @return fila en la que se inserta la ficha
     */
    public int insertar(int m[][], int col, int jugador) {
        int y = n_filas - 1;
        //Ir a la última posición de la columna
        while ((y >= 0) && (m[y][col] != 0)) {
            y--;
        }
        m[y][col] = jugador;
        return y;
    }

    /**
     * Determina si la columna col está llena
     *
     * @param matriz matriz de la cual se comprobará la fila
     * @param col    columna a sopesar
     * @return true si la columna está llena, false en caso contrario
     */
    private Boolean columnaLlena(int matriz[][], int col) {
        if (matriz[0][col] != 0)
            return true;
        return false;
    }

    /**
     * Determina si la matriz ha llegado a una situación de empate
     *
     * @param matriz matriz de la que se comprobará
     * @return true en caso de que se haya llegado a una situación de empate,
     * false en caso contrario
     */
    private Boolean esEmpate(int matriz[][]) {
        for (int i = 0; i < n_columnas; i++) {
            if (matriz[0][i] == 0)
                return false;
        }
        return true;
    }

    /**
     * Función basada en el Grid.checkWin
     * Se ha añadido el parámetro boton_int[][] para poder trabajar con cualquier matriz
     * y se han cambiado las variables de fila y columna a las globales definidas en esta
     * clase (n_columnas, n_filas)
     */
    public int checkWin(int boton_int[][], int x, int y, int conecta) {
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
} // AlfaBetaPlayer