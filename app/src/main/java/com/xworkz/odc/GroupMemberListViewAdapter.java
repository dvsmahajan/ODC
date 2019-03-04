package com.xworkz.odc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xworkz.odc.dto.GroupUserDTO;
import com.xworkz.odc.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

public class GroupMemberListViewAdapter extends RecyclerView.Adapter<GroupMemberListViewAdapter.MemberListView> {

    Context context;
    List<UserDTO> list;

    public GroupMemberListViewAdapter(Context context, List<UserDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GroupMemberListViewAdapter.MemberListView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_member_list,viewGroup,false);

        return new MemberListView(view,context,list);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMemberListViewAdapter.MemberListView memberListView, int i) {
        memberListView.id.setText(String.valueOf(list.get(i).getUserId()));
        memberListView.email.setText(String.valueOf(list.get(i).getUserEmail()));
        memberListView.memberName.setText(String.valueOf(list.get(i).getUserName()));
        final int pos=i;
        memberListView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked on "+list.get(pos),Toast.LENGTH_LONG).show();
//                new GroupEdit().selectMember();
            }
        });
        memberListView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context,"Clicked on "+list.get(pos),Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MemberListView extends RecyclerView.ViewHolder {
        TextView id,memberName,email;
        Context context;
        View itemView;
        List<UserDTO> list;


        public MemberListView(@NonNull View itemView,Context context,List<UserDTO> list) {
            super(itemView);
            this.context=context;
            this.list=list;
            this.itemView=itemView;
            id=itemView.findViewById(R.id.id);
            email=itemView.findViewById(R.id.email);
            memberName=itemView.findViewById(R.id.memberName);

        }
    }
}
