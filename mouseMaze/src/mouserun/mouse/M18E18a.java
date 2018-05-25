package mouserun.mouse;

import mouserun.game.Cheese;
import mouserun.game.Grid;
import mouserun.game.Mouse;

import java.util.*;


public class M18E18a extends Mouse {
//DECLARACION DE LAS VARIABLES NECESARIAS
    //Clave que determina si se tienen movimentos
    //disponibles a nuevas casillas
    private final Integer SIN_MOVIMIENTOS = 0;
    
    //Variables para almacenar el mapa
    private HashMap<Integer, Grid> moverse;
    private HashMap<Integer, Grid> visitado;
    
    //Lista para almacenar el contrario del ultimo movimiento realizado
    private LinkedList<Integer> movimientos;
    
    //Variables que sirven para poner bombas
    int quedanBomba;
    Grid lastGrid;
//______________________________________________
    
    public M18E18a(){
        super("Explorador");
        this.moverse = new HashMap<>();
        this.visitado = new HashMap<>();
        this.movimientos = new LinkedList<>();
        this.quedanBomba=0;
        this.lastGrid=null;
    }

    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        //Comprueba si puede poner una bomba
        if(ponerBomba(currentGrid)){
            return Mouse.BOMB;
        }
        //si no la pone devuelve un moviento mediante los metodos utilizados
        aniadeCelda( currentGrid );
        Integer movimiento = posiblesMovimientos( currentGrid );
        if ( !movimiento.equals( SIN_MOVIMIENTOS ) )
            return movimiento;
        else
            return sinMovimientos();
    }

    //Si el raton pilla una bomba borra el los ultimos movimientos realizados
    //el hashmap que usa para moverse
    @Override
    public void respawned() {
        moverse = new HashMap<>();
        movimientos = new LinkedList<>();
    }

    @Override
    public void newCheese() {
    }

//METODOS NECESARIOS PARA EL RATON
    //Funcion que saca de la lista de movimientos el 
    //contrario del ultimo movimiento realizado
    private Integer sinMovimientos(){
            return movimientos.pop();
    }

    //Funcion que almacena la casilla actual en los hashmap ya nombrados,
    //almacenando en visitados las casillas que no esten con anterioridad
    private void aniadeCelda( Grid celda ){
        int x = celda.getX();
        int y = celda.getY();

        moverse.putIfAbsent( clave(x, y), celda );

        if (visitado.get(clave(x, y)) == null) {
            visitado.put(clave(x, y), celda);
            incExploredGrids();
        }
    }
    
    //Crea la clave para los HashMap
    private Integer clave( int x, int y ){
        return (x*10000+y);
    }

    //Funcion que comprueba si tomando un camino se va a acceder a una casilla
    //ya explorada, en ese caso devuelve false, el resto de veces true.
    //Lo hace mediante la comprobacion de la casilla en el hashmap moverse.
    private Boolean seHaVisitado(Integer direccion, Grid celda ){
        int x = celda.getX();
        int y = celda.getY();

        switch ( direccion ){
            case Mouse.UP:
                y++;
                break;
            case Mouse.DOWN:
                y--;
                break;
            case Mouse.LEFT:
                x--;
                break;
            case Mouse.RIGHT:
                x++;
                break;
        }
        return !(moverse.get(clave( x, y)) == null);
    }

    //Funcion que comprueba los posibles movimientos del raton desde la celda
    //que se le pasa. Va comprobando si hay algun movimiento que acceda a una
    //casilla no visitado, en cuyo caso lo devuelve.
    //En caso de que no haya moviminetos o no se pueda acceder a ninguna casilla
    //sin explorar devuelve la clade de SIN_MOVIMIENTOS
    private Integer posiblesMovimientos( Grid celda ){
        ArrayList<Integer> posibles = new ArrayList<>();
        if ( celda.canGoDown() ) posibles.add( Mouse.DOWN) ;
        if ( celda.canGoUp() ) posibles.add( Mouse.UP) ;
        if ( celda.canGoLeft() ) posibles.add( Mouse.LEFT) ;
        if ( celda.canGoRight() ) posibles.add( Mouse.RIGHT) ;
        if ( !posibles.isEmpty() ){
            for (int i = 0; i < posibles.size(); i++) {
                Integer movimiento = posibles.get(i);
                if ( !seHaVisitado( movimiento, celda ) ){
                    movimientos.push(contrario(movimiento));
                    return movimiento;
                }
            }
        }
        return SIN_MOVIMIENTOS;
    }

    //Devuelve el movimiento contrario al que se le pasa
    private Integer contrario( Integer direccion ){
        Integer contrario = null;

        switch ( direccion ){
            case Mouse.UP:
                contrario = Mouse.DOWN;
                break;
            case Mouse.LEFT:
                contrario = Mouse.RIGHT;
                break;
            case Mouse.RIGHT:
                contrario = Mouse.LEFT;
                break;
            case Mouse.DOWN:
                contrario = Mouse.UP;
                break;
        }
        return contrario;
    }
    
    //Funcion que se encarga de que el raton ponga bombas,
    //si quedan bombas entonces hace unas comprobaciones abajo descritas para
    //determinar si pone o no una bomba.
    private boolean ponerBomba(Grid celda){
        
        if(quedanBomba != 5){
            /**
            * Como nos piden que el raton sea competitivo, si esta en una 
            * casilla con 3 o mas movimientos disponibles, le quedan bombas 
            * por poner y no ha puesto ya una bomba es esta casilla, 
            * entonces pone una bomba. 
            * Para que sea mas competitivo y no las ponga todas
            * de golpe, tiene que acertear un numero que se
            * a elegido aleatoriamente.
            */
            Random random = new Random();

            ArrayList<Integer> posibles = new ArrayList<>();

            if ( celda.canGoDown() ) posibles.add( Mouse.DOWN) ;
            if ( celda.canGoUp() ) posibles.add( Mouse.UP) ;
            if ( celda.canGoLeft() ) posibles.add( Mouse.LEFT) ;
            if ( celda.canGoRight() ) posibles.add( Mouse.RIGHT) ;
            
            if(posibles.size() >= 3){   
                
                if(celda != lastGrid){
                    
                    int ponerBomba = random.nextInt(10);
                    if(ponerBomba == 4){
                        
                        lastGrid = celda;                       
                        quedanBomba++;
                        return true;
                    }   
                } 
            }
        }
        
        return false;
    }
}
