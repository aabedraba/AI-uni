package Alumno;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.stream.Stream;
/**
 *
 * @author Sergio
 */
public class Main {
    
    public void Leer(){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Variables que voy a necesitar para los distintos calculos.
        String cent="0123456789";
        int i=0,a=0,n=1,c=0;
        boolean q=true;
        
        try{
            //Creo las variables para leer y escribir archivos.
            BufferedReader rb = new BufferedReader(new FileReader("datos.txt"));
            BufferedWriter wb = new BufferedWriter(new FileWriter("pares.txt"));
            
            String k = rb.readLine();
            
            while(q){              
                while(a<10){
                    if(k.charAt(i) == cent.charAt(a)){
                        String SubCadena = k.substring(i,(i+8));
                        n = Integer.parseInt(SubCadena);
                        if(n%2 == 0){
                            wb.write(k);
                            wb.newLine();
                            System.out.println(k);        
                        }
                        a=10;
                    }
                    a++;
                }
                a=0;
                if(i < k.length()){
                    i++;
                }  
                if(n!=1){
                    k = rb.readLine();
                    c++;
                    i=0;
                    n=1;
                }
                if(c==8){
                    q=false;
                }
            }
            rb.close();
            wb.close();
            
        }catch(IOException | NumberFormatException e){
             System.out.println("Error de E/S: "+e);
        }
    }
}
    
    

