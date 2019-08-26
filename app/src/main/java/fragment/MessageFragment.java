package fragment;


import android.os.Bundle;
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
    private EditText messageView;

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
        final String message = messageView.getText().toString();
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           String msg1 = message;
                                           if (!TextUtils.isEmpty(message)) {
                                               messageView.setText("");
                                               messageView.requestFocus();
                                           }
                                       }
                                   }

        );

    }
}
