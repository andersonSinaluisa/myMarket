package com.today.mymarket.Model;

public class Validaciones {
    public static Boolean validaRucEP(String ruc){
        final int num_provincias = 24;
        int prov = Integer.parseInt(ruc.substring(0, 2));
        boolean val = false;

        if (!((prov > 0) && (prov <= num_provincias))) {
            return val;
        }

        Integer v1,v2,v3,v4,v5,v6,v7,v8,v9;
        Integer sumatoria;
        Integer modulo;
        Integer digito;
        Integer sustraendo;
        int[] d = new int[ruc.length()];

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(ruc.charAt(i) + "");
        }

        v1 = d[0]* 3;
        v2 = d[1]* 2;
        v3 = d[2]* 7;
        v4 = d[3]* 6;
        v5 = d[4]* 5;
        v6 = d[5]* 4;
        v7 = d[6]* 3;
        v8 = d[7]* 2;
        v9 = d[8];

        sumatoria = v1+v2+v3+v4+v5+v6+v7+v8;
        modulo = sumatoria % 11;
        sustraendo = modulo * 11;
        digito = 11-(sumatoria - sustraendo);


        if(digito == v9){
            val = true;
        }else
            val = false;
        return val;
    }

    public static boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    int verificador = Integer.parseInt(cedula.substring(9,10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    }
                    else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }
}
