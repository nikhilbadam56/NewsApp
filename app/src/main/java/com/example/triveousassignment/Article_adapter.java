package com.example.triveousassignment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Article_adapter extends RecyclerView.Adapter<Article_adapter.ArticleViewHolder>{

    List<Article> news;
    ArticleClickInterface context;
    Context  contexts;
    public Article_adapter(Context contexts,List<Article> news,ArticleClickInterface context)
    {
        this.news = news;
        this.context = context;
        this.contexts = contexts;
    }
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragmentcardlayout,parent,false);
        ArticleViewHolder articleViewHolder  = new ArticleViewHolder(view);
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface ArticleClickInterface{
        public void onArticleItemClick(Article article);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private View view;
        private TextView source;
        private TextView description;
        private ImageView imageView;
        private Article article;
        private TextView title;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            view  = itemView;
            view.setOnClickListener(this);
            source = view.findViewById(R.id.source);
            description = view.findViewById(R.id.description);
            imageView = view.findViewById(R.id.imageview);
            title = view.findViewById(R.id.title);
        }
        public void bind(int position)
        {
            article = news.get(position);
            source.setText(article.getSource());
            description.setText(article.getDescription());
            Glide.with(contexts).load(article.getUrltoimage()).into(imageView);
            title.setText(article.getTitle());
        }

        @Override
        public void onClick(View v) {
            context.onArticleItemClick(article);
        }
    }




}
