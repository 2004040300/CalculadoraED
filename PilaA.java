/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadorafin;

import java.util.ArrayList;

/**
 *
 * @author lulugutierrez
 * 
 */
public class PilaA <T> implements PilaADT<T>{
    private T[] pila;
    private int tope;
    private final int MAX_PILA= 20;
    
    public PilaA(){
        pila = (T[]) new Object[MAX_PILA];
        tope = -1; //indica que la pila esta vacia
    }
    
    public PilaA(int max){
        pila = (T[]) new Object[max];
        tope = -1; //indica que la pila esta vacia
    }
    @Override
    public void push(T dato) {
        if(tope == pila.length -1){
            expande();
        }
        tope++;
        pila[tope] = dato;
    }
    
    private void expande(){
        T[] masGrande = (T[])new Object[pila.length * 2];
        for(int i = 0; i < pila.length; i++){
            masGrande[i] = pila[i];
        }
        pila = masGrande;
    }

    @Override
    public T pop() {
        if(isEmpty()){
            throw new ExcepcionColeccionVacia("la pila esta vacia");
        }
        T resultado = pila[tope];
        tope--;
        return resultado;
    }

    @Override
    public boolean isEmpty() {
      return tope == -1;
    }

    public T peek() {
         if(isEmpty()){
            throw new ExcepcionColeccionVacia("la pila esta vacia");
        }
    
        return pila[tope];
    }
   
    public String toString(){
        StringBuilder sb= new StringBuilder();
        
        for(int i=0;i<=tope;i++){
            sb.append(pila[i]);
        }
   
        return sb.toString();   
    }
    public void multiPop(int n){
        int i;
        if(tope>= n-1){
            for(i=0;i<n;i++)
                this.pop(); //tope los actualiza el pop
        }
    }

}
