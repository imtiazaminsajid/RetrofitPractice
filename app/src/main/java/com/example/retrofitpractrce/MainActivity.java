package com.example.retrofitpractrce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        
        //getComments();

        createPost();


    }

    private void createPost() {
        Post post = new Post(23, "New Title", "New Text");

        Call<Post> call =  jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: "+ response.code() + "\n";
                content += "ID: "+postResponse.getId()+"\n";
                content += "User ID: "+postResponse.getUserId()+"\n";
                content += "Title: "+postResponse.getTitle()+"\n";
                content += "Text: "+postResponse.getText()+"\n";

                textViewResult.append(content+"\n");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });
    }

    private void getComments() {

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/4/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()){

                    textViewResult.setText("Code: "+response.code());
                    return;

                }

                List<Comment> comments = response.body();

                for (Comment comment : comments){
                    String content = "";
                    content += "ID: "+comment.getId()+"\n";
                    content += "Post ID: "+comment.getPostId()+"\n";
                    content += "Name: "+comment.getName()+"\n";
                    content += "Email: "+comment.getEmail()+"\n";
                    content += "Text: "+comment.getText()+"\n";


                    textViewResult.append(content+"\n");
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });

    }

    private void getPosts(){

        Map<String, String> parameters =  new HashMap<>();
        parameters.put("userId", "2");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()){

                    textViewResult.setText("Code: "+response.code());
                    return;

                }

                List<Post> posts = response.body();

                for (Post post : posts){
                    String content = "";
                    content += "ID: "+post.getId()+"\n";
                    content += "User ID: "+post.getUserId()+"\n";
                    content += "Title: "+post.getTitle()+"\n";
                    content += "Text: "+post.getText()+"\n";


                    textViewResult.append(content+"\n");
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });

    }
}
