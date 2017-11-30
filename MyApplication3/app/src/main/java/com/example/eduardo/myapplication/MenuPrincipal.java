package com.example.eduardo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


    }


    public void play(View view){
        Intent it = new Intent(MenuPrincipal.this, game.class);
        startActivity(it);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        /////////////////////////////////////
        //Funçoes para setar o TIME
        if (id == R.id.easy) {
            //MODO DE EXIBICAO  de MENSAGEM 1
            // Toast.makeText(getApplicationContext(), "Easy selected",
            //      Toast.LENGTH_SHORT).show();

            //MODO DE EXIBICAO  de MENSAGEM 2
            Snackbar.make(this.findViewById(android.R.id.content),"Easy selected",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return true;
        }

        if (id == R.id.normal) {
            //MODO DE EXIBICAO  de MENSAGEM 1
            // Toast.makeText(getApplicationContext(), "Normal selected",
            //         Toast.LENGTH_SHORT).show();

            //MODO DE EXIBICAO  de MENSAGEM 2
            Snackbar.make(this.findViewById(android.R.id.content),"Normal selected",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return true;
        }

        if (id == R.id.hard) {
            //MODO DE EXIBICAO  de MENSAGEM 1
            //  Toast.makeText(getApplicationContext(), "Hard selected",
            //          Toast.LENGTH_SHORT).show();

            // MODO DE EXIBICAO  de MENSAGEM 2
            Snackbar.make(this.findViewById(android.R.id.content),"Hard selected",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return true;
        }
        ////////////////////////////////////

        return super.onOptionsItemSelected(item);
    }

}
