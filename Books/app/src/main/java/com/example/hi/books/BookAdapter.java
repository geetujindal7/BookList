package com.example.hi.books;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by hi on 29-04-2017.
 */
public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, List<Book> books) {
        super(context,0, books);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Book getbook = getItem(position);
        TextView authorview = (TextView) listItemView.findViewById(R.id.author);
        authorview.setText(getbook.getAuthor());
        TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
        publisherView.setText(getbook.getPublisher());
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(getbook.getTitle());
        return listItemView;
    }
}