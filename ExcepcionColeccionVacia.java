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
public class ExcepcionColeccionVacia extends RuntimeException{
    public ExcepcionColeccionVacia(){   
    }
    public ExcepcionColeccionVacia(String message){
        super(message);
    }
}
