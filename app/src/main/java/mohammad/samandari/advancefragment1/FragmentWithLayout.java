package mohammad.samandari.advancefragment1;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWithLayout extends Fragment {

    EditText editText;
    Button button;

    OnFragmentInteractionListener mOnFragmentInteractionListener;
    public FragmentWithLayout () {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText = getActivity().findViewById(R.id.edittextFragment2);
        button = getActivity().findViewById(R.id.btnFragment2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mOnFragmentInteractionListener.onFragment2ButtonClick(editText.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_with_layout, container, false);
    }

    // Interface definition and onFeedbackChoice() callback.
    interface OnFragmentInteractionListener {
        void onFragment2ButtonClick (String string);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mOnFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                                         + " must implement OnFragmentInteractionListener");
        }
    }


}
