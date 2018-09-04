package test1.android.com.test1.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test1.android.com.test1.Models.MasterStock;
import test1.android.com.test1.R;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.MyViewHolder> {

    List<MasterStock> masterStockList;

    public MasterAdapter(List <MasterStock> MasterList) {
        masterStockList = MasterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        holder.mTextViewId.setText("TickerID = " + masterStockList.get(position).getId());
        holder.mTextViewName.setText("Ticker Name = " + masterStockList.get(position).getTickerName());

    }

    @Override
    public int getItemCount () {
        return masterStockList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewId, mTextViewName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewId = (TextView) itemView.findViewById(R.id.tvId);
            mTextViewName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }
}
