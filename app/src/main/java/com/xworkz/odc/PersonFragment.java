package com.xworkz.odc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xworkz.odc.dto.UserDTO;
import com.xworkz.odc.sqliteHelper.SQLiteDBHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    EditText userName,emailId;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
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
        final View view=inflater.inflate(R.layout.fragment_person,container,false);
        List<UserDTO> userDTOList=new SQLiteDBHelper(getContext()).getAllUser();
        if(userDTOList!=null) {
            setUsersRecycler(view,getContext(), userDTOList);
        }
        Button button=view.findViewById(R.id.addPerson);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=view.findViewById(R.id.user_name);
                emailId=view.findViewById(R.id.user_email_id);
                String user=String.valueOf(userName.getText());
                String email=String.valueOf(emailId.getText());
                boolean checkUser=new SQLiteDBHelper(getContext()).checkUserByName(user);
                if(checkUser){
                    Toast.makeText(getContext(),"User Data Already Available...",Toast.LENGTH_LONG).show();
                }else{
                    if(user.length()>0 && email.length()>0) {
                        boolean save = new SQLiteDBHelper(getContext()).savePerson(user, email);
                        if (save) {
                            Toast.makeText(getContext(), "User Data Saved Successfully.......", Toast.LENGTH_LONG).show();
                            userName.setText("");
                            emailId.setText("");
                            List<UserDTO> userDTOList = new SQLiteDBHelper(getContext()).getAllUser();
                            if (userDTOList != null) {
                                setUsersRecycler(view, getContext(), userDTOList);
                            }
                        } else {
                            Toast.makeText(getContext(), "User Data Not Saved Successfully.......", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Please Enter Username and Email id", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        return view;
    }

    private void setUsersRecycler(View view, Context context, List<UserDTO> userDTOList) {
        recyclerView=view.findViewById(R.id.user_list_recycle_view);
        GroupMemberListViewAdapter groupMemberListViewAdapter=new GroupMemberListViewAdapter(context,userDTOList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(groupMemberListViewAdapter);
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
