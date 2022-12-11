package app;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.UTTCOOLER.Integradora.R;
import com.bumptech.glide.Glide;


public class HomeFragment extends Fragment {



private ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT)
        {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        imageView=view.findViewById(R.id.gif);
        String url="https://cdn.homecrux.com/wp-content/uploads/2017/06/autonomous-follow-me-cooler.gif";
        Uri urlparse= Uri.parse(url);
        Glide.with(getContext()).load(urlparse).into(imageView);

        return view;
    }

}