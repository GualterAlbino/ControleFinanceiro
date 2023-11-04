package Modelo;

import java.util.Calendar;

public class ExtratoClass {

    private int codigo;
    private Calendar data;
    private ContaClass conta;
    private String descricao;
    private double valor;

    public ExtratoClass(Calendar data, ContaClass conta, String descricao, double valor) {
        this.codigo = 0;
        this.data = data;
        this.conta = conta;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public ContaClass getConta() {
        return conta;
    }

    public void setConta(ContaClass conta) {
        this.conta = conta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
