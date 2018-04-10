package cat.tecnocampus.sqliteexample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cat.tecnocampus.sqliteexample.Database.Model.ProgrammingLanguage;
import cat.tecnocampus.sqliteexample.R;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>{


    private List<ProgrammingLanguage> mLanguages;
    private Context mContext;

    public LanguageAdapter(Context mContext, List<ProgrammingLanguage> languages){
        this.mContext = mContext;
        this.mLanguages = languages;
    }


    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.language_row, parent, false);
        return new LanguageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {

        ProgrammingLanguage language = this.mLanguages.get(position);
        holder.name.setText(language.getName());
        holder.difficulty.setText(language.getDifficulty());
        holder.description.setText(language.getDescription());


    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder {

        TextView name,difficulty,description;
        public LanguageViewHolder(View itemView){
            super (itemView);
            name = itemView.findViewById(R.id.tv_name);
            difficulty = itemView.findViewById(R.id.tv_difficulty);
            description = itemView.findViewById(R.id.tv_description);
        }

    }
}
