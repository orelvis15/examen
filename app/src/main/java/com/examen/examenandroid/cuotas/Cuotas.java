package com.examen.examenandroid.cuotas;

public class Cuotas {

    final String message;

    public Cuotas(String message) {
        this.message = message;
    }

    public String getNumCoutas(){
        return message.split(" ")[0];
    }

    public String getCuotasMonto(){
        return message.split(" ")[4];
    }

    public String getMontoTotal(){
        return message.split(" ")[6].substring(0,message.split(" ")[6].length()-1);
    }

    public String getMessage() {
        return message;
    }
}
