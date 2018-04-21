package wpos.com.br.filmesdb.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import wpos.com.br.filmesdb.bean.Generos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;




public class GenerosDAO extends FilmesDAO {


    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "Filme";
    private Context context;
    private SQLiteDatabase db;
    private TipoGenerosDAO tdrDAO;

    public GenerosDAO (Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        tdrDAO = new TipoGenerosDAO(context);
    }
    // Método para inserção
    public long insert(Generos oDr)
    {
        SQLiteStatement cmdInsert;
        String INSERT = "INSERT INTO " + TABLE_NAME + " (titulo, diretor, anoLancamento, genero) "+"VALUES (?,?,?,?)";
        cmdInsert = this.db.compileStatement(INSERT);
        cmdInsert.bindString(1, oDr.getTitulo());
        cmdInsert.bindString(2, oDr.getDiretor());
        cmdInsert.bindLong  (3, oDr.getAnoLancamento());
        cmdInsert.bindLong(4, oDr.getTipoGeneros().getCodigo());
       // Método útil - podemos logar informações que aparecem no LogCat durante execução!
       // Log.v("SQL", "strftime('%s','"+oDr.getData().getYear()+"-"+(oDr.getData().getMonth() < 10 ? "0"+oDr.getData().getMonth() :
       //         oDr.getData().getMonth())+"-"+(oDr.getData().getDay() < 10 ?"0"+oDr.getData().getDay():oDr.getData().getDay())+"')");
        return cmdInsert.executeInsert();
    }
    // Método apaga tudo (!)


    public void deleteAll()
    {
        this.db.delete(TABLE_NAME, null, null);
    }
    // Traz todas as despesas/receitas presentes no banco, por uma lista de objetos DespesaReceita
    public List<Generos> selectAll()
    {

        List<Generos> list = new ArrayList<Generos>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[] { "codigo", "titulo", "diretor", "anoLancamento ", "genero" }, null, null, null, null, "codigo");
        if (cursor.moveToFirst())
        {
            do
            {
                Generos tipodr = new Generos(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        tdrDAO.findById(cursor.getInt(4))
                  ) ;
                list.add(tipodr);
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return list;
    }
    // Encerra conexão com o banco.
    public void encerrarDB() {
        this.db.close();
    }

}
