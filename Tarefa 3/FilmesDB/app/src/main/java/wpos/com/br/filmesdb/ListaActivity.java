package wpos.com.br.filmesdb;

import java.util.List;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import wpos.com.br.filmesdb.bean.Generos;
import wpos.com.br.filmesdb.dao.GenerosDAO;

public class ListaActivity extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        GenerosDAO drDAO = new GenerosDAO(this);
        List<Generos> lista = drDAO.selectAll();

        for (Generos oDr1 : lista) {
            final LinearLayout linha = new LinearLayout(this);
            linha.setOrientation(0);


            final TextView colTitulo = new TextView(this);
            colTitulo.setWidth(250);
            colTitulo.setText(oDr1.getTitulo());


            final TextView colDiretor = new TextView(this);
            colDiretor.setWidth(250);
            colDiretor.setText(oDr1.getDiretor());


            TextView colanoLancamento = new TextView(this);
            colanoLancamento.setWidth(250);
            colanoLancamento.setText(String.valueOf(oDr1.getAnoLancamento()));


            final TextView colGenero = new TextView(this);
            colGenero.setWidth(250);
            colGenero.setText(oDr1.getTipoGeneros().getNome());


            linha.addView(colTitulo);
            linha.addView(colDiretor);
            linha.addView(colanoLancamento);
            linha.addView(colGenero);


            linearLayout.addView(linha);
        }
    }
}
