package com.example.hi.books;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by hi on 05-05-2017.
 */

public class Loading extends AsyncTaskLoader<List<Book>> {
        private String url1;
        public Loading(Context context, String url) {
            super(context);
            url1 = url;
        }
        @Override
        protected void onStartLoading() {
            forceLoad();
        }
        @Override
        public List<Book> loadInBackground() {
            if (url1 == null) {
                return null;
            }
            List<Book> res = Utils.fetchBooksInfo(url1);
            return res;
        }
}
