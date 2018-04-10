package cat.tecnocampus.sqliteexample;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cat.tecnocampus.sqliteexample.Adapter.LanguageAdapter;
import cat.tecnocampus.sqliteexample.Database.DatabaseHelper;
import cat.tecnocampus.sqliteexample.Database.Model.ProgrammingLanguage;
import cat.tecnocampus.sqliteexample.Utils.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity {

    private LanguageAdapter mAdapter;
    private List<ProgrammingLanguage> languages = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView tvSQLiteEmpty;
    private Context mContext;
    private Button btn_addLanguage;

    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvSQLiteEmpty = (TextView) findViewById(R.id.tv_sqlite_empty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(mContext);
        languages.addAll(db.getProgrammingLanguages());
        mAdapter = new LanguageAdapter(mContext, languages);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        checkSQLiteData();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showEditCreateDialog(position,1);
            }

            @Override
            public void onLongClick(View view, int position) {
                showDeleteDialog(position);
            }
        }));

        btn_addLanguage = findViewById(R.id.btn_addLanguage);
        btn_addLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditCreateDialog(-1,0);
            }
        });

    }
    private void showDeleteDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to delete this language");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteLanguage(position);
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    private void showEditCreateDialog(final int position, final int action){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final EditText et_name = new EditText(mContext);
        final EditText et_difficulty = new EditText(mContext);
        final EditText et_description = new EditText(mContext);
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        builder.setMessage("Edit Language");
        builder.setCancelable(true);
        ll.addView(et_name);
        ll.addView(et_difficulty);
        ll.addView(et_description);
        builder.setView(ll);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (action == 1) {
                            updateLanguage(et_name.getText().toString(), et_difficulty.getText().toString(), et_description.getText().toString(), position);
                        } else {
                            createLanguage(et_name.getText().toString(), et_difficulty.getText().toString(), et_description.getText().toString());
                        }
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert1 = builder.create();
        alert1.show();

    }

    private void checkSQLiteData() {

        if (db.getDataCount() == 0) {
            tvSQLiteEmpty.setVisibility(View.VISIBLE);
        } else {
            tvSQLiteEmpty.setVisibility(View.INVISIBLE);
        }
    }




    private void createLanguage(String language, String difficulty, String description) {
        long id = db.insertLanguage(language,difficulty,description);
        ProgrammingLanguage programmingLanguage = db.getProgrammingLanguage(id);

        if (programmingLanguage != null) {
            languages.add(0, programmingLanguage);
            mAdapter.notifyDataSetChanged();
            checkSQLiteData();
        }
    }

    private void updateLanguage(String language, String difficulty, String description, int position) {
        ProgrammingLanguage programmingLanguage = languages.get(position);
        programmingLanguage.setName(language);
        programmingLanguage.setDifficulty(difficulty);
        programmingLanguage.setDescription(description);
        db.updateLanguage(programmingLanguage);
        languages.set(position, programmingLanguage);
        mAdapter.notifyItemChanged(position);
        checkSQLiteData();
    }


    private void deleteLanguage(int position) {
        db.deleteLanguage(languages.get(position));
        languages.remove(position);
        mAdapter.notifyItemRemoved(position);
        checkSQLiteData();
    }
}



