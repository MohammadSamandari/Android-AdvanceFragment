package mohammad.samandari.advancefragment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentWithLayout.OnFragmentInteractionListener {

    FragmentManager fragmentManager;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Trying to show the Fragment1
        fragmentManager = getSupportFragmentManager();

    }

    public void showFragment1 (View view) {
        //Get an instance of the Fragment
        FragmentWithLayoutWithFactorymethods fragment1 = FragmentWithLayoutWithFactorymethods.newInstance("", "");

        //Setting up the Fragment Transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Cheaking to see if fragment is showing.
        if (fragmentManager.findFragmentById(R.id.frameLayout) == null) {
            //Adding the Fragment to the layout
            transaction.add(R.id.frameLayout, fragment1).addToBackStack(null).commit();
        }
    }

    public void removeFragment1 (View view) {
        FragmentWithLayoutWithFactorymethods fragment1 = (FragmentWithLayoutWithFactorymethods) fragmentManager.findFragmentById(R.id.frameLayout);

        //checking to see if the fragment is showing
        if (fragment1 != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment1).addToBackStack(null).commit();
        }
    }

    public void showFragment2 (View view) {
        FragmentWithLayout fragment2 = new FragmentWithLayout();

        if (fragmentManager.findFragmentById(R.id.frameLayout) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.add(R.id.frameLayout, fragment2).commit();
        }
    }

    public void removeFragment2 (View view) {
        FragmentWithLayout fragment2 = (FragmentWithLayout) fragmentManager.findFragmentById(R.id.frameLayout);

        if (fragment2 != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment2).commit();
        }

    }

    public void showDatePicker (View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(datePickerFragment, "tag").commit();

    }

    @Override
    public void onFragment2ButtonClick (String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
