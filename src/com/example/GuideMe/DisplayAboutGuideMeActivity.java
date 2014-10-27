package com.example.GuideMe;
import com.example.GuideMe.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;




public class DisplayAboutGuideMeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_about_guide_me);
		
		ImageView image = (ImageView) findViewById(R.id.imageView1);
        image.setImageResource(R.drawable.ic_launcher);

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
        
        
		//Show instructions.
//		TextView about = (TextView) findViewById(R.id.about);
//		
//		String aboutText = "Press the green button to start recording.\n\n" +
//								"When the recording is done press the red button to stop recording.\n\n" + 
//								"The recorded files will be saved in the GuideMe folder.";
//				
//		about.setText(aboutText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_about_guide_me, menu);
		return true;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_display_about_guide_me, container, false);
			return rootView;
		}
	}

}
