package com.xworkz.odc.sqliteHelper;

import  android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.xworkz.odc.dto.GroupDTO;
import com.xworkz.odc.dto.GroupUserDTO;
import com.xworkz.odc.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME="ODC.db";
    public final static String EVENT_TABLE_NAME="EVENT_TABLE";
    public final static String GROUP_TABLE_NAME="GROUP_TABLE";
    public final static String USER_TABLE_NAME="USER_TABLE";
    public final static String GROUP_USER_TABLE_NAME="GROUP_USER_TABLE";

    public final static String EVENT_COL_1="EVENT_ID";
    public final static String EVENT_COL_2="EVENT_NAME";
    public final static String EVENT_COL_3="EVENT_TIME";
    public final static String EVENT_COL_4="EVENT_DATE";
    public final static String EVENT_COL_5="EVENT_DESCRIPTION";
    public final static String EVENT_COL_6="EVENT_GROUP_ID";


    public final static String GROUP_COL_1="GROUP_ID";
    public final static String GROUP_COL_2="GROUP_NAME";
    public final static String GROUP_COL_3="GROUP_DESCRIPTION";


    public final static String USER_COL_1="USER_ID";
    public final static String USER_COL_2="USER_NAME";
    public final static String USER_COL_3="USER_PASSWORD";
    public final static String USER_COL_4="USER_EMAIL";


    public final static String USER_GROUP_COL_1="USER_GROUP_ID";
    public final static String USER_GROUP_COL_2="GROUP_ID";
    public final static String USER_GROUP_COL_3="USER_ID";


    final Context context;

    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1    );
        this.context=context;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+EVENT_TABLE_NAME+" ("+EVENT_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+EVENT_COL_2+" TEXT ,"+EVENT_COL_3+" TEXT,"+EVENT_COL_4+" TEXT,"+EVENT_COL_5+" TEXT,"+EVENT_COL_6+" TEXT )");
        db.execSQL("create table "+GROUP_TABLE_NAME+" ("+GROUP_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+GROUP_COL_2+" TEXT ,"+GROUP_COL_3+" TEXT)");
        db.execSQL("create table "+USER_TABLE_NAME+" ("+USER_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+USER_COL_2+" TEXT ,"+USER_COL_3+" TEXT,"+USER_COL_4+" TEXT)");
        db.execSQL("create table "+GROUP_USER_TABLE_NAME+" ("+USER_GROUP_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+USER_GROUP_COL_2+" INTEGER ,"+USER_GROUP_COL_3+" INTEGER)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+GROUP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+GROUP_USER_TABLE_NAME);
        onCreate(db);
    }


    public boolean insertGroupData(String groupName,String groupDesc){

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(GROUP_COL_2,groupName);
        contentValues.put(GROUP_COL_3,groupDesc);
        long res=sqLiteDatabase.insert(GROUP_TABLE_NAME,null,contentValues);
        if(res == -1){
            Log.d("Not Saved","Saved Not Successfully................");
            return false;
        }else{
            Log.d("Saved","Does Saved Successfully................");
            return true;
        }
    }

    public List<GroupDTO> getAllGroup(){
        String selectAllGroup="SELECT * FROM "+GROUP_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectAllGroup,null);
        List<GroupDTO> groupDTOS=new ArrayList<GroupDTO>();
        Log.d("Data","My data is comming"+cursor.getCount());
        if(cursor.moveToFirst()){
            do{
                Log.d("Data","My data is comming here");
                GroupDTO groupDTO=new GroupDTO(cursor.getInt(0),String.valueOf(cursor.getString(1)),String.valueOf(cursor.getString(2)));
                Log.e("Data",""+String.valueOf(cursor.getString(1))+"  "+String.valueOf(cursor.getString(2)));
                groupDTOS.add(groupDTO);
            }while (cursor.moveToNext());
        }
        return groupDTOS;
    }

    public GroupDTO getGroupDetailsById(int id){
        String selectGroupByID="SELECT * FROM "+GROUP_TABLE_NAME+ " WHERE "+GROUP_COL_1+" = "+id;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectGroupByID,null);
        GroupDTO groupDTO;
        if(cursor!=null){
            return new GroupDTO(cursor.getInt(0),String.valueOf(cursor.getString(1)),String.valueOf(cursor.getString(2)));
        }
        return null;
    }

    public List<GroupUserDTO> getGroupUserByGroupId(int id){
        String selectGroupByID="SELECT * FROM "+GROUP_USER_TABLE_NAME+ " WHERE "+USER_GROUP_COL_2+" = "+id;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectGroupByID,null);
        List<GroupUserDTO> groupUserList=new ArrayList<GroupUserDTO>();
        if(cursor.moveToFirst()){
            do{
                GroupUserDTO groupUserDTO=new GroupUserDTO(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2));
                Log.e("Data",""+String.valueOf(cursor.getString(1))+"  "+String.valueOf(cursor.getString(2)));
                groupUserList.add(groupUserDTO);
            }while (cursor.moveToNext());
        }
        return groupUserList;
    }

    public UserDTO getUserById(int id){
        String selectUser="SELECT * FROM "+USER_TABLE_NAME+" WHERE "+USER_COL_1+" = "+id;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectUser,null);
        if(cursor!=null){
            return new UserDTO(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        }
        return null;
    }

    public String[] getUserNames(){
        String selectUserNames="SELECT "+USER_COL_2+" FROM "+USER_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectUserNames,null);
        List<String> namelist=new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                namelist.add(cursor.getString(0));
                Log.d("Data","Name "+cursor.getString(0));
            }while (cursor.moveToNext());
        }
        if(namelist!=null){
            String[] names=new String[namelist.size()];
            for(int i=0;i<namelist.size();i++){
                names[i]=namelist.get(i);

            }
            return names;
        }
        return null;
    }

    public boolean insert(){
        return false;
    }

    public List<UserDTO> getAllUser() {
        String selectAllGroup="SELECT * FROM "+USER_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectAllGroup,null);
        List<UserDTO> userDTOS=new ArrayList<UserDTO>();
        if(cursor.moveToFirst()){
            do{
                UserDTO userDTO=new UserDTO(cursor.getInt(0),String.valueOf(cursor.getString(1)),String.valueOf(cursor.getString(2)),String.valueOf(cursor.getString(3)));
                Log.e("Data",""+String.valueOf(cursor.getString(1))+"  "+String.valueOf(cursor.getString(2)));
                userDTOS.add(userDTO);
            }while (cursor.moveToNext());
        }

        return userDTOS;
    }


    public List<GroupDTO> getAllGroupData() {
        String selectAllGroup="SELECT * FROM "+GROUP_TABLE_NAME ;
        SQLiteDatabase database=getReadableDatabase();
        Cursor getAllGroup=database.rawQuery(selectAllGroup,null);
        List<GroupDTO> groupDTOList=new ArrayList<GroupDTO>();
        Log.d("Cursor","cursor Value "+getAllGroup.getCount());
        Log.d("First","Move to first "+getAllGroup.moveToFirst());
        if(getAllGroup.moveToFirst()){
            do{
                groupDTOList.add(new GroupDTO(getAllGroup.getInt(0),String.valueOf(getAllGroup.getString(1)),String.valueOf(getAllGroup.getString(2))));
            }while (getAllGroup.moveToNext());
        }
        return groupDTOList;
    }


    public boolean registerUser(String username,String password,String email){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_COL_2,username);
        contentValues.put(USER_COL_3,password);
        contentValues.put(USER_COL_4,email);
        long res=sqLiteDatabase.insert(USER_TABLE_NAME,null,contentValues);
        if(res == -1){
            Log.d("Not Saved","Does Not Saved Successfully................");
            return false;
        }else{
            Log.d("Saved","saved Successfully................");
            return true;
        }

    }

    public UserDTO checkLogin(String username, String password) {
        String selectUser="SELECT * FROM "+USER_TABLE_NAME+ " WHERE "+USER_COL_4+" = '"+username+"' AND "+USER_COL_3+ " = '"+password +"'";
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectUser,null);
        if(cursor.moveToFirst()){
            Log.d("","data Is "+cursor.getInt(0)+ "    " +cursor.getString(1)+"     "+cursor.getString(2)+"   "+cursor.getString(3));
            return new UserDTO(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        }
        return null;
    }

    public boolean checkGroupDetailsByNameNdDesc(String grpName, String grpDesc) {
        String selectGroup="SELECT * FROM "+GROUP_TABLE_NAME+ " WHERE "+GROUP_COL_2+ "='"+grpName+"'";
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectGroup,null);
        if(cursor.moveToFirst()){
            return false;
        }else {
            return true;
        }
    }

    public String getEmailIdByName(String username) {
        String selectGroup="SELECT "+ USER_COL_4 +" FROM "+USER_TABLE_NAME+ " WHERE "+USER_COL_2+" = '"+username+"'";
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectGroup,null);
        String res=null;
        if(cursor!=null){
            if(cursor.moveToFirst()){
                res=cursor.getString(0);
            }
        }
        return res;
    }

    public boolean addGroupMember(String userName, String email, String groupName) {
        String selectQuery="SELECT * FROM "+USER_TABLE_NAME+" WHERE "+USER_COL_2+" = '"+userName+"' AND "+USER_COL_4+" = '"+email+"'";
        String getGroupIdByGroupName="SELECT "+GROUP_COL_1+" FROM "+GROUP_TABLE_NAME+" WHERE "+GROUP_COL_2+" = '"+groupName+"'";
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor cursor1=sqLiteDatabase.rawQuery(getGroupIdByGroupName,null);
        int groupId=0;
        if(cursor1.moveToFirst()){
            groupId=cursor1.getInt(0);
        }
        if(groupId>0) {
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(USER_GROUP_COL_2,groupId);
                contentValues.put(USER_GROUP_COL_3,cursor.getInt(0));
                long savedGroupid=sqLiteDatabase.insert(GROUP_USER_TABLE_NAME,null,contentValues);
                Log.d("Group User Saved","Saved Group User id "+savedGroupid);
                return  true;
            }
            else {
                ContentValues userContantValue=new ContentValues();
                userContantValue.put(USER_COL_2,userName);
                userContantValue.put(USER_COL_4,email);
                long saveUser=sqLiteDatabase.insert(USER_TABLE_NAME,null,userContantValue);
                Log.d("User Saved","Saved User id "+saveUser);
                if(saveUser!=-1) {
                    long savedUserId=this.getUserByName(userName);
                    if(savedUserId>0){
                        ContentValues userGroupContantValue = new ContentValues();
                        userGroupContantValue.put(USER_GROUP_COL_3, savedUserId);
                        userGroupContantValue.put(USER_GROUP_COL_2, groupId);
                        long savedGroupUserid = sqLiteDatabase.insert(GROUP_USER_TABLE_NAME, null, userGroupContantValue);
                        if(savedGroupUserid>0) {
                            Log.d("Group User Saved", "Saved Group User id " + savedGroupUserid);
                            return true;
                        }else{
                            Log.d("Group User Saved", "Saved Group User id " + savedGroupUserid);
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    private long getUserByName(String userName) {
        String getUserIdByName="SELECT "+USER_COL_1+" FROM "+USER_TABLE_NAME+" WHERE "+USER_COL_2+" = '"+userName+"'";
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(getUserIdByName,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }
        }
        return 0;
    }

    public List<UserDTO> getAllMemberByGroupId(String gId) {
        List<Integer> memberId = new ArrayList<Integer>();
        String getMemberListByGroupId = "SELECT " + USER_GROUP_COL_3 + " FROM " + GROUP_USER_TABLE_NAME + " WHERE " + USER_GROUP_COL_2 + " = '" + gId + "'";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(getMemberListByGroupId, null);
        if(cursor.getCount()>1){
            if (cursor.moveToFirst()) {
                do {
                    memberId.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        }else if(cursor.getCount()==1){
            cursor.moveToFirst();
            Toast.makeText(context,"Data is "+cursor.getInt(0),Toast.LENGTH_LONG).show();
            memberId.add(cursor.getInt(0));
        }
        if(memberId.size()>0){
            List<UserDTO> memberList=new ArrayList<UserDTO>();
            for(int id:memberId){
                String getMemberListById="SELECT * FROM "+USER_TABLE_NAME+" WHERE "+USER_COL_1+" = "+id;
                Cursor cursor1=sqLiteDatabase.rawQuery(getMemberListById,null);
                if(cursor1.moveToFirst()){
                      memberList.add(new UserDTO(cursor1.getInt(0),cursor1.getString(1),cursor1.getString(2),cursor1.getString(3)));
                }
            }
            return memberList;
        }

        return null;
    }

    public boolean savePerson(String user, String email) {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_COL_2,user);
        contentValues.put(USER_COL_4,email);
        long res=sqLiteDatabase.insert(USER_TABLE_NAME,null,contentValues);
        if(res==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkUserByName(String user) {
        String selectUserByName="SELECT * FROM "+USER_TABLE_NAME+ " WHERE "+USER_COL_2+" = '"+user+"'";
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectUserByName,null);
        boolean res=false;
        int userId=0;
        if(cursor!=null){
            if(cursor.moveToFirst()){
                userId=cursor.getInt(0);
            }
        }
        if(userId>0){
            return true;
        }
        return res;
    }
}
