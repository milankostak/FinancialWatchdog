package hk.edu.cityu.financialwatchdog;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Matt on 29. 7. 2016.
 */
public class PageFragment extends Fragment {
    private static final String ARG_PAGE_NUMBER = "page_number";
    private int[] pages = {
            R.layout.page_one,
            R.layout.page_two,
            R.layout.page_three
    };

    public PageFragment() {
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
        View rootView = inflater.inflate(pages[page - 1], container, false);
        return rootView;
    }
}