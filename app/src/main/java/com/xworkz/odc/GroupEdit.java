package com.xworkz.odc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xworkz.odc.dto.GroupUserDTO;
import com.xworkz.odc.dto.UserDTO;
import com.xworkz.odc.sqliteHelper.SQLiteDBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupEdit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupEdit extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView groupName;
    private EditText groupDesc;
    private Button updateGroupDesc;
    private Button addGroupMember;
    private Dialog dialog;
    RecyclerView groupMember;
    GroupEdit groupEdit=this;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GroupEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupEdit.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupEdit newInstance(String param1, String param2) {
        GroupEdit fragment = new GroupEdit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_group_edit,container,false);
        Bundle bundle=getArguments();
        final String gId=bundle.getString("g_id");
        String gName=bundle.getString("g_name");
        String gDesc=bundle.getString("g_desc");
        final Context context=getContext();
        Log.d("Data is ",""+gId+ "   "+gName+"   "+gDesc);
        groupName=view.findViewById(R.id.group_name);
        groupName.setText(gName);
        groupDesc=view.findViewById(R.id.desc);
        groupDesc.setText(gDesc);

        groupMember=view.findViewById(R.id.member_list_view);
        List<UserDTO> groupUserDTOS=new SQLiteDBHelper(getContext()).getAllMemberByGroupId(gId);
        if(groupUserDTOS!=null){
            setGroupMemberListRecycleView(getContext(),groupUserDTOS);
        }
        dialog=new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addGroupMember=view.findViewById(R.id.add_member_in_list);
        addGroupMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.add_member_popup);
                final SQLiteDBHelper sqLiteDBHelper=new SQLiteDBHelper(v.getContext());
                String[] getUserList=sqLiteDBHelper.getUserNames();
                List<String> list=new ArrayList<String>();
                list.addAll(Arrays.asList(getUserList));
//                String[] getUserList={"Yellow","Red","Green","Black"};
                if(getUserList.length>0) {
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) dialog.findViewById(R.id.member_auto_name);
                    ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_list_item_1, list);
                    Log.d("arrayadapter", "" + namesAdapter.getCount() + "         context " + namesAdapter.getContext());
                    autoCompleteTextView.setAdapter(namesAdapter);
                    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selected = (String) parent.getItemAtPosition(position);
                            Log.d("Selected",selected+" is Selected.............");
                            String emailByName=sqLiteDBHelper.getEmailIdByName(selected);
                            Log.d("email","Email is "+emailByName);
                            EditText editText=dialog.findViewById(R.id.member_email);
                            editText.setText(emailByName);
                        }
                    });
                }

                Button addMember=dialog.findViewById(R.id.addMemberToList);
                addMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText memberName=dialog.findViewById(R.id.member_auto_name);
                        EditText memberEmail=dialog.findViewById(R.id.member_email);
                        Toast.makeText(dialog.getContext(),String.valueOf(memberName.getText())+"  <<------------------>>  "+String.valueOf(memberEmail.getText()),Toast.LENGTH_LONG).show();
//                        dialog.dismiss();

                        boolean addMemberIntoGroupUser=new SQLiteDBHelper(getContext()).addGroupMember(String.valueOf(memberName.getText()),String.valueOf(memberEmail.getText()),String.valueOf(groupName.getText()));
                        if(addMemberIntoGroupUser){
                            List<UserDTO> groupUserDTOS=new SQLiteDBHelper(getContext()).getAllMemberByGroupId(gId);
                            setGroupMemberListRecycleView(getContext(),groupUserDTOS);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(),"Wrong Input Try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private void setGroupMemberListRecycleView(Context context, List<UserDTO> groupUserDTOS) {
        GroupMemberListViewAdapter groupMemberListViewAdapter=new GroupMemberListViewAdapter(context,groupUserDTOS);
        groupMember.setLayoutManager(new LinearLayoutManager(getContext()));
        groupMember.setAdapter(groupMemberListViewAdapter);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /*  public void addMemberInList(){
        dialog.setContentView(R.layout.add_member_popup);
        EditText memberName=dialog.findViewById(R.id.member_name);
        EditText memberEmail=dialog.findViewById(R.id.member_email);
        Button addMember=dialog.findViewById(R.id.add_member_in_list);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
