package com.xworkz.odc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xworkz.odc.dto.GroupDTO;

import java.util.List;

public class GroupListViewAdapter extends RecyclerView.Adapter<GroupListViewAdapter.GroupListView> {

    private Context context;
    private List<GroupDTO> groupList;

    public GroupListViewAdapter(Context context,List<GroupDTO> groupList){
        this.context=context;
        this.groupList=groupList;
    }

    @NonNull
    @Override
    public GroupListViewAdapter.GroupListView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_list_view,viewGroup,false);

        return new GroupListView(view,context,groupList);
    }



    @Override
    public void onBindViewHolder(@NonNull final GroupListViewAdapter.GroupListView groupListViewHolder, int i) {
        Log.e("Position ","I Index Position is "+i);
        groupListViewHolder.id.setText(String.valueOf(groupList.get(i).getGroupId()));
        if(!groupList.get(i).getGroupName().trim().isEmpty())
            Log.e("Name","data is"+groupList.get(i).getGroupName());
            groupListViewHolder.group_name.setText(String.valueOf(groupList.get(i).getGroupName()));
        if(!groupList.get(i).getGroupDesc().trim().isEmpty() )
            Log.e("Name","data is"+groupList.get(i).getGroupDesc().trim().isEmpty());
            groupListViewHolder.desc.setText(String.valueOf(groupList.get(i).getGroupDesc()));
        final int pos=i;
        groupListViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Call","Calling..............................................");
                Toast.makeText(context,"Clicked On "+groupList.get(pos),Toast.LENGTH_LONG).show();
                new GroupCard().selectedGroup(groupList.get(pos),context);
                Log.d("Event","Click event Call....Click event Call....");
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class GroupListView extends RecyclerView.ViewHolder {
        TextView id,group_name,desc;
        List<GroupDTO> list;
        Context context;
        View itemView;


        public GroupListView(@NonNull View itemView, final Context context, final List<GroupDTO> list) {
            super(itemView);
            this.context=context;
            this.list=list;
            this.itemView=itemView;
            id=itemView.findViewById(R.id.id);
            group_name=itemView.findViewById(R.id.group_name);
            desc=itemView.findViewById(R.id.desc);

        }



    }



}
