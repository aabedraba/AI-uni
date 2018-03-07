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
    private final ArrayList<Grid> celdasVisitadas;
    
    /**
     * ArrayList de movimientos realizados.
     */
    private final ArrayList<Integer> movRealizados;
    

    /**
     * Variable que almacena la ultima celda visitada.
     */
    private Grid lastGrid;
    
    /**
     * Variable que almacena la ultima celda visitada.
     */
    private Grid nextGrid;
    
    /**
     * Variable que almacena la ultima celda visitada.
     */
    private int ponerBomba;
    private int quedanBomba;
    
    private boolean Teletrans;
    /**
     * Constructor que inicializa el nombre del raton
     * y el ArrayList de celdas visitadas.
     */
    public M18E18a(){
        //Nombre del ratón.
        super("Sanic");
        this.celdasVisitadas = new ArrayList<>();
        this.movRealizados = new ArrayList<>();
        this.ponerBomba = 0;
        this.quedanBomba = 0;
        this.nextGrid = null;
        this.Teletrans = false;
    }

    //Declaro las funciones que el raton necesitara para moverse.
    /**
     * @brief Funcion que permite que el raton se mueva.
     * @param currentGrid Casilla actual.
     * @param cheese Queso, en este caso como es el raton explorador
     *               no lo usaremos.
     * @return Devuelve el movimiento que realizara el raton, de tipo entero.
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {

        boolean control=true;
        //Primero añadimos la casilla actual a las celdas visitadas.
        for (int i = 0; i < celdasVisitadas.size(); i++) {
            if (celdasVisitadas.get(i).getX() == currentGrid.getX() &&
                celdasVisitadas.get(i).getY() == currentGrid.getY()){
                control=false;
            }
        }
        if(control){
            celdasVisitadas.add( currentGrid );  
        }
        
        
        
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
        ArrayList<Integer> posiblesMov = new ArrayList<>();
            if (currentGrid.canGoDown()) posiblesMov.add(Mouse.DOWN);
            if (currentGrid.canGoUp()) posiblesMov.add(Mouse.UP); 
            if (currentGrid.canGoLeft()) posiblesMov.add(Mouse.LEFT);
            if (currentGrid.canGoRight()) posiblesMov.add(Mouse.RIGHT);
        
        /**
         * Como nos piden que el raton sea competitivo, si esta en una 
         * casilla con 3 o mas moviminetos disponibles, le quedan bombas 
         * por poner y no ha puesto ya una bomba es esta casilla, 
         * entonces pone una bomba.
         */
        Random random = new Random();
        if(quedanBomba != 5){
            if(posiblesMov.size() >= 3){
                if(currentGrid != lastGrid){
                    ponerBomba = random.nextInt(5);
                    if(ponerBomba%2 == 0){
                        lastGrid = currentGrid;                       
                        quedanBomba++;
                        return Mouse.BOMB;
                    }
                    
                } 
            }
        }
        
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
                Teletrans = false;
                return posiblesMov.get(i);
            }      
        }
        
        /**
         * Si ya se a explorado la zona a la que se a teletransportado
         * se activa la segunda funcion para moverse que solo tiene en
         * cuenta la casilla de donde viene.
         */
        if(!(nextGrid.getX() == currentGrid.getX() &&
                nextGrid.getY() == currentGrid.getY())){
            int cent = yaExplorado(currentGrid,posiblesMov);
            if(cent != -1){
                /**
                 * Cuando se cumple la condicion se le asigna a lastGrid 
                 * la casilla actual y se envia el movimiento.
                 */
                lastGrid = currentGrid;
                movRealizados.add(cent);
                return cent;
            }            
        }
        
        /**
        * Si lo anterior no devuelve un movimiento quiere decir que no hay 
        * disponible ninguna casilla inexplorada por lo que el raton debe 
        * volver sobre sus pasos hasta que encuentre una casilla sin explorar.
        */
        lastGrid = currentGrid;
        return Vuelta(currentGrid);
    }

    
    /**
     * @brief La funcion determina la direccion que se le pasa esta 
     *        explorada o no.
     * @param direction Direccion a la que se quiere ir.
     * @param currentGrid Casilla actual
     * @return Devuelve verdadero si no se ha visitado la casilla ha la     
     *         que se quiere ir, y falso en caso contrario.
     */
    private boolean Explorar (int direccion, Grid currentGrid){
        //Centinela que se utiliza para ver si se a visitado la casilla.
        boolean control=true;
        
        //Variables que sirven para ver si se a visitado la casilla.
        int x = currentGrid.getX();
        int y = currentGrid.getY();
        
        /**
         * En funcion del movimiento se calcula la posicion de la 
         * casilla a la que se va a acceder.
        */
        switch (direccion){
            case Mouse.UP: 
                y += 1;
                nextGrid = new Grid(x, y);
                break;

            case Mouse.DOWN:
                y -= 1;
                nextGrid = new Grid(x, y);
                break;

            case Mouse.LEFT:
                x -= 1;
                nextGrid = new Grid(x, y);
                break;

            case Mouse.RIGHT:
                x += 1;
                nextGrid = new Grid(x, y);
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
    private int Vuelta (Grid currentGrid){
        int centinela= movRealizados.size()-1;
        int mov=0;
        
        //Variables que sirven para ver si se a visitado la casilla.
        int x = currentGrid.getX();
        int y = currentGrid.getY();
        
        switch (movRealizados.get(centinela)){
            case Mouse.UP: 
                mov = Mouse.DOWN;
                y -= 1;
                nextGrid = new Grid(x, y);
                break;

            case Mouse.DOWN:
                mov = Mouse.UP;
                y += 1;
                nextGrid = new Grid(x, y);
                break;

            case Mouse.LEFT:
                mov = Mouse.RIGHT;
                x += 1;
                nextGrid = new Grid(x, y);
                break;

            case Mouse.RIGHT:
                mov = Mouse.LEFT;
                x -= 1;
                nextGrid = new Grid(x, y);
                break;
            }
        movRealizados.remove(centinela);
        return mov;
    }
  
    /**
     * @brief Funcion para que el raton siempre se mueva.
     * @param direccion Movimiento a realizar
     * @param currentGrid Casilla actual
     * @return Verdadero si no se viene a donde se quiere ir.
     */
    private int yaExplorado (Grid currentGrid, 
            ArrayList<Integer> posiblesMov){
        
       //Centinela que se utiliza para ver si se a visitado la casilla.
        boolean control;
        int direccion;
        //Variables que sirven para ver si se a visitado la casilla.
        int x = currentGrid.getX();
        int y = currentGrid.getY();
        
        Random random = new Random();
        do {
            
            if(posiblesMov.isEmpty()){
                direccion=-1;
                return direccion;
            }
            control=false;
            int centinela=random.nextInt(posiblesMov.size());
            direccion = posiblesMov.get(centinela);
            posiblesMov.remove(centinela);
            /**
             * En funcion del movimiento se calcula la posicion de la 
             * casilla a la que se va a acceder.
            */ 
            switch (direccion){
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
             * Comprueba si la casilla a la que se quiere ir es de donde viene.
             */
            if(lastGrid.getX() == x && lastGrid.getY() == y){
                control=true;
            }
            
        } while (control);
        
        
        return direccion;
    }

    private boolean Teletransporte (Grid currentGrid){
    //Variables que sirven para ver si se a visitado la casilla.
    int x = currentGrid.getX();
    int y = currentGrid.getY();
    
    if(nextGrid == null){
        return false;
    }
    
    return !(nextGrid.getX() == x && nextGrid.getY() == y);
    }
    
    @Override
    public void newCheese() {

    }

    @Override
    public void respawned() {

    }
}
