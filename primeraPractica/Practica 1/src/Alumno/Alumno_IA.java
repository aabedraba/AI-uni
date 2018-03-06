package Alumno;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Scanner;

/**
 *
 * @author Sergio
 */
public class Alumno_IA extends Alumno{
    private String gpracticas;
    private int nota;
    
    public String getGpracticas() {
        return gpracticas;
    }

    public void setGpracticas(String gpracticas) {
        this.gpracticas = gpracticas;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
    
    //Funcio que calcula la nota media de 4 notas introducidas por teclado.
    public void MNota (){
        System.out.println("Para la media se necesitan 4 notas: \n");
        System.out.println("Introduzca la primera nota: \n");
        int n1 = 0;
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        n1 = entradaEscaner.nextInt(); //Invocamos un método sobre un objeto Scanner
        System.out.println("Introduzca la segunda nota: \n");
        int n2 = 0;
        n1 += n2;
        n2 = entradaEscaner.nextInt(); //Invocamos un método sobre un objeto Scanner
        System.out.println("Introduzca la tercera nota: \n");
        n2 = entradaEscaner.nextInt(); //Invocamos un método sobre un objeto Scanner
        n1 += n2;
        System.out.println("Introduzca la cuarta nota: \n");
        n2 = entradaEscaner.nextInt(); //Invocamos un método sobre un objeto Scanner
        n1 += n2;
        n1= n1 / 4;
        this.setNota(n1);
        System.out.println("La nota media de las practicas introducidas es: " + this.getNota() + " \n");
    }
}
