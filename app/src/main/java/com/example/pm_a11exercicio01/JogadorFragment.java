package com.example.pm_a11exercicio01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm_a11exercicio01.controller.JogadorController;
import com.example.pm_a11exercicio01.controller.TimeController;
import com.example.pm_a11exercicio01.model.Jogador;
import com.example.pm_a11exercicio01.model.Time;
import com.example.pm_a11exercicio01.persistence.JogadorDao;
import com.example.pm_a11exercicio01.persistence.TimeDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class JogadorFragment extends Fragment {

    private View view;
    private EditText etIdJo;
    private EditText etNomeJo;
    private EditText etDataNascJo;
    private EditText etPesoJo;
    private EditText etAlturaJo;
    private Spinner spTime;
    private Button btnBuscarJo;
    private Button btnInserJo;
    private Button btnDeletarJo;
    private Button btnAtualizarJo;
    private Button btnListarJo;
    private TextView tvJogadores;
    private JogadorController jCont;
    private TimeController tCont;
    private List<Time> times;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador, container, false);

        etIdJo = view.findViewById(R.id.etIdJo);
        etNomeJo = view.findViewById(R.id.etNomeJo);
        etDataNascJo = view.findViewById(R.id.etDataNascJo);
        etAlturaJo = view.findViewById(R.id.etAlturaJo);
        etPesoJo = view.findViewById(R.id.etPesoJo);
        spTime = view.findViewById(R.id.spTime);
        btnBuscarJo = view.findViewById(R.id.btnBuscarJo);
        btnInserJo = view.findViewById(R.id.btnInserirJo);
        btnDeletarJo = view.findViewById(R.id.btnDeletarJo);
        btnAtualizarJo = view.findViewById(R.id.btnAtualizarJo);
        btnListarJo = view.findViewById(R.id.btnListarJogadores);
        tvJogadores = view.findViewById(R.id.tvJogadores);

        jCont = new JogadorController(new JogadorDao(view.getContext()));
        tCont = new TimeController(new TimeDao(view.getContext()));
        preencherSpinner();

        btnInserJo.setOnClickListener(op -> insert());
        btnAtualizarJo.setOnClickListener(op -> update());
        btnDeletarJo.setOnClickListener(op -> deletar());
        btnBuscarJo.setOnClickListener(op -> buscar());
        btnListarJo.setOnClickListener(op -> listar());

        return view;

    }

    private void listar() {
        try {
            List<Jogador> jogadores = jCont.listar();
            StringBuffer sb = new StringBuffer();
            jogadores.forEach(jogador -> sb.append(jogador.toString() + "\n"));
            tvJogadores.setText(sb.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buscar() {
        try {
            Jogador j = new Jogador();
            j.setId(Integer.parseInt(etIdJo.getText().toString()));
            Jogador t = jCont.buscar(j);
            if (t.getNome() != null) {
                preencherTela(t);
            } else {
                Toast.makeText(view.getContext(), "Jogador não encontrado", Toast.LENGTH_LONG).show();
                limpar();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void deletar() {
        try {
            jCont.deletar(preencherJogador());
            Toast.makeText(view.getContext(), "Jogador excluido com sucesso", Toast.LENGTH_LONG).show();
            limpar();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void update() {
        int spPos = spTime.getSelectedItemPosition();
        if (spPos > 0) {
            try {
                jCont.modificar(preencherJogador());
                Toast.makeText(view.getContext(), "Jogador atualizado com sucesso", Toast.LENGTH_LONG).show();
                limpar();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um time", Toast.LENGTH_LONG).show();

        }
    }

    private void insert() {
        int spPos = spTime.getSelectedItemPosition();
        if (spPos > 0) {
            try {
                jCont.inserir(preencherJogador());
                Toast.makeText(view.getContext(), "Jogador inserido com sucesso", Toast.LENGTH_LONG).show();
                limpar();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um time", Toast.LENGTH_LONG).show();

        }

    }

    private void preencherTela(Jogador jogador) {
        etIdJo.setText(String.valueOf(jogador.getId()));
        etNomeJo.setText(jogador.getNome());
        etDataNascJo.setText(jogador.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        etAlturaJo.setText(String.valueOf(jogador.getAltura()));
        etPesoJo.setText(String.valueOf(jogador.getPeso()));

        int cont = 1;
        for (Time t : times) {
            if (t.getCodigo() == jogador.getTime().getCodigo()) {
                spTime.setSelection(cont);
            } else {
                cont++;
            }
        }
        if (cont > times.size()) {
            spTime.setSelection(0);
        }
    }

    private Jogador preencherJogador() {
        Jogador jogador = new Jogador();
        jogador.setNome(etNomeJo.getText().toString());
        jogador.setId(Integer.parseInt(etIdJo.getText().toString()));
        if (etDataNascJo.getText().toString().matches("^([0-2][0-9]|(3)[0-1])/((0)[0-9]|(1)[0-2])/((19|20)\\d\\d)$")) {
            jogador.setDataNasc(LocalDate.parse(etDataNascJo.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        jogador.setAltura(Float.parseFloat(etAlturaJo.getText().toString()));
        jogador.setPeso(Float.parseFloat(etPesoJo.getText().toString()));
        jogador.setTime((Time) spTime.getSelectedItem());
        return jogador;
    }

    private void limpar() {
        etIdJo.setText("");
        etNomeJo.setText("");
        etDataNascJo.setText("");
        etAlturaJo.setText("");
        etPesoJo.setText("");
        spTime.setSelection(0);
    }

    private void preencherSpinner() {
        Time t0 = new Time();
        t0.setCodigo(0);
        t0.setNome("Selecione um time");

        try {
            times = tCont.listar();
            times.add(0, t0);

            ArrayAdapter ad = new ArrayAdapter(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    times);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTime.setAdapter(ad);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}