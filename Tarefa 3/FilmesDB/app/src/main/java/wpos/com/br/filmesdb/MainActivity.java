package wpos.com.br.filmesdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import wpos.com.br.filmesdb.bean.Generos;
import wpos.com.br.filmesdb.bean.TipoGeneros;
import wpos.com.br.filmesdb.dao.GenerosDAO;
import wpos.com.br.filmesdb.dao.TipoGenerosDAO;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    Spinner spiGenero;
    EditText txtTitulo;
    EditText txtDiretor;
    EditText txtanoLancamento;
    LinearLayout linhaMensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDiretor = (EditText) findViewById(R.id.txtDiretor);
        txtanoLancamento = (EditText) findViewById(R.id.txtanoLancamento);
        spiGenero = (Spinner) findViewById(R.id.spiGenero);

         carregarTipo();
    }


    public void carregarTipo()
    {


        //

        TipoGenerosDAO tdrDAO = new TipoGenerosDAO(this);
        List<TipoGeneros> lista = tdrDAO.selectByGeneros();
        List<String> labels = new ArrayList<String>();
        for(TipoGeneros tdp : lista)
        {
            labels.add(tdp.getNome());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// anexando os Adapters carregados ao Spinner
        spiGenero.setAdapter(dataAdapter);
    }
    public void cadastrarFilme(View v)
    {

        TipoGenerosDAO tdrDAO = new TipoGenerosDAO(this);
        TipoGeneros oTdr = tdrDAO.findByNome(spiGenero.getSelectedItem().toString());

        Generos oDr = new Generos(0, txtTitulo.getText().toString(),txtDiretor.getText().toString(), Integer.parseInt(txtanoLancamento.getText().toString()),  oTdr   );



        GenerosDAO drDAO = new GenerosDAO(this);
        drDAO.insert(oDr);

// Chama a outra atividade
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
    }

}