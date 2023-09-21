/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadorafin;
import java.util.ArrayList;
/**
 *
 * @author LourdesGutierrez
 * @author DiegoFedericoRomero
 * @author JoséRodrigoGonzález
 *
 */
/**
* <pre>
* Clase Calculadora
*
* Contiene métodos para revisar la válidez de una expresión aritmética.
* También contiene métodos para evaluar expresiones aritméticas
* y obtener la notación postfija.
* </pre>    
 */
public class Calculadora {
    
    //1. Verificar validez de operacion
    /**
     * Método para verificar la validez de una operación
     * @param operacion String con la ecuación
     * @return <ul>
     * <li>true: la operación es válida</li>
     * <li>false: la operación no es válida</li>
     * </ul>
     */
    public static boolean verificaValidezOperacion(String operacion){
        boolean resp=false;
        if(operadorSinComplemento(operacion) && verificarDecimales(operacion) && !operadorConsecutivo(operacion) && revisarParentesis(operacion))
            resp=true;
        
        return resp;
    }

//2. Convertir la expresion a su equivalente en notacion postfija 
    /**
     * Método para convertir una expresión a su notación postfija
     * @param operacion String con la ecuación
     * @return expresión en su notación postfija
     */
    public static String infijaPostfija (String operacion){
        StringBuilder resultado= new StringBuilder();
        PilaA <Character> pilaOp= new PilaA();
        int i=0;
        
       if(verificaValidezOperacion(operacion)){
            while(i< operacion.length()){
                char car=operacion.charAt(i); 
                
                //verificar si es un numero
                if(Character.isDigit(car)|| car== '.'){
                    while(i< operacion.length() && Character.isDigit(car) || car== '.'){
                        resultado.append(car);
                        i++;
                        if(i < operacion.length()){
                            car=operacion.charAt(i);
                        }
                    }
                    resultado.append(" ");
                }
                //verificar si es '('
                else if(car=='('){ //verificar si es '('
                    pilaOp.push(car);
                    i++;
                }
                //verificar si es ')'
                else if(car==')'){
                    while(!pilaOp.isEmpty() && pilaOp.peek() != '('){
                        resultado.append(pilaOp.pop()).append(" ");
                    }
                    //quitar '('
                    if(!pilaOp.isEmpty() && pilaOp.peek() == '('){
                        pilaOp.pop();
                    }
                    i++;
                }
                //verificar si es un operador 
                else if(esOperador(car)){
                    while(!pilaOp.isEmpty() && jerarquiaOperadores(car)<=jerarquiaOperadores(pilaOp.peek())){
                        resultado.append(pilaOp.pop()).append(" ");   
                    }
                    pilaOp.push(car);
                    i++;
                }
            }
            //vaciar ultimo dato pila
            while(!pilaOp.isEmpty()){
                    resultado.append(pilaOp.pop()).append(" ");
            } 
       }
       return resultado.toString();
    } 
    
//3. Evaluar la expresion para conocer la su resultado global  
    /**
     * Método para evaluar la expresión en su notación postfija
     * @param operacion String con la ecuación postfija
     * @return resultado de la expresión
     */
    public static Double resultadoGlobal(String operacion){
        double num1,num2,resp;
        
        PilaA <Double> pila= new PilaA();
        String[] tokens = operacion.split("\\s+");
        String token;
       
        
        for(int i=0;i<tokens.length;i++){
            token= tokens[i];
            
            if(esNumero(token)){
                pila.push(Double.parseDouble(token));   
            }
            else if (esOperador(token.charAt(0))) {
                num2=pila.pop();
                num1=pila.pop();
                resp= funcionDelOperador(num1,num2,token);
                pila.push(resp);
            }    
        }
        if(pila.isEmpty()){
            throw new RuntimeException("Expresion invalida");
        }
        
        return pila.pop();
    }
    
//Metodos necesarios para validar una operacion 
    
    //Metodo que revisa que los parentesis de una operacion esten balanceados
    /**
     * Método para revisar que los parentesis esten balanceados
     * @param operacion String con la ecuación
     * @return <ul>
     * <li>true: los parentesis estan balanceados</li>
     * <li>false: los parentesis no estan balanceados</li>
     * </ul>
     */
    public static boolean revisarParentesis(String operacion){
         PilaA<Character> pila = new PilaA<Character>();

        for (int i = 0; i < operacion.length(); i++) {
            char car = operacion.charAt(i);

            if (car == '(') {
                pila.push(car);
            } else if (car == ')') {
                if (pila.isEmpty()) {
                    return false; 
                }
                pila.pop(); 
            }
        }

        return pila.isEmpty(); // La pila debe estar vacía al final si los paréntesis están balanceados.
    }
    
    //Metodo que revisa que no haya dos operadores seguidos 
    /**
     * Método que revisa que no haya dos operadores seguidos
     * @param operacion String con la ecuación
     * @return <ul>
     * <li>true: si es que hay 2 operadores juntos</li>
     * <li>false: si es que no hay 2 operadores juntos</li>
     * </ul>
     */
    public static boolean operadorConsecutivo(String operacion){
        boolean resp=false;
        int i;
        char car;
        char carAntes='0';
        
        for(i=0;i< operacion.length();i++){
            car=operacion.charAt(i);
              if(i>0 && esOperador(car)==true && esOperador(carAntes)==true){
                 resp=true; 
              }
            carAntes=car;
         }
       
        return resp;
    }
    
