package Modelo;

import java.util.UUID;

public class ContaClass {
    
    private Integer codigo;
    private String nome;
    private int agencia;
    private int numero;
    private Double saldo;
    
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
    
}
