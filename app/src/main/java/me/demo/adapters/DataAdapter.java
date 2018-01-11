package me.demo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.demo.R;
import me.demo.classes.User;
import me.demo.interfaces.OnItemClickListener;

/**
 * Created by Rui Oliveira on 10/01/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> usersList;
    private final OnItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView userName, email;
        public ImageView gender;

        public ViewHolder(View view) {
            super(view);
            picture = (ImageView) view.findViewById(R.id.picture);
            userName = (TextView) view.findViewById(R.id.user_name);
            email = (TextView) view.findViewById(R.id.email);
            gender = (ImageView) view.findViewById(R.id.gender);
        }

        public void bind(final User item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public DataAdapter(Context context, ArrayList<User> usersList, OnItemClickListener listener) {
        this.context = context;
        this.usersList = usersList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(usersList.get(position), listener);

        User user = usersList.get(position);

        Picasso.with(context).load(user.getPicture().getThumbnail()).into(holder.picture);

        holder.userName.setText(String.format("%s %s %s", user.getName().getTitle(), user.getName().getFirst(), user.getName().getLast()));
        holder.email.setText(user.getEmail());

        if (user.getGender().equalsIgnoreCase("male")) {
            holder.gender.setImageResource(R.drawable.ic_male);
        } else {
            holder.gender.setImageResource(R.drawable.ic_female);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
