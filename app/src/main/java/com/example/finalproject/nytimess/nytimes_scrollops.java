package com.example.finalproject.nytimess;

import android.widget.AbsListView;


/**
 * The type Nytimes scrollops.
 */
public abstract class nytimes_scrollops implements AbsListView.OnScrollListener {

    private int visibleThreshold = 6;
    private int startingPageIndex = 0;
    private int previousitemCount = 0;

    private int currentPage = 0;
    private boolean loading = true;


    /**
     * Instantiates a new Nytimes scrollops.
     */
    public nytimes_scrollops() {
    }

    /**
     * Instantiates a new Nytimes scrollops.
     *
     * @param visibleThreshold the visible threshold
     */
    public nytimes_scrollops(int visibleThreshold) {

        this.visibleThreshold = visibleThreshold;
    }

    /**
     * Instantiates a new Nytimes scrollops.
     *
     * @param visibleThreshold the visible threshold
     * @param startingPage     the starting page
     */
    public nytimes_scrollops(int visibleThreshold, int startingPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startingPage;
        this.currentPage = startingPage;
    }



    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int itemCount) {


        if (itemCount < previousitemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousitemCount = itemCount;

            if (itemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (itemCount > previousitemCount)) {
            loading = false;
            previousitemCount = itemCount;
            currentPage++;
        }


        if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= itemCount) {
            loading = onLoadMore(currentPage + 1, itemCount);
        }
    }


    /**
     * On load more boolean.
     *
     * @param page            the page
     * @param totalItemsCount the total items count
     * @return the boolean
     */
    public abstract boolean onLoadMore(int page, int totalItemsCount);


}
