package com.example.sse.customlistview_sse;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

//Step-By-Step, Setting up the ListView

    private
    ListView lvEpisodes;     //Reference to the listview GUI component
    MyCustomAdapter lvAdapter;   //Reference to the Adapter used to populate the listview.
    SharedPreferences simpleAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleAppInfo = getSharedPreferences("ActivityMainInfo", Context.MODE_PRIVATE);
        lvEpisodes = (ListView) findViewById(R.id.lvEpisodes);
//        lvAdapter = new MyCustomAdapter(this.getBaseContext());  //instead of passing the boring default string adapter, let's pass our own, see class MyCustomAdapter below!
//        String nameStrings = simpleAppInfo.getString("order", "");
//        String commentStrings = simpleAppInfo.getString("comments", "");
//        String ratingStrings = simpleAppInfo.getString("ratings", "");
//        System.out.println(ratingStrings);
//        ArrayList<String> names = new ArrayList<>();
//        ArrayList<Float> ratings = new ArrayList<>();
//        ArrayList<String> comments = new ArrayList<>();
//        if (!nameStrings.equals("")) {
//            String nameArray[] = nameStrings.split(",");
//            String commentsArray[] = commentStrings.split(",");
//            String ratingArray[] = ratingStrings.split(",");
//            System.out.println(commentsArray[1]);
//            for (int i = 0; i < nameArray.length; i++) {
//                names.add(nameArray[i]);
//                comments.add(commentsArray[i]);
//                ratings.add(Float.parseFloat(ratingArray[i]));
//            }
////            names = (ArrayList<String>) Arrays.asList(nameStrings.split(","));
//        }
//
//        if (names.size() == 0) {
//            lvAdapter = new MyCustomAdapter(this.getBaseContext());
//        } else {
//            lvAdapter = new MyCustomAdapter(this.getBaseContext(), names, ratings, comments);
//        }
        lvAdapter = new MyCustomAdapter(this.getBaseContext(), simpleAppInfo);
        lvEpisodes.setAdapter(lvAdapter);
        lvEpisodes.setItemsCanFocus(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);   //get rid of default behavior.

        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.my_test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnu_zero) {
            Toast.makeText(getBaseContext(), "Menu Zero.", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.mnu_one) {
            Toast.makeText(getBaseContext(), "Ring ring, Hi Mom.", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.mnu_three) {
            Toast.makeText(getBaseContext(), "Hangup it's a telemarketer.", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);  //if none of the above are true, do the default and return a boolean.
    }

//    @Override
//    protected void onStop() {
//        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityMainInfo", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = simpleAppInfo.edit();
//        String order = "";
//        String comments = "";
//        String ratings = "";
//        for (int i = 0; i < lvAdapter.getCount(); i++) {
//            if (i != lvAdapter.getCount() - 1) {
//                order += ((Episode) lvAdapter.getItem(i)).getName() + ",";
//                if (((Episode) lvAdapter.getItem(i)).getComment().equals("")) {
//                    comments += " ,";
//                } else {
//                    comments += ((Episode) lvAdapter.getItem(i)).getComment() + ",";
//                }
//                ratings += ((Episode) lvAdapter.getItem(i)).getRating().toString() + ",";
//            } else {
//                order += ((Episode) lvAdapter.getItem(i)).getName();
//                if (((Episode) lvAdapter.getItem(i)).getComment().equals("")) {
//                    comments += " ,";
//                } else {
//                    comments += ((Episode) lvAdapter.getItem(i)).getComment();
//                }
//                ratings += ((Episode) lvAdapter.getItem(i)).getRating().toString();
//            }
//        }
//        editor.putString("order", order);
//        editor.putString("comments", comments);
//        editor.putString("ratings", ratings);
//        System.out.println(order + " " + comments + " " + ratings);
//        editor.apply();
//        super.onStop();
//    }
    @Override
    protected void onResume() {
        //every time we call the onResume, we need to pass the newest data to the adapter
        simpleAppInfo = getSharedPreferences("ActivityMainInfo", Context.MODE_PRIVATE);
        lvAdapter = new MyCustomAdapter(this.getBaseContext(), simpleAppInfo);
        super.onResume();

    }
}


class MyCustomAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Episode> episodes = new ArrayList<>();
    private Context context;
    private SharedPreferences simpleAppInfo;


    public MyCustomAdapter(Context aContext, SharedPreferences sharedPreferences) {
        context = aContext;
        simpleAppInfo = sharedPreferences;
        String episodesTemp[] = aContext.getResources().getStringArray(R.array.episodes);
        String episodeDescriptions[] = aContext.getResources().getStringArray(R.array.episode_descriptions);

        Integer imageArray[] = {R.drawable.st_spocks_brain, R.drawable.st_arena__kirk_gorn, R.drawable.st_this_side_of_paradise__spock_in_love,
                R.drawable.st_mirror_mirror__evil_spock_and_good_kirk, R.drawable.st_platos_stepchildren__kirk_spock, R.drawable.st_the_naked_time__sulu_sword,
                R.drawable.st_the_trouble_with_tribbles__kirk_tribbles};
        for (int i = 0; i < episodesTemp.length; i++) {
            Episode temp = new Episode(episodesTemp[i], episodeDescriptions[i], imageArray[i], 0, "");
            episodes.add(temp);
        }
    }

    public MyCustomAdapter(Context aContext, ArrayList<String> aNames, ArrayList<Float> aRatings, ArrayList<String> comments) {
        context = aContext;
        String episodesTemp[] = aContext.getResources().getStringArray(R.array.episodes);
        String episodeDescriptions[] = aContext.getResources().getStringArray(R.array.episode_descriptions);

        Integer imageArray[] = {R.drawable.st_spocks_brain, R.drawable.st_arena__kirk_gorn, R.drawable.st_this_side_of_paradise__spock_in_love,
                R.drawable.st_mirror_mirror__evil_spock_and_good_kirk, R.drawable.st_platos_stepchildren__kirk_spock, R.drawable.st_the_naked_time__sulu_sword,
                R.drawable.st_the_trouble_with_tribbles__kirk_tribbles};

        for (int i = 0; i < aNames.size(); i++) {
            for (int j = 0; j < episodesTemp.length; j++) {
                if (aNames.get(i).equals(episodesTemp[j])) {
                    Episode temp = new Episode(episodesTemp[j], episodeDescriptions[j], imageArray[j], aRatings.get(i), comments.get(i));
                    episodes.add(temp);
                    break;
                }
            }
        }
    }

    @Override
    public int getCount() {

        return episodes.size();

    }


    @Override
    public Episode getItem(int position) {

        return episodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {  //convertView is Row (it may be null), parent is the layout that has the row Views.
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //Inflater's are awesome, they convert xml to Java Objects!
            convertView = inflater.inflate(R.layout.listview_row, parent, false);
            holder.tvEpisodeTitle = (TextView) convertView.findViewById(R.id.tvEpisodeTitle);
            holder.tvEpisodeDescription = (TextView) convertView.findViewById(R.id.tvEpisodeDescription);
            holder.imgEpisode = (ImageView) convertView.findViewById(R.id.imgEpisode);
            holder.rbEpisodes = (RatingBar) convertView.findViewById(R.id.rbEpisode);
            holder.edtComment = (EditText) convertView.findViewById(R.id.edtComment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tvEpisodeTitle.setText(episodes.get(position).getName());
        holder.tvEpisodeDescription.setText(episodes.get(position).getDescription());
        holder.imgEpisode.setImageResource(episodes.get(position).getImage());
        holder.rbEpisodes.setFocusable(true);
        holder.rbEpisodes.setRating(simpleAppInfo.getFloat("rating " + position, 0.0f));
        String comment = simpleAppInfo.getString("comment " + position, "");
        System.out.println("the comment is: "+ comment);
        holder.edtComment.setFocusable(true);
        holder.edtComment.setText(simpleAppInfo.getString("comment " + position, ""));
        holder.edtComment.setTag(null);
        holder.rbEpisodes.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    System.out.println("on rating changed");
                    episodes.get(position).setRating(rating);
                    SharedPreferences.Editor editor = simpleAppInfo.edit();
                    editor.putFloat("rating " + position, rating);
                    editor.apply();
                    Map<String, ?> map = simpleAppInfo.getAll();
                    System.out.println(map);
//                    System.out.println("episode number " + position + " is: " + episodes.get(position));
                }
            }
        });

        holder.edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.edtComment.getTag() == null) {
                    System.out.println("after text changed " + position);
                    final EditText comment = (EditText) holder.edtComment;
                    if (comment.getText().toString().length() > 0) {
                        Episode tmpEpi = episodes.get(position);
                        tmpEpi.setComment(s.toString());
                        episodes.set(position, tmpEpi);
                    }
                }
                System.out.println("current position is " + position);
                SharedPreferences.Editor editor = simpleAppInfo.edit();
                editor.putString("comment " + position, s.toString());
                editor.apply();
//                episodes.get(position).setComment(edtComment.getText().toString());
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imgEpisode;
        TextView tvEpisodeTitle;
        TextView tvEpisodeDescription;
        RatingBar rbEpisodes;
        EditText edtComment;
    }



}