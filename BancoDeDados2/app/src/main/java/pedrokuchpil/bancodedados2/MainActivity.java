package pedrokuchpil.bancodedados2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;

    private EditText textoNome, textoTelefone, textoEmail;
    private Button botao, botaoLista;

    void criarTabela(){
        bancoDados = openOrCreateDatabase("appAgenda", MODE_PRIVATE, null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS agenda (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR (30), celular VARCHAR (12), email VARCHAR (30))");
    }

    void gravarDados(String nome, String telefone, String email){
        bancoDados.execSQL("INSERT INTO agenda (nome, celular, email) VALUES ('"+nome+"', '"+telefone+"', '"+email+"')");
    }

    public void mostrarTabela(){
        try {

            Cursor ponteiro = bancoDados.rawQuery("SELECT * FROM agenda",null);
            int indiceColunaNome = ponteiro.getColumnIndex("nome");
            int indiceColunaCelular = ponteiro.getColumnIndex("celular");
            int indiceColunaEmail = ponteiro.getColumnIndex("email");
            int indiceColunaId = ponteiro.getColumnIndex("id");
            ponteiro.moveToFirst();
            while (ponteiro!= null)
            {
                Log.i("Resultado - ID: ",ponteiro.getString(indiceColunaId));
                Log.i("Resultado - Nome: ",ponteiro.getString(indiceColunaNome));
                Log.i("Resultado - Celular: ",ponteiro.getString(indiceColunaCelular));
                Log.i("Resultado - Email: ",ponteiro.getString(indiceColunaEmail));
                ponteiro.moveToNext();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        criarTabela();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNome = (EditText) findViewById(R.id.textoNomeId);
        textoTelefone = (EditText) findViewById(R.id.textoTelefoneId);
        textoEmail = (EditText) findViewById(R.id.textoEmailId);
        botao = (Button) findViewById(R.id.button);
        botaoLista = (Button) findViewById(R.id.buttonListaId);


        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravarDados(textoNome.getText().toString(), textoTelefone.getText().toString(), textoEmail.getText().toString());
                mostrarTabela();
            }
        });

        botaoLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

    }
}
