package org.httpinghelram.socketchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pinghelram on 25/09/15.
 */
public class CustomChatAdapter extends RecyclerView.Adapter<CustomChatAdapter.ViewHolder> {
    private ArrayList<ChatData> data = new ArrayList<>();

    public CustomChatAdapter(ArrayList<ChatData> data) {
        this.data = data;
    }

    @Override
    public CustomChatAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_chat, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomChatAdapter.ViewHolder viewHolder, int i) {
        if (data.get(i).isOwnText) {
            viewHolder.getTv_content_me().setText(data.get(i).getContent());
            viewHolder.getTv_username_me().setText(data.get(i).getUsername());
            viewHolder.getTv_content_me().setVisibility(View.VISIBLE);
            viewHolder.getTv_username_me().setVisibility(View.VISIBLE);
            viewHolder.getTv_content_other().setVisibility(View.GONE);
            viewHolder.getTv_username_other().setVisibility(View.GONE);
        } else {
            viewHolder.getTv_content_other().setText(data.get(i).getContent());
            viewHolder.getTv_username_other().setText(data.get(i).getUsername());
            viewHolder.getTv_content_me().setVisibility(View.GONE);
            viewHolder.getTv_username_me().setVisibility(View.GONE);
            viewHolder.getTv_content_other().setVisibility(View.VISIBLE);
            viewHolder.getTv_username_other().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData() {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_content_me, tv_username_me, tv_content_other, tv_username_other;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content_me = (TextView) itemView.findViewById(R.id.tv_content_me);
            tv_username_me = (TextView) itemView.findViewById(R.id.tv_username_me);
            tv_content_other = (TextView) itemView.findViewById(R.id.tv_content_other);
            tv_username_other = (TextView) itemView.findViewById(R.id.tv_username_other);
        }

        public TextView getTv_content_me() {
            return tv_content_me;
        }

        public TextView getTv_username_me() {
            return tv_username_me;
        }

        public TextView getTv_content_other() {
            return tv_content_other;
        }

        public TextView getTv_username_other() {
            return tv_username_other;
        }
    }
}
