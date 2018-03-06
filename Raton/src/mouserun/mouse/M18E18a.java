package mouserun.mouse;

import mouserun.game.*;
import java.util.*;

//Raton Explorador
public class M18E18a extends Mouse {

    //Declaro las variables que el raton necesitara para saber moverse.
    /**
     * Comienzo con un ArrayList de celdas visitadas, el cual almacena los 
     * Grids(celdas) que el raton a visitado.
     */
    private ArrayList<Grid> celdasVisitadas;
    
    /**
     * Comienzo con un ArrayList de celdas visitadas, el cual almacena los 
     * Grids(celdas) que el raton a visitado.
     */
    private ArrayList<Integer> movRealizados;
    
    /**
     * Variable que almacena la ultima celda visitada.
     */
    private Grid lastGrid;
    
    
    /**
     * Constructor que inicializa el nombre del raton
     * y el ArrayList de celdas visitadas.
     */
    public M18E18a(){
        //Nombre del ratón.
        super("Sanic");
        this.celdasVisitadas = new ArrayList<>();
        this.movRealizados = new ArrayList<>();
    }

    
    /**
     * @brief Funcion que permite que el raton se mueva.
     * @param currentGrid Casilla actual.
     * @param cheese Queso, en este caso como es el raton explorador
     *               no lo usaremos.
     * @return Devuelve el movimiento que realizara el raton, de tipo entero.
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        
        //Primero añadimos la casilla actual a las celdas visitadas.
        celdasVisitadas.add( currentGrid );
        
        //A continuacion incrementamos los pasos dados.
        this.incSteps();
        
        /**
         * Por ultimo llamamos a la funcion busqueda, la cual concentra todo el 
         * codigo usado para que el raton sepa a donde ir.
         */
        return movimiento(currentGrid);
    }
    

    /**
     * @brief Funcion que calcula el movimiento que va a realizar el raton.
     * @param currentGrid
     * @return 
     */
    public int movimiento ( Grid currentGrid ) {
        /**
         * Array que almacena los posibles movimientos que 
         * puede realizar el raton.
         */
        ArrayList<Integer> posiblesMov = new ArrayList<Integer>();
		if (currentGrid.canGoUp()) posiblesMov.add(Mouse.UP);
		if (currentGrid.canGoDown()) posiblesMov.add(Mouse.DOWN);
		if (currentGrid.canGoLeft()) posiblesMov.add(Mouse.LEFT);
		if (currentGrid.canGoRight()) posiblesMov.add(Mouse.RIGHT);
        
        /**
         * En este bucle va buscando cual de las posibles casillas a visitar no
         * ha explorado todavia, y coge la primera de ellas.
         */
        for(int i=0;i<posiblesMov.size();i++){
            if(Explorar(posiblesMov.get(i),currentGrid)){
                /**
                 * Cuando se cumple la condicion se le asigna a lastGrid 
                 * la casilla actual, se incrementa el contador de casillas
                 * exploradas, se le añade el movimiento que se va a realizar 
                 * al ArrayList de movimientos realizados y se envia el 
                 * movimiento.
                 */
                lastGrid = currentGrid;
                this.incExploredGrids();
                movRealizados.add(posiblesMov.get(i));
                return posiblesMov.get(i);
            }      
        }
        
        /**
        * Si lo anterior no devuelve un movimiento quiere decir que no hay 
        * disponible ninguna casilla inexplorada por lo que el raton debe 
        * volver sobre sus pasos hasta que encuentre una casilla sin explorar.
        */
        lastGrid = currentGrid;
        return Vuelta();
    }

    
    /**
     * @brief La funcion determina la direccion que se le pasa esta 
     *        explorada o no.
     * @param direction Direccion a la que se quiere ir.
     * @param currentGrid Casilla actual
     * @return Devuelve verdadero si no se ha visitado la casilla ha la     
     *         que se quiere ir, y falso en caso contrario.
     */
    private boolean Explorar (int direction, Grid currentGrid){
        //Centinela que se utiliza para ver si se a visitado la casilla.
        boolean control=true;
        
        //Variables que sirven para ver si se a visitado la casilla.
        int x = currentGrid.getX();
        int y = currentGrid.getY();
        
        /**
         * En funcion del movimiento se calcula la posicion de la 
         * casilla a la que se va a acceder.
        */
        switch (direction){
            case Mouse.UP: 
                y += 1;
                break;

            case Mouse.DOWN:
                y -= 1;
                break;

            case Mouse.LEFT:
                x -= 1;
                break;

            case Mouse.RIGHT:
                x += 1;
                break;
        }

        /**
         * Una vez determinala posicion de la nueva casilla se 
         * compruba que no esta en el ArrayList de celdas visitadas.
        */
        for (int i = 0; i < celdasVisitadas.size(); i++) {
            if (celdasVisitadas.get(i).getX() == x &&
                celdasVisitadas.get(i).getY() == y){
                control=false;
            }
        }
        
        /**
         * Devuelve falso si se ha cumplido que la posicion a la que se va ha 
         * acceder es la misma que una de las almacenadas en celdasVisitadas.
        */
        return control;
    }
    
    
    /**
     * @brief Funcion que devuelve el movimiento contrario al ultimo que se ha 
     *        realizado para que el raton pueda volver sobre sus pasos.
     * @return Devuelve el movimiento para regresar.
     */
    private int Vuelta (){
        int centinela= movRealizados.size()-1;
        int mov=0;
        switch (movRealizados.get(centinela)){
            case Mouse.UP: 
                mov = Mouse.DOWN;
                break;

            case Mouse.DOWN:
                mov = Mouse.UP;
                break;

            case Mouse.LEFT:
                mov = Mouse.RIGHT;
                break;

            case Mouse.RIGHT:
                mov = Mouse.LEFT;
                break;
            }
        movRealizados.remove(centinela);
        return mov;
    }
  
    
    @Override
    public void newCheese() {

    }

    @Override
    public void respawned() {

    }
}
