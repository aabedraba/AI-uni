package mouserun.mouse;

import mouserun.game.*;
import java.util.*;

//TODO eliminar mensajes depuración

public class M18E18a extends Mouse {

    private ArrayList<Grid> celdasVisitadas;
    /**
     * Constructor
     */
    public M18E18a(){
        super("Ratotuille");
        this.celdasVisitadas = new ArrayList<>();

    }

    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        celdasVisitadas.add( currentGrid );

        return busqueda(currentGrid);
    }

    public int busqueda( Grid currentGrid ) {
        boolean control=true;
        if (currentGrid.canGoUp()) {
            for (int i = 0; i < celdasVisitadas.size(); i++) {
                if (celdasVisitadas.get(i).getY() == currentGrid.getY() + 1) control=false;
            }
            if(control) return Mouse.UP;
        }
        if (currentGrid.canGoRight()) {
            for (int i = 0; i < celdasVisitadas.size(); i++) {
                if (celdasVisitadas.get(i).getX() == currentGrid.getX() + 1) control=false;
            }
            if(control) return Mouse.RIGHT;
        }
        if (currentGrid.canGoLeft()) {
            for (int i = 0; i < celdasVisitadas.size(); i++) {
                if (celdasVisitadas.get(i).getX() != currentGrid.getX() - 1) control=false;
            }
            if(control) return Mouse.LEFT;
        }
        return Mouse.DOWN;
    }

    @Override
    public void newCheese() {

    }

    @Override
    public void respawned() {

    }
}
