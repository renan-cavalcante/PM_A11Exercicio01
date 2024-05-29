package com.example.pm_a11exercicio01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm_a11exercicio01.controller.TimeController;
import com.example.pm_a11exercicio01.model.Time;
import com.example.pm_a11exercicio01.persistence.TimeDao;

import java.sql.SQLException;
import java.util.List;


public class TimeFragment extends Fragment {

    private View view;
    private EditText etCodigoTi, etNomeTi, etCidadeTi;
    private Button btnBuscarTi, btnInserTi, btnDeletarTi, btnAtualizarTi, btnListarTi;
    private TextView tvTimes;
    private TimeController tCont;

    public TimeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);

        etCodigoTi = view.findViewById(R.id.etCodigoTi);
        etNomeTi = view.findViewById(R.id.etNomeTi);
        etCidadeTi = view.findViewById(R.id.etCidadeTi);
        btnBuscarTi = view.findViewById(R.id.btnBuscarTi);
        btnInserTi = view.findViewById(R.id.btnInserirTi);
        btnDeletarTi = view.findViewById(R.id.btnDeletarTi);
        btnAtualizarTi = view.findViewById(R.id.btnAtualizarTi);
        btnListarTi = view.findViewById(R.id.btnListarTi);
        tvTimes = view.findViewById(R.id.tvTimes);
        tvTimes.setMovementMethod(new ScrollingMovementMethod());

        tCont = new TimeController(new TimeDao(view.getContext()));

        btnInserTi.setOnClickListener(op -> insert());
        btnAtualizarTi.setOnClickListener(op -> update());
        btnDeletarTi.setOnClickListener(op -> deletar());
        btnBuscarTi.setOnClickListener(op -> buscar());
        btnListarTi.setOnClickListener(op -> listar());

        return view;
    }

    private void listar() {
        try {
            List<Time> times = tCont.listar();
            StringBuffer sb = new StringBuffer();
            times.forEach(time -> sb.append(time.toString() + "\n"));
            tvTimes.setText(sb.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buscar() {
        try {
            Time t = tCont.buscar(preencherTime());
            if (t.getNome() != null) {
                preencherTela(t);
            } else {
                Toast.makeText(view.getContext(), "Time não encontrado", Toast.LENGTH_LONG).show();
                limpar();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void deletar() {
        try {
            tCont.deletar(preencherTime());
            Toast.makeText(view.getContext(), "Time excluido com sucesso", Toast.LENGTH_LONG).show();
            limpar();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void update() {
        try {
            tCont.modificar(preencherTime());
            Toast.makeText(view.getContext(), "Time atualizado com sucesso", Toast.LENGTH_LONG).show();
            limpar();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void insert() {
        try {
            tCont.inserir(preencherTime());
            Toast.makeText(view.getContext(), "Time inserido com sucesso", Toast.LENGTH_LONG).show();
            limpar();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencherTela(Time time) {
        etCodigoTi.setText(String.valueOf(time.getCodigo()));
        etCidadeTi.setText(time.getCidade());
        etNomeTi.setText(time.getNome());
    }

    private Time preencherTime() {
        Time time = new Time();
        time.setNome(etNomeTi.getText().toString());
        time.setCidade(etCidadeTi.getText().toString());
        time.setCodigo(Integer.parseInt(etCodigoTi.getText().toString()));
        return time;
    }

    private void limpar() {
        etCodigoTi.setText("");
        etCidadeTi.setText("");
        etNomeTi.setText("");
    }
}