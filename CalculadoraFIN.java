/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculadorafin;

import java.util.ArrayList;

/**
 *
 * @author lulugutierrez
 * 
 */
public class CalculadoraFIN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String operacion="(-1+2^4)*(4+6/2)-7^3";
        Double Resultado;
        
        if(Calculadora.verificaValidezOperacion(operacion)){
           operacion=Calculadora.corregirExpresionNegativa(operacion);
            String expresionPostfija=Calculadora.infijaPostfija(operacion);
            Resultado=Calculadora.resultadoGlobal(expresionPostfija);
            System.out.println("El resultado es: "+Resultado);
        }
        else{
            System.out.println("Operación no válida");
        }
    }
    
}
