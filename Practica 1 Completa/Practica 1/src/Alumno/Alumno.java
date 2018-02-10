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
public class Alumno {
    protected String Nombre,DNI,email;
    
    //Constructor generico que inicializa los datos con valores fijos.
    public Alumno(){
        this.Nombre="";
        this.DNI="00000000A";
        this.email="";
    }
    
    //Constructor que inicializa el objeto con los valores que se le pasen.
    public Alumno(String n , String dni,String e){
        this.Nombre=n;
        this.DNI=dni;
        this.email=e;
    }
    
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //Funcion que crea un alumno mediante datos introducidos por teclado.
    public void CreaAlumno (){
        System.out.printf("Para crear un alumno debe introducir los siguientes datos: \n");
        System.out.printf("Nombre del alumno: \n");
        String nombre = "";
        String dni = "";
        String email = "";
        Scanner entradaEscaner = new Scanner (System.in);
        nombre = entradaEscaner.nextLine ();
        System.out.printf("D.N.I. del alumno: \n");
        dni = entradaEscaner.nextLine ();
        System.out.printf("E-mail del alumno: \n");
        email = entradaEscaner.nextLine ();
        this.setNombre(nombre);
        this.setDNI(dni);
        this.setEmail(email);
        System.out.printf("Alumno creado. \n");
    }
    
        //Funcion que muestra los datos del alumno que la llama.
    public void MuestraAlumno(){
        System.out.println("Datos del alumno: \n");
        System.out.println("-Nombre: "+this.getNombre()+"\n");
        System.out.println("-DNI: "+this.getDNI()+"\n");
        System.out.println("-Email: "+this.getEmail()+"\n");
        System.out.println("------------------------------------------------ \n");
    }
}

