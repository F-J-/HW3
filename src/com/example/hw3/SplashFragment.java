package com.example.hw3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SplashFragment extends Fragment {
	
	public static final int[] STUDENT_NAME = {R.string.student1_name, R.string.student2_name};
	public static final int[] STUDENT_ID = {R.string.student1_id, R.string.student2_id};
	
	private String name;
	private String sid;
	private int position;
	
	static SplashFragment newInstance(int i) {
		SplashFragment si = new SplashFragment();
		
		Bundle args = new Bundle();
		args.putInt("position", i);
		si.setArguments(args);
		
		return si;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
			this.position = getArguments().getInt("position");
		else
			this.position = 0;
        this.sid = getString(STUDENT_ID[position]);
        this.name = getString(STUDENT_NAME[position]);
       
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_splash, container, false);
        View tv = v.findViewById(R.id.name_text);
        ((TextView)tv).setText(name + " " + sid);
        return v;
    }
	
}