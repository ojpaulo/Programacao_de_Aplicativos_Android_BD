package wpos.com.br.filmesdb.dao;



import java.util.ArrayList;
import java.util.List;
import wpos.com.br.filmesdb.bean.TipoGeneros;
import wpos.com.br.filmesdb.dao.FilmesDAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


public class TipoGenerosDAO extends FilmesDAO {
        // Tabela utilizada no SQLite
        private static final String TABLE_NAME = "Genero";
        private Context context;
        private SQLiteDatabase db;
        private SQLiteStatement insertStmt;
        //Comando para realizar a inserção
        private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (codigo, nome) VALUES (?,?)";

        public TipoGenerosDAO(Context context) {
            this.context = context;
            OpenHelper openHelper = new OpenHelper(this.context);
            this.db = openHelper.getWritableDatabase();
            this.insertStmt = this.db.compileStatement(INSERT);
        }

        // Método para inserção
        public long insert(TipoGeneros tipodr) {
            this.insertStmt.bindLong(1, tipodr.getCodigo());
            this.insertStmt.bindString(2, tipodr.getNome());
            return this.insertStmt.executeInsert();
        }

        // Método onde passamos o código e ele retorna um objeto TipoDespesaReceita
// carregado com informações direto do banco de dados
        public TipoGeneros findById(Integer id) {
            TipoGeneros tipodr = new TipoGeneros();
            String selectQuery = "SELECT codigo, nome FROM Genero WHERE codigo =?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{id.toString()
            });
            if (cursor.moveToFirst()) {
                tipodr.setCodigo(cursor.getInt(0));
                tipodr.setNome(cursor.getString(1));

            }
            cursor.close();
            return tipodr;
        }


        // Método apaga tudo (!)
        public void deleteAll() {
            this.db.delete(TABLE_NAME, null, null);
        }

        // Método onde passamos o NOME DA DESPESA/RECEITA e ele retorna um objeto TipoDespesaReceita carregado com informações direto do banco de dados
        public TipoGeneros findByNome(String nome) {
            TipoGeneros tipodr = new TipoGeneros();
            String selectQuery = "SELECT codigo, nome  FROM Genero " + " WHERE nome=?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{nome});
            if (cursor.moveToFirst()) {
                tipodr.setCodigo(cursor.getInt(0));
                tipodr.setNome(cursor.getString(1));

            }
            cursor.close();
            return tipodr;
        }

        // Neste método, passa-se de queremos "RECEITAS" Ou "DESPESAS" e
// retorna-se uma lista com todas as despesas ou receitas
// (é este método que será usado no Spinner)
        public List<TipoGeneros> selectByGeneros() {
            String selectQuery = "SELECT codigo, nome FROM Genero";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{});
            return carregarLista(cursor);
        }

        // Traz todas as despesas/receitas presentes no banco, por uma lista de
// objetos TipoDespesaReceita
        public List<TipoGeneros> selectAll() {
            Cursor cursor = this.db.query(TABLE_NAME, new String[]{"codigo", "nome"}, null, null, null, null, "codigo");
            return carregarLista(cursor);
        }

        // Este método é para carregar a lista recebendo um cursor carregado
// com a consulta do banco. Repare que mais de um método o utiliza, ou seja,
// reaproveitamente de código!
        private List<TipoGeneros> carregarLista(Cursor cursor) {
            List<TipoGeneros> list = new ArrayList<TipoGeneros>();
            if (cursor.moveToFirst()) {
                do {
                    TipoGeneros tipodr = new TipoGeneros(
                            cursor.getInt(0),
                            cursor.getString(1)
                            );
                    list.add(tipodr);
                }
                while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return list;
        }

        // Encerra conexão com o banco.
        public void encerrarDB() {
            this.db.close();
        }

    }