    //Metodo que revisa que numeros decimales sean validos (ej. número no válido: 1.4.4)
    /**
     * Método que revisa que números decimales sean válidos
     * @param operacion String con la ecuación
     * @return <ul>
     * <li>true: si es que hay 2 operadores juntos</li>
     * <li>false: si es que no hay 2 operadores juntos</li>
     * </ul>
     */
     public static boolean verificarDecimales(String operacion){ 
        boolean resp=true;
        char car;
        int contaDecimal,i;
        i=0;
        contaDecimal=0;
        
        while(i<operacion.length() && contaDecimal<2){
            car=operacion.charAt(i);
            if(Character.isDigit(car)){
              i++;  
            }
            else if(car=='.'){
                contaDecimal++;
                i++;  
            }
            else if(esOperador(car) || car=='(' || car==')'){
                contaDecimal=0;
                i++;
            }
        }
        if(contaDecimal>=2){
            resp=false;
        }
        return resp;
    } 
    
    //Metodo que revisa que no haya operaciones incompletas (ej operacion incompleta: 4+)
     /**
     * Método que revisa si es que la ecuación acaba o termina con operadores
     * @param operacion String con la ecuación
     * @return <ul>
     * <li>true: la expresión no empieza con un operador inválido o no termina en operador</li>
     * <li>false: la expresión empieza con un operador inválido o termina en operador</li>
     * </ul>
     */
    public static boolean operadorSinComplemento(String operacion){
        boolean resp=true;
        int i = 0;
       
        if(!operadorConsecutivo(operacion)){
            if(esOperador(operacion.charAt(i))){
                switch (operacion.charAt(i)){
                    case '+':
                        break;
                    case '-':
                        break;
                    case '*':
                        resp = false;
                        break;
                    case '/':
                        resp = false;
                        break;
                    case '^':
                        resp = false;
                        break;
                }
            }
            if(esOperador(operacion.charAt(operacion.length()-1))){
                switch (operacion.charAt(operacion.length()-1)){
                    case '+':
                        resp = false;
                        break;
                    case '-':
                        resp = false;
                        break;
                    case '*':
                        resp = false;
                        break;
                    case '/':
                        resp = false;
                        break;
                    case '^':
                        resp = false;
                        break;
                }
            }
        }
        return resp;
    }

//Metodos necesarios para realizar las operacioes
    
    //Metodo que verfica que sea operador 
    /**
     * Método que revisa si un char es operador o no
     * @param car un char por parte del usuario
     * @return <ul>
     * <li>true: es un operador</li>
     * <li>false: no es operador</li>
     * </ul>
     */
    public static boolean esOperador(char car) {
        boolean resp=false;
        
        if(car == '+' || car == '-' || car == '*' || car == '/' || car == '^')
            resp=true;
     
        return resp;   
    }
    
    //Metodo que verifica que el token es un numero
    /**
     * Método que revisa si un token puede ser convertido a un valor double
     * @param token el token que sera o no convertido
     * @return <ul>
     * <li>true: el token se pudo convertir a un valor double</li>
     * <li>false: el token no se pudo convertir a un valor double</li>
     * </ul>
     */
    public static boolean esNumero(String token) {
    
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    //Método que asigna funciones a los operadores y que checa que sea un operador valido
    /**
     * Método que realiza operaciones de 2 en 2
     * @param num1 primer número a considerar
     * @param num2 segundo número a considerar
     * @param operador operación que sucederá entre los doubles
     * @return el resultado de la operacion entre los 2 numeros
     */
    public static double funcionDelOperador(double num1,double num2, String operador){  
        double operacion =0;
        
        switch (operador) {
            case "+":
                operacion= num1+num2;
                break;
            case "-":
                operacion=num1-num2;
                break;
            case "*":
                operacion= num1*num2;
                break;
            case "/":
                if (num2!= 0) {
                    operacion= num1/num2;
                }else{
                     throw new RuntimeException("Se intento una division entre 0");
                }
                break;
            case "^":
                operacion=Math.pow(num1,num2);
                break;
            default:throw new RuntimeException("No es valido el operador");
        }
        
        return operacion;    
    }
    
    //Método que asigna jerarquia de operaciones
    /**
     * Método que revisa que operador tiene prioridad
     * @param operador un operador en forma de char
     * @return <ul>
     * <li>1: en caso de ser un + o un -</li>
     * <li>2: en caso de ser un * o un /</li>
     * <li>3: en caso de ser una ^</li>
     * </ul>
     */
    public static int jerarquiaOperadores(char operador){
        int jerarquia=0;
 
        switch (operador) {
            case '+':
                 jerarquia=1;
                 break;
            case '-':
                jerarquia=1; 
                break;
            case '*':
                jerarquia=2;
                 break;
            case'/':
                jerarquia=2;
                 break;
            case '^':
                jerarquia=3;
                 break;        
        }    
        return jerarquia;
    }
 
    //Metodo que balancea un numero negativo( ej: -38 se convertira a 0-38) 
    /**
     * Método que en caso de que haya un número negativo le agregue un cero a la izquierda
     * @param operacion String con la ecuación
     * @return devuelve la operación con los ceros agregados
     */
    public static String corregirExpresionNegativa(String operacion) {
        StringBuilder operacionCorregida=new StringBuilder();
        boolean inicioDeExpresion=true;

        for (int i=0;i<operacion.length();i++) {
            char car=operacion.charAt(i);

            if(car=='-' && inicioDeExpresion) {
                operacionCorregida.append("0");
            }
            operacionCorregida.append(car);
            inicioDeExpresion=(esOperador(car)|| car == '(');
        }
        return operacionCorregida.toString();
    }
}
