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
    private Grid currentGrid;
    private int conecta;
    private class Pair<A, B, C> {
        public A entrar;
        public B puntuacion;
        public C tablero;

        public Pair() {
        }

        public Pair(A _first, B _second, C _third) {
            entrar = _first;
            puntuacion = _second;
            tablero = _third;
        }
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
        Pair<Boolean, Integer, Grid> actual = new Pair<>();
        actual.puntuacion = 0; actual.tablero = tablero; actual.entrar = true;
        int columna = alphaBeta( actual, conecta );
        //Pintar Ficha (sustituir 'columna' por el valor adecuado)
        //Pintar Ficha
//        int columna = getRandomColumn(tablero);

        return tablero.checkWin(tablero.setButton(columna, ConectaN.JUGADOR2), columna, conecta);

    } // jugada

    //TODO: mirar columnas llenas
    int alphaBeta( Pair<Boolean, Integer, Grid> actual, int profundidad ){
        //TODO Rechazar en caso de que el booleano sea falso, o si hemos llegado a la profundidad máxima

        Pair<Boolean, Integer, Grid> []vector = new Pair[currentGrid.getColumnas()];
        for (int i = 0; i < currentGrid.getColumnas(); i++) {
            vector[i].tablero = currentGrid;
            int ganador = vector[i].tablero.checkWin( vector[i].tablero.getButton(i, ConectaN.JUGADOR2), i, conecta );
            if ( ganador == ConectaN.JUGADOR2 ) {
                vector[i].entrar = false;
                vector[i].puntuacion += profundidad;
            }
            else if ( ganador == ConectaN.JUGADOR1 ) {
                vector[i].entrar = false;
                vector[i].puntuacion -= profundidad;
            }
        }
    }
} // AlfaBetaPlayer
