package com.xworkz.odc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.xworkz.odc.sqliteHelper.SQLiteDBHelper;

public class UserHomeScreen extends AppCompatActivity  implements UserHome.OnFragmentInteractionListener,
        AddEvent.OnFragmentInteractionListener,
        AddGroup.OnFragmentInteractionListener,
        GroupCard.OnFragmentInteractionListener,
        GroupEdit.OnFragmentInteractionListener,
        PersonFragment.OnFragmentInteractionListener
{





    private FragmentManager fragmentManager=getSupportFragmentManager();
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView uNavigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

    //Database Helper Class
    private SQLiteDBHelper SQLiteDBHelper;

    private GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);

        //Database Class
        SQLiteDBHelper =new SQLiteDBHelper(this);



        //define Toolbar
        toolbar=findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawerLayout);
        uNavigationView=findViewById(R.id.navigationView);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
//        gridLayout=findViewById(R.id.mainGrid);
//        setSingleEvent(gridLayout);

//        loadFragment(getApplicationContext(),new UserHome());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new UserHome()).disallowAddToBackStack().commit();

        uNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("Item Clicked","Item name is "+item.getItemId());
                switch (item.getItemId()){
                    case R.id.menu1:
                        item.setChecked(true);
                        displayMessage("Menu 1");
//                        loadFragment(getApplicationContext(),new CreateProjectFragment());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.menu2:
                        item.setChecked(true);
                        displayMessage("Menu 2");
//                        loadFragment(getApplicationContext(),new AllProjectFragment());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.menu3:
                        item.setChecked(true);
                        displayMessage("Menu 3");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.menu4:
                        item.setChecked(true);
                        displayMessage("Menu 4");
//                        loadFragment(getApplicationContext(),new CreateResourceFragment());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.menu5:
                        item.setChecked(true);
                        displayMessage("Menu 5");
//                        loadFragment(getApplicationContext(),new AllResourceFragment());
                        drawerLayout.closeDrawers();
                        return true;

                    default:
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container,this.class,null).commit();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            Log.d("Option","Name is "+item.getItemId());
            return true;
        }
        switch (item.getItemId()){
            case R.id.logout_item:
                displayMessage("Logout Successfully...............");
                startActivity(new Intent(UserHomeScreen.this,LogIn.class));
                return true;
            case R.id.user_profile:
                displayMessage("User Profile Selected.........");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSingleEvent(GridLayout mainGrid){
        for(int i=0;i<mainGrid.getChildCount();i++) {
            final int x=i;
            final CardView cardView =(CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cardView.getCardBackgroundColor().getDefaultColor()==-1){
                        cardView.setCardBackgroundColor(Color.parseColor("FF110A07"));
                        Toast.makeText(UserHomeScreen.this,"Calling i is"+x ,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void loadFragment(Context context, Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .addToBackStack("home_stack")
                .commit();
    }

    public void displayMessage(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.appbar_main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
