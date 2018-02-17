package mouserun.mouse;

import mouserun.game.*;
import java.util.*;

//TODO eliminar mensajes depuración

public class M18E18a extends Mouse {

    private ArrayList<Grid> celdasVisitadas;
    private Grid nextGrid;
    private Grid lastGrid;
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
        ArrayList<Integer> posiblesMov = new ArrayList<Integer>();
		if (currentGrid.canGoUp()) posiblesMov.add(Mouse.UP);
		if (currentGrid.canGoDown()) posiblesMov.add(Mouse.DOWN);
		if (currentGrid.canGoLeft()) posiblesMov.add(Mouse.LEFT);
		if (currentGrid.canGoRight()) posiblesMov.add(Mouse.RIGHT);
        if (posiblesMov.size() == 1){
            lastGrid = currentGrid;
            return posiblesMov.get(0);
        }else{
            for(int i=0;i<posiblesMov.size();i++){
                if(Prueba(posiblesMov.get(i),currentGrid)){
                    lastGrid = currentGrid;
                    return posiblesMov.get(i);
                }      
            }
        }
        for(int i=0;i<posiblesMov.size();i++){
            if(Vuelta(posiblesMov.get(i),currentGrid)){
                lastGrid = currentGrid;
                return posiblesMov.get(i);
            }
        }
        return Mouse.BOMB;
    }

    private boolean Prueba (int direction, Grid currentGrid){
        boolean control=true;
        if (celdasVisitadas.size() == 1){
            return true;
        }else{
            int x = currentGrid.getX();
            int y = currentGrid.getY();
		
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
		
            for (int i = 0; i < celdasVisitadas.size(); i++) {
                    if (celdasVisitadas.get(i).getX() == x &&
                        celdasVisitadas.get(i).getY() == y){
                        control=false;
                    }
                }
                
            return control;
        }
    }
    
    private boolean Vuelta (int direction, Grid currentGrid){
        boolean control=true;
        int x = currentGrid.getX();
        int y = currentGrid.getY();

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
            if (lastGrid.getX() == x && lastGrid.getY() == y){
                control=false;
            }
        return control;
    }
  

    @Override
    public void newCheese() {

    }

    @Override
    public void respawned() {

    }
}
