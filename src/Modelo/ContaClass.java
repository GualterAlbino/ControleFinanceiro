package Modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class ContaClass {

    private Integer codigo;
    private String nome;
    private int agencia;
    private int numero;
    private Double saldo;
    private ArrayList<ExtratoClass> extrato;
    private String registroSelecionado;

    public ContaClass() {
    }

    public ContaClass(String nome, int agencia, int numero, Double saldo) {
        this.nome = nome;
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = saldo;
    }

    public String[] toArray() {
        String[] array = new String[5];
        array[0] = codigo.toString();
        array[1] = nome;
        array[2] = Integer.toString(agencia);
        array[3] = Integer.toString(numero);
        array[4] = saldo.toString();

        return array;

    }

    public ContaClass arrayTo(String[] array) {
        codigo = Integer.parseInt(array[0]);
        nome = array[1];
        agencia = Integer.parseInt(array[2]);
        numero = Integer.parseInt(array[3]);
        saldo = Double.parseDouble(array[4]);

        return this;
    }

    public ArrayList<ExtratoClass> getExtrato() {
        return extrato;
    }

    public void setExtrato(ArrayList<ExtratoClass> extrato) {
        this.extrato = extrato;
    }

    public String getRegistroSelecionado() {
        return registroSelecionado;
    }

    public void setRegistroSelecionado(String registroSelecionado) {
        this.registroSelecionado = registroSelecionado;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public boolean Saque(double vlr) {
        if (vlr <= saldo) {
            extrato.add(new ExtratoClass(Calendar.getInstance(), this, "saque", vlr));
            saldo = saldo - vlr;
            return true;
        } else {
            return false;
        }
    }

    public boolean Deposito(double vlr) {
        if (vlr > 0) {
            extrato.add(new ExtratoClass(Calendar.getInstance(), this, "deposito", vlr));
            saldo = saldo + vlr;
            return true;
        } else {
            return false;
        }
    }

    public int getTamanhoExtrato() {
        return extrato.size();
    }

    public ExtratoClass getExtrato(int index) {
        return extrato.get(index);
    }

    public void SetFromStringArray(String[] conta) {
        if (!(conta[0].compareTo("") == 0)) {
            setCodigo(Integer.parseInt(conta[0]));
        }
        setNome(conta[1]);
        setNumero(Integer.parseInt(conta[2]));
        setAgencia(Integer.parseInt(conta[3]));
        setSaldo(Double.parseDouble(conta[4]));
    }

    public String[] GetToStringArray() {
        Number.class.toString();

        String[] conta = {getCodigo().toString(), getNome(), Integer.toString(getNumero()), Integer.toString(getAgencia()), getSaldo().toString()};
        return conta;
    }

}
