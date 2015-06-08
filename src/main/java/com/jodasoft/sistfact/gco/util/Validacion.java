/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.util;

import com.jodasoft.sistfact.gco.util.exp.CedulaInvalidaException;



/**
 *
 * @author wilo
 */
public class Validacion {

    public Validacion() {
    }
    

    public static void validadarCed_RUC(String RUC) throws CedulaInvalidaException{
        if (RUC.length() == 10 || RUC.length() == 13) {
            System.out.println("Va a 1......................");
            boolean validadorDeCedula = validadorDeCedula(RUC);
            if (validadorDeCedula == false) 
            {
                System.out.println("Va a 2......................");
                validadorDeCedula = validadorDePerJuridica(RUC);
                if (validadorDeCedula == false) 
                {
                    System.out.println("Va a 3......................");
                    validadorDeCedula = validadorDeEmpresa(RUC);
                    
                }
            }
            if(validadorDeCedula==false)
                throw new CedulaInvalidaException("La cédula ingresada es incorrecta");
        } else {
            throw new CedulaInvalidaException("La cédula ingresada es incorrecta");
        }
    }

    public static boolean validadorDeEmpresa(String ruc) {

        int prov = Integer.parseInt(ruc.substring(0, 2));
        boolean val = false;

        if (!((prov > 0) && (prov <= 24))) {
            return val;
        }

        int v1, v2, v3, v4, v5, v6, v7, v8, v9;
        int sumatoria;
        int modulo;
        int digito;
        int sustraendo;
        int[] d = new int[ruc.length()];

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(ruc.charAt(i) + "");
        }

        v1 = d[0] * 3;
        v2 = d[1] * 2;
        v3 = d[2] * 7;
        v4 = d[3] * 6;
        v5 = d[4] * 5;
        v6 = d[5] * 4;
        v7 = d[6] * 3;
        v8 = d[7] * 2;
        v9 = d[8];

        sumatoria = v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8;
        modulo = sumatoria % 11;
        sustraendo = modulo * 11;
        if(modulo==0 && d[8]==0)
            return true;
        
        digito = 11 - (modulo);

        System.out.println("Digito RUC       –> "+digito);
        System.out.println("Digito Calculado –> "+v9);

        if (digito ==v9)
            val = true;
        else{
            val = false;
        }
        return val;
    }

    public static boolean validadorDePerJuridica(String ruc) {

        int prov = Integer.parseInt(ruc.substring(0, 2));
        int[] coeficientes = {4,3,2,7,6,5,4,3,2};
         int constante = 11;

        if (!((prov > 0) && (prov <= 24))) {
            System.out.println("Error: ruc ingresada mal");
            return false;
        }

        int[] d = new int[10];
        int suma = 0;

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(ruc.charAt(i) +"");
        }
        
        for(int i=0; i<(d.length-1); i++) 
        {
            d[i] = d[i]*coeficientes[i];
            suma += d[i];
        }

        System.out.println("Suma es: " + suma);
        int aux, resp;

        aux = suma%constante;
        
        if(aux==0 && d[9]==0)
            return true;
        
        resp=constante-aux;

        resp = (resp == 10) ? 0 : resp;

        System.out.println("Aux: " + aux);
        System.out.println("Resp " + resp);
        System.out.println("d[9] " + d[9]);

        if (resp == d[9])
            return true;
        else{
            return false;
        }
    }

    public static boolean validadorDeCedula(String RUC) {
        boolean cedulaCorrecta = false;
        String cedula;
        try {
            if (RUC.length() == 10) {
                cedula = RUC;
            } else {
                cedula = RUC.substring(0, 10);
            }

            int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
            if (tercerDigito < 6) {
                int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                int verificador = Integer.parseInt(cedula.substring(9, 10));
                int suma = 0;
                int digito = 0;
                for (int i = 0; i < (cedula.length() - 1); i++) {
                    digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                    suma += ((digito % 10) + (digito / 10));
                }
                if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                    cedulaCorrecta = true;
                } else if ((10 - (suma % 10)) == verificador) {
                    cedulaCorrecta = true;
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException e) {
            cedulaCorrecta = false;
        } catch (Exception e) {
            cedulaCorrecta = false;
        }
        return cedulaCorrecta;
    }
}
