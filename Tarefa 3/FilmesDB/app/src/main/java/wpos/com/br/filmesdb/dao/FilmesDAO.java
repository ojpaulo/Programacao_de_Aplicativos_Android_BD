package wpos.com.br.filmesdb.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FilmesDAO {

    private static final String DATABASE_NAME = "filmesdb.db";
    private static final int DATABASE_VERSION = 1;

    protected static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db)
        {
// Cria as duas tabelas necessárias para armazenar as informações
            db.execSQL("CREATE TABLE Genero (codigo INTEGER PRIMARY KEY AUTOINCREMENT, " + "nome TEXT)");
            db.execSQL("CREATE TABLE Filme (codigo INTEGER PRIMARY KEY AUTOINCREMENT, " + " titulo TEXT, " + "diretor TEXT, anoLancamento  INTEGER, genero INTEGERINTEGER NOT NULL)");
// Cria dois tipos de receitas e dois tipos de despesas.
// Sinta-se à vontade para criar outros!
            db.execSQL("INSERT INTO Genero VALUES (1,'Romance')");
            db.execSQL("INSERT INTO Genero VALUES (2, 'Comédia')");
            db.execSQL("INSERT INTO Genero VALUES (3, 'Ficção Científica')");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int
                newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Genero");
            db.execSQL("DROP TABLE IF EXISTS Filme");
            onCreate(db);
        }
    }


}
