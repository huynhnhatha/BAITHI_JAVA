package com.example.danhba_dienthoai;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener
{
	final String DATABASE_NAME = "danhba.sqlite";
	SQLiteDatabase database;
	EditText editID,editTEN,editSDT;
	ImageView imgAdd,imgDelete,imgModify,imgView,imgViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM danhbadt", null);
        cursor.moveToFirst();
        editID=(EditText)findViewById(R.id.editid);
        editTEN=(EditText)findViewById(R.id.editten);
        editSDT=(EditText)findViewById(R.id.editsdt);
        imgAdd=(ImageView)findViewById(R.id.imgAdd);
        imgDelete=(ImageView)findViewById(R.id.imgDelete);
        imgView=(ImageView)findViewById(R.id.imgView);
        imgModify=(ImageView)findViewById(R.id.imgModify);
        imgViewAll=(ImageView)findViewById(R.id.imgView_All);
 
        imgAdd.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        imgModify.setOnClickListener(this);
        imgView.setOnClickListener(this);
        imgViewAll.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View view) {
		
    	if(view==imgAdd)
    	{
    		if(editID.getText().toString().trim().length()==0||
    		   editTEN.getText().toString().trim().length()==0||
    		   editSDT.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter all values");
    			return;
    		}
    		database.execSQL("INSERT INTO danhbadt VALUES('"+editID.getText()+"','"+editTEN.getText()+
    				   "','"+editSDT.getText()+"');");
    		showMessage("Success", "Record added");
    		clearText();
    	}
    	if(view==imgDelete)
    	{
    		if(editID.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter ID");
    			return;
    		}
    		Cursor c=database.rawQuery("SELECT * FROM danhbadt WHERE id='"+editID.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			database.execSQL("DELETE FROM danhbadt WHERE id='"+editID.getText()+"'");
    			showMessage("Success", "Record Deleted");
    		}
    		else
    		{
    			showMessage("Error", "Invalid ID");
    		}
    		clearText();
    	}
    	if(view==imgModify)
    	{
    		if(editID.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter ID");
    			return;
    		}
    		Cursor c=database.rawQuery("SELECT * FROM danhbadt WHERE id='"+editID.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			database.execSQL("UPDATE danhbadt SET ten='"+editTEN.getText()+"',dt='"+editSDT.getText()+
    					"' WHERE ten='"+editID.getText()+"'");
    			showMessage("Success", "Record Modified");
    		}
    		else
    		{
    			showMessage("Error", "Invalid ID");
    		}
    		clearText();
    	}
    	if(view==imgView)
    	{
    		if(editID.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter ID");
    			return;
    		}
    		Cursor c=database.rawQuery("SELECT * FROM danhbadt WHERE id='"+editID.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			editTEN.setText(c.getString(1));
    			editSDT .setText(c.getString(2));
    		}
    		else
    		{
    			showMessage("Error", "Invalid ID");
    			clearText();
    		}
    	}
    	if(view==imgViewAll)
    	{
    		Cursor c=database.rawQuery("SELECT * FROM danhbadt", null);
    		if(c.getCount()==0)
    		{
    			showMessage("Error", "No records found");
    			return;
    		}
    		StringBuffer buffer=new StringBuffer();
    		while(c.moveToNext())
    		{
    			buffer.append("Tên: "+c.getString(0)+"\n");
    			buffer.append("Ngày Sinh: "+c.getString(1)+"\n");
    			buffer.append("Số Điện Thoại: "+c.getString(2)+"\n\n");
    		}
    		showMessage("DANH BẠ ĐIỆN THOẠI", buffer.toString());
    	}
    }
    	 public void showMessage(String title,String message)
    	    {
    	    	Builder builder=new Builder(this);
    	    	builder.setCancelable(true);
    	    	builder.setTitle(title);
    	    	builder.setMessage(message);
    	    	builder.show();
    		}
    	    public void clearText()
    	    {
    	    	editID.setText("");
    	    	editTEN.setText("");
    	    	editSDT.setText("");
    	    	editID.requestFocus();
    	    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	
}
