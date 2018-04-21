package wpos.com.br.filmesdb.bean;

import java.sql.Date;

public class Generos {


    private int codigo;
    private String titulo;
    private String diretor;
    private int	anoLancamento;
    private TipoGeneros tipoGeneros;


    public Generos()
    {
        super();
    }

    public Generos(int codigo, String titulo, String diretor, int anoLancamento, TipoGeneros tDr) {
        super();
        this.codigo = codigo;
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.tipoGeneros = tDr;

    }



    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getDiretor() {
        return diretor;
    }
    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }


    public int getAnoLancamento() {
        return anoLancamento;
    }
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public TipoGeneros getTipoGeneros() {
        return tipoGeneros;
    }
    public void setTipoDespesaReceita(TipoGeneros tipoGeneros) {
        this.tipoGeneros = tipoGeneros;
    }


}
