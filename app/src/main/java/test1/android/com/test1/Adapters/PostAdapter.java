package test1.android.com.test1.Adapters;


import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import test1.android.com.test1.AddPostActivity;
import test1.android.com.test1.Models.Post;
import test1.android.com.test1.R;
import test1.android.com.test1.ShowPostActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";


    List<Post> postList;
    Context context;
    private int position;



    public PostAdapter(List<Post> PostList, Context context ) {
        this.postList = PostList;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)  {
//        Log.d(TAG, "onBindViewHolder: called.");
        holder.mTextViewId.setText(postList.get(position).getPostTitle() + " - "+ postList.get(position).getId());
        holder.mTextViewName.setText(postList.get(position).getPostContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onBindViewHolder: "+postList.get(position).getPostTitle());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_post, popup.getMenu());
                popup.show();

//                getMenuInflater().inflate(R.menu.menu_post, menu);
//            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit "+postList.get(position).getPostTitle());
//            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
                return true;
            }
        });

        holder.btnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.getContext().startActivity(new Intent(view.getContext(), AddPostActivity.class));
                Intent intent = new Intent(view.getContext(), AddPostActivity.class);
//                intent.putExtra("id", String.valueOf(users.get(pos).getId()));
                intent.putExtra("id", postList.get(position).getId());
                intent.putExtra("post_title", postList.get(position).getPostTitle());
                intent.putExtra("post_content", postList.get(position).getPostContent());
                view.getContext().startActivity(intent);
            }
        });

        holder.btnDelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //context.startActivity(new Intent(context, AddPostActivity.class));
                Log.d(TAG, "onBindViewHolder -Del: "+postList.get(position).getPostTitle());
//                ApiClient.getClient().create(ApiService.class);
                ShowPostActivity.showPost.goDelete(postList.get(position).getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

//    public int getPosition() {
//        return position;
//    }
//
//    public void setPosition(int position) {
//        this.position = position;
//    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {
//        implements View.OnCreateContextMenuListener
        public TextView mTextViewId, mTextViewName;
        public  ImageButton btnEditPost, btnDelPost;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewId = (TextView) itemView.findViewById(R.id.tvId);
            mTextViewName = (TextView) itemView.findViewById(R.id.tvName);
            btnEditPost = (ImageButton) itemView.findViewById(R.id.btnEditPost);
            btnDelPost = (ImageButton) itemView.findViewById(R.id.btnDelPost);
//            itemView.setOnCreateContextMenuListener(this);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
////            super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
////            getMenuInflater().inflate(R.menu.menu_post, contextMenu);
//            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit "+postList.get(position).getPostTitle());
//            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
////            MenuItem Edit = menu.add(0, v.getId(), 1, "Edit "+postList.get(position).getPostTitle());
////            MenuItem Delete = menu.add(0,v.getId(),2, "Delete");
//
//            Edit.setOnMenuItemClickListener(onEditMenu);
//            Delete.setOnMenuItemClickListener(onEditMenu);
//        }
//
//        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case 1:
//                        Log.d("Info Long click", "Edit - "+ getAdapterPosition());
//                        Log.d("Info lain", "Edit - "+ PostAdapter.this.getPosition());
//                        return true;
//                    case 2:
//                        Log.d("Info Long click", "Delete - "+ postList.get(position).getId());
//                        Log.d("Info lain", "Delete - "+ PostAdapter.this.getPosition());
////                        break;
//                        return true;
////                    default:
////                        return super.onContextItemSelected(item);
//                }
//                return true;
//            }
//        };

//        @Override
//        public void onClick(View view) {
//            Log.d("Info saja", "Masuk - "+getAdapterPosition());
//            Log.d("Info saja plus ", "Masuk - "+postList.get(position).getPostTitle());
//        }

    }
}