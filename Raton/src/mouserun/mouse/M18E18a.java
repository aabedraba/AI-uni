package mouserun.mouse;

import mouserun.game.Cheese;
import mouserun.game.Grid;
import mouserun.game.Mouse;

import java.util.*;

public class M18E18a extends Mouse {

    Grid celdaActual;
    HashMap<Integer, Grid> celdasPez;
    HashMap<Integer, Grid> celdasElefante;
    LinkedList<Integer> movimientos;
    int quedanBombas;
    private final Integer SIN_MOVIMIENTOS = 0;

    public M18E18a(){
        super("Explorador");
        this.celdasPez = new HashMap<>();
        this.celdasElefante = new HashMap<>();
        this.movimientos = new LinkedList<>();
    }

    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        celdaActual = currentGrid;
        aniadeCasilla();
        
        if(quedanBombas != 5){
            
        }
        Integer movimiento = posiblesMovimientos();
        if ( movimiento != SIN_MOVIMIENTOS )
            return movimiento;
        else
            return sinMovimientos();
    }

    private Integer sinMovimientos(){
        if ( !movimientos.isEmpty() ){
            return movimientos.pop();
        } else {
            celdasPez = new HashMap<>();
        }
        return Mouse.BOMB;
    }

    private void aniadeCasilla(){
        int x = celdaActual.getX();
        int y = celdaActual.getY();

        if (celdasPez.get(clave(x, y)) == null)
            celdasPez.put(clave(x, y), celdaActual);

        if (celdasElefante.get(clave(x, y)) == null) {
            celdasElefante.put(clave(x, y), celdaActual);
            incExploredGrids();
        }
    }

    private Integer clave( int x, int y ){
        return (x*10000+y);
    }

    private Boolean seHaVisitado(Integer direccion ){
        int x = celdaActual.getX();
        int y = celdaActual.getY();

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

        return !(celdasPez.get(clave( x, y)) == null);
    }

    public Integer posiblesMovimientos(){
        ArrayList<Integer> posibles = new ArrayList<>();
        if ( celdaActual.canGoDown() ) posibles.add( Mouse.DOWN) ;
        if ( celdaActual.canGoUp() ) posibles.add( Mouse.UP) ;
        if ( celdaActual.canGoLeft() ) posibles.add( Mouse.LEFT) ;
        if ( celdaActual.canGoRight() ) posibles.add( Mouse.RIGHT) ;
        if ( !posibles.isEmpty() ){
            for (int i = 0; i < posibles.size(); i++) {
                Integer movimiento = posibles.get(i);
                if ( !seHaVisitado( movimiento ) ){
                    movimientos.push(contrario(movimiento));
                    return movimiento;
                }
            }
        }
        return SIN_MOVIMIENTOS;
    }
    @Override
    public void respawned() {
        celdasPez = new HashMap<Integer, Grid>();
        movimientos = new LinkedList<>();
    }

    @Override
    public void newCheese() {
        
    }

    private Integer contrario( Integer movimiento ){
        Integer contrario = null;

        switch ( movimiento ){
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
