package mouserun.mouse;

import mouserun.game.Cheese;
import mouserun.game.Grid;
import mouserun.game.Mouse;

import java.util.*;


public class Explorador extends Mouse {

    private final Integer SIN_MOVIMIENTOS = 0;

    HashMap<Integer, Grid> memoriaPez;
    HashMap<Integer, Grid> memoriaElefante;
    LinkedList<Integer> movimientos;

    public Explorador(){
        super("Explorador");
        this.memoriaPez = new HashMap<>();
        this.memoriaElefante = new HashMap<>();
        this.movimientos = new LinkedList<>();
    }

    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        aniadeCelda( currentGrid );
        Integer movimiento = posiblesMovimientos( currentGrid );
        if ( movimiento != SIN_MOVIMIENTOS )
            return movimiento;
        else
            return sinMovimientos();
    }

    @Override
    public void respawned() {

    }

    @Override
    public void newCheese() {
        memoriaPez = new HashMap<Integer, Grid>();
        movimientos = new LinkedList<>();
    }

    private Integer sinMovimientos(){
        if ( !movimientos.isEmpty() ){
            return movimientos.pop();
        } else {
            memoriaPez = new HashMap<>();
        }
        return Mouse.BOMB;
    }

    private void aniadeCelda( Grid celda ){
        int x = celda.getX();
        int y = celda.getY();

        if (memoriaPez.get(clave(x, y)) == null)
            memoriaPez.put(clave(x, y), celda);

        if (memoriaElefante.get(clave(x, y)) == null) {
            memoriaElefante.put(clave(x, y), celda);
            incExploredGrids();
        }
    }

    private Integer clave( int x, int y ){
        return (x*10000+y);
    }

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
        return !(memoriaPez.get(clave( x, y)) == null);
    }

    public Integer posiblesMovimientos( Grid celda ){
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
}
