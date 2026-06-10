package br.com.nutrifit.service;

import org.springframework.stereotype.Service;

@Service
public class IMCService {

    public double calcularIMC(
            Double peso,
            Double altura) {

        if (peso == null || altura == null || altura == 0) {
            return 0;
        }

        return peso / (altura * altura);
    }

    public String classificarIMC(double imc) {

        if (imc < 18.5) {
            return "Abaixo do peso";
        }

        if (imc < 25) {
            return "Peso normal";
        }

        if (imc < 30) {
            return "Sobrepeso";
        }

        if (imc < 35) {
            return "Obesidade Grau I";
        }

        if (imc < 40) {
            return "Obesidade Grau II";
        }

        return "Obesidade Grau III";
    }
}