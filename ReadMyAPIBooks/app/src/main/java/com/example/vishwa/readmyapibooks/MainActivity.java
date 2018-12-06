package com.example.vishwa.readmyapibooks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("Hi there");
        setContentView(R.layout.activity_main);

        List<String> books=getbooks();
        System.out.print("books: " +books.toString());
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_main, books);
        ListView listView = (ListView) findViewById(R.id.listview_books);
        listView.setAdapter(adapter);
    }

    private List<String> getbooks() {
        final List<String> books= new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/advanced-jaxrs/webapi/test/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService =retrofit.create(APIService.class);

        apiService.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> bookList = response.body();

                //Creating an String array for the ListView
//                 books.size() = bookList.size();

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < bookList.size(); i++) {
                    books.add(bookList.get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

            }
        });
        return books;
    }
}
