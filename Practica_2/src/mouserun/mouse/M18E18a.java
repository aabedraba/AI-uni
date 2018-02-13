package mouserun.mouse;

import mouserun.game.*;
import java.util.*;

//TODO eliminar mensajes depuración

public class M18E18a extends Mouse {

    private ArrayList<Grid> celdasVisitadas;
    /**
     * Constructor, Hola
     */
    public M18E18a(){
        super("Ratotuille");
        this.celdasVisitadas = new ArrayList<>();

    }

    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        celdasVisitadas.add( currentGrid );

        ArrayList<Integer> possibleMoves = new ArrayList<>();
        if (currentGrid.canGoUp()) possibleMoves.add(Mouse.UP);
        if (currentGrid.canGoDown()) possibleMoves.add(Mouse.DOWN);
        if (currentGrid.canGoLeft()) possibleMoves.add(Mouse.LEFT);
        if (currentGrid.canGoRight()) possibleMoves.add(Mouse.RIGHT);
        possibleMoves.add(Mouse.BOMB);

        return Mouse.UP;
    }

    public boolean busqueda( Grid currentGrid ){
        for (int i = 0; i < celdasVisitadas.size(); i++) {
            if ( celdasVisitadas.get(i).getX() == currentGrid.getX()+1 ) return false;
            if ( celdasVisitadas.get(i).getY() == currentGrid.getY()+1 ) return false;
            if ( celdasVisitadas.get(i).getX() == currentGrid.getX()-1 ) return false;
        }
        return true;
    }

    @Override
    public void newCheese() {

    }

    @Override
    public void respawned() {

    }
}
