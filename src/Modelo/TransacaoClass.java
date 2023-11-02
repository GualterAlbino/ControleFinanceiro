package Modelo;

public class TransacaoClass {

    private Integer codigo;
    private Integer codigoConta;
    private String descricao;
    private String tipo;
    private Double valor;
    private String data;

    public TransacaoClass() {
    }

    public TransacaoClass(Integer codigoConta, String descricao, String tipo, Double valor, String data) {
        this.codigoConta = codigoConta;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    public String[] toArray() {
        String[] array = new String[5];
        array[0] = codigo.toString();
        array[1] = codigoConta.toString();
        array[2] = descricao;
        array[3] = tipo;
        array[4] = valor.toString();
        array[5] = data;

        return array;

    }

    public TransacaoClass arrayTo(String[] array) {
        codigo = Integer.parseInt(array[0]);
        codigoConta = Integer.parseInt(array[1]);
        descricao = array[2];
        tipo = array[3];
        valor = Double.parseDouble(array[4]);
        data = array[5];

        return this;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoConta() {
        return codigoConta;
    }

    public void setCodigoConta(Integer codigoConta) {
        this.codigoConta = codigoConta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
