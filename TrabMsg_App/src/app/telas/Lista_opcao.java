/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.telas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import app.objeto.ItemOpcao;
import java.util.ArrayList;

/**
 *
 * @author Guilherme Gehling
 */
public class Lista_opcao extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ItemOpcao> itens;

    public Lista_opcao(Context context, ArrayList<ItemOpcao> itens) {
        this.inflater = LayoutInflater.from(context);
        this.itens = itens;
    }

    public ItemOpcao getItem(int position) {
        return itens.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        ItemOpcao item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = inflater.inflate(R.layout.lista_opcao, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.text)).setText(item.getAcao());

        return view;
    }

    public int getCount() {
        return itens.size();
    }
}