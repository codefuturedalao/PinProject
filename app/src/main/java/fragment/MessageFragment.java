package fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    private ImageButton button1;
    private ImageButton button2;
    private EditText messageView;
    private OnSendMessageListener listener;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        messageView = view.findViewById(R.id.messageSend);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        button1 = (ImageButton) getActivity().findViewById(R.id.clearMessage);
        String message = messageView.getText().toString();
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           String msg1 = messageView.getText().toString();
                                           if (!TextUtils.isEmpty(messageView.getText().toString())) {
                                               messageView.setText("");
                                               messageView.requestFocus();
                                               getActivity().onBackPressed();
                                           }
                                       }
                                   }

        );

        button2 = (ImageButton) getActivity().findViewById(R.id.sMessage);
        button2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           String msg2 = messageView.getText().toString();
                                           listener.OnSendMessage(messageView.getText().toString());
                                       }
                                   }
        );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnSendMessageListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " Activity must implements OnSendMessage ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnSendMessageListener {
        public void OnSendMessage(String message);
    }
}
