package com.xworkz.odc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xworkz.odc.dto.GroupDTO;
import com.xworkz.odc.sqliteHelper.SQLiteDBHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupCard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupCard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText groupDescription;
    private EditText groupName;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SQLiteDBHelper sqLiteDBHelper;
    private OnFragmentInteractionListener mListener;

    public GroupCard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupCard.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupCard newInstance(String param1, String param2) {
        GroupCard fragment = new GroupCard();
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
       final View view=inflater.inflate(R.layout.fragment_group_card,container,false);
       sqLiteDBHelper=new SQLiteDBHelper(view.getContext());
        RecyclerView gropRecyclerView=view.findViewById(R.id.group_list_recycle);
        List<GroupDTO> list=sqLiteDBHelper.getAllGroupData();
        if(list.size()>0) {
            gropRecyclerView=view.findViewById(R.id.group_list_recycle);
            setRecycleView(view.getContext(),list,gropRecyclerView);
        }
        Button addGroup=view.findViewById(R.id.addGroup);
        final RecyclerView finalGropRecyclerView = gropRecyclerView;
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName=view.findViewById(R.id.groupName);
                groupDescription=view.findViewById(R.id.desc);
                String grpName=String.valueOf(groupName.getText());
                String grpDesc=String.valueOf(groupDescription.getText());
                Log.d("Nothing",grpName+"    "+grpDesc);
                if(grpName.length()>0 && grpDesc.length()>0) {
                    boolean resCheck = new SQLiteDBHelper(view.getContext()).checkGroupDetailsByNameNdDesc(grpName, grpDesc);
                    if (resCheck) {
                        boolean res = new SQLiteDBHelper(view.getContext()).insertGroupData(grpName, grpDesc);
                        if (res) {
//                        RecyclerView recyclerView=view.findViewById(R.id.group_list_recycle);
                            final List<GroupDTO> list = sqLiteDBHelper.getAllGroup();
                            setRecycleView(getContext(), list, finalGropRecyclerView);
                            Toast.makeText(getContext(), "Saved successfully.....", Toast.LENGTH_LONG).show();
                            groupName.setText("");
                            groupDescription.setText("");
                        } else {
                            Toast.makeText(getContext(), "Not Saved successfully.....", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Data Is already available.......", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Please Enter Group Name And Group Description First. ", Toast.LENGTH_LONG).show();
                }
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new GroupEdit()).addToBackStack("home_stack").commit();
            }
        });



        return view;

    }



    public void setRecycleView(Context context, List<GroupDTO> list, RecyclerView gropRecyclerView){
        GroupListViewAdapter groupListViewAdapter = new GroupListViewAdapter(getContext(), list);
        Log.i("Recycler Data", "Recycler Is Called.....");
        gropRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gropRecyclerView.setAdapter(groupListViewAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    public void selectedGroup(GroupDTO groupDTO,Context context) {
        Bundle bundle=new Bundle();
        bundle.putString("g_id",String.valueOf(groupDTO.getGroupId()));
        bundle.putString("g_name",String.valueOf(groupDTO.getGroupName()));
        bundle.putString("g_desc",String.valueOf(groupDTO.getGroupDesc()));

        GroupEdit groupEdit=new GroupEdit();
        groupEdit.setArguments(bundle);
        FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,groupEdit).addToBackStack("home_stack").commit();
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
