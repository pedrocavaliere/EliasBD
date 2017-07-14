package pedrokuchpil.bancodedados2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;

    private ListView listaNomes;
    // criando um Adapter para o listView
    private ArrayAdapter<String> itensAdaptador;
    // criando as listas para uso no listView
    private ArrayList<String> nomes;
    private ArrayList<Integer> ids;

    private void recuperarNomes() {
        try {
            // Atualizar em ordem decrescente
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM agenda ORDER BY nome", null);
            // criar as listas para listView
            nomes = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    nomes);
            listaNomes.setAdapter(itensAdaptador);
            // recuperar as colunas
            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunaNome = cursor.getColumnIndex("nome");
            //listar tarefas
            cursor.moveToFirst();
            while (cursor != null) {
                nomes.add(cursor.getString(indiceColunaNome));
                //--- para remover o item selecionado
                ids.add(Integer.parseInt(cursor.getString(indiceColunaId)));
                //---------------
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listaNomes = (ListView) findViewById(R.id.listaNomesId);

        bancoDados = openOrCreateDatabase("appAgenda", MODE_PRIVATE, null);

        recuperarNomes();

        listaNomes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main2Activity.this, nomes.get(0), Toast.LENGTH_SHORT ).show();
            }
        });

    }

}
