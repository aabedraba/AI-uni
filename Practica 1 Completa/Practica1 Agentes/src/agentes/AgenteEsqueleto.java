/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class AgenteEsqueleto extends Agent{  
    
    protected void setup(){
        System.out.println("Hola amigos soy "+this.getName()+" y acabo de iniciar mi ejecución.");//hace captura de pantalla.
    }
    
    protected void takeDown(){
        System.out.println("Finaliza la ejecución del agente: "+this.getName());
    }
    
    
}
