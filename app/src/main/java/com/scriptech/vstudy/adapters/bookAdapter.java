//package com.scriptech.vstudy.adapters;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//import com.scriptech.vstudy.ui.fragments.pdf.Pdf;
//import com.scriptech.vstudy.R;
//import com.scriptech.vstudy.model.booksModel;
//import com.squareup.picasso.Picasso;
//
//
//public class bookAdapter extends RecyclerView.Adapter<bookAdapter.ViewHolder> {
//    private ArrayList<booksModel> modelArrayList;
//    private Context context;
//
//    public bookAdapter(ArrayList<booksModel> modelArrayList, Context context) {
//        this.modelArrayList = modelArrayList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public bookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_trending_book, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull bookAdapter.ViewHolder holder, int position) {
//        booksModel model = modelArrayList.get(position);
//
//        Picasso.get().load(model.getBookImage()).into(holder.BookImage);
//        holder.BookName.setText(model.getBookAuthor());
//
//        holder.Book.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new Pdf();
//                Bundle bundle = new Bundle();
//                bundle.putString("Link", model.getBookLink());
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameContainer1, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return modelArrayList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private final ImageView BookImage;
//        private final TextView BookName;
//        private final CardView Book;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            Book = itemView.findViewById(R.id.card_trending_book);
//
//            BookImage = itemView.findViewById(R.id.book_image);
//            BookName = itemView.findViewById(R.id.book_name);
//        }
//    }
//}