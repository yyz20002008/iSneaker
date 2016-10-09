package gradle.udacity.com.isneaker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }


    GridView gridview;
    private ImageAdapter mImages;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        mImages=new ImageAdapter(getActivity());
      /*  gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(mImages);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
*/
        StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.list);

        stickyList.setAdapter(mImages);

        return rootView;
    }
}
