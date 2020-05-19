package com.example.noteapp.presentation;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.example.noteapp.usecase.NotesAdaptor;
import com.example.noteapp.usecase.SwipeToDeleteCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesBoard extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private NotesAdaptor adaptor;
    private LinearLayout linearLayout;
    private FloatingActionButton add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        linearLayout = findViewById(R.id.conLayout);
        add_btn = findViewById(R.id.save_btn_notes);


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#0000'>" + " All notes " + "</font>"));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesBoard.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView=findViewById(R.id.recycler_Note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();
        enableSwipeToDeleteAndUndo();


    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void getData() {


        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // RecyclerView Adaptor
                adaptor = new NotesAdaptor(notes);
                recyclerView.setAdapter(adaptor);

            }
        });


    }


    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getLayoutPosition();
//                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();

                final Note item = adaptor.getData().get(position);


                adaptor.removeItem(position);
                viewModel.delete(item).observe(NotesBoard.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                    }
                });
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);

                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adaptor.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);

                        viewModel.insertData(item).observe(NotesBoard.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                            }
                        });
                    }
                });


                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();



            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setBackgroundColor((0x52C0C0C0));
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("Search notes");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adaptor.getFilter().filter(newText);
                return false;
            }


        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(getApplicationContext(),"sa"  , Toast.LENGTH_SHORT).show();
        Note note = null;
        if (data != null) {
            Bundle bundle = data.getExtras();
            int position = data.getIntExtra("position", -1);
            note = (Note) bundle.getSerializable("updatedNote");
//                Toast.makeText(getApplicationContext(), "" +note.getId(), Toast.LENGTH_SHORT).show();

            Note finalNote = note;
            viewModel.update(note).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    adaptor.adaptorNotify(finalNote, position);

                }
            });
        }


    }


}