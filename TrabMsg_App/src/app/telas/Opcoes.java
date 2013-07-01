/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import app.objeto.ItemOpcao;
import java.util.ArrayList;

/**
 *
 * @author Guilherme Gehling
 */
public class Opcoes extends Activity implements OnItemClickListener {

    private ListView listView;
    private Lista_opcao lista;
    private ArrayList<ItemOpcao> itens;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.opcoes);

        listView = (ListView) findViewById(R.id.list);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        createListView();
    }

    public void onItemClick(AdapterView<?> av, View view, int i, long l) {
        //Pega o item que foi selecionado.
        ItemOpcao item = lista.getItem(i);
        if ("Nova Msg".equals(item.getAcao())) {
            Intent intent = new Intent(this, FrmNovaMsg.class);
            startActivity(intent);
        }
        if ("Recebidas".equals(item.getAcao())) {
            Intent intent = new Intent(this, ListMsgRecebidas.class);
            startActivity(intent);
        }
        if ("Enviadas".equals(item.getAcao())) {
            Intent intent = new Intent(this, ListMsgEnviadas.class);
            startActivity(intent);
        }

        //Demostração
        //Toast.makeText(this, "Você Clicou em: " + item.getAcao(), Toast.LENGTH_LONG).show();
    }

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemOpcao>();
        ItemOpcao item1 = new ItemOpcao("Nova Msg");
        ItemOpcao item2 = new ItemOpcao("Recebidas");
        ItemOpcao item3 = new ItemOpcao("Enviadas");

        itens.add(item1);
        itens.add(item2);
        itens.add(item3);

        //Cria o adapter
        lista = new Lista_opcao(this, itens);

        //Define o Adapter
        listView.setAdapter(lista);
        //Cor quando a lista é selecionada para ralagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }
}
