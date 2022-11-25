package com.project.mytask.Fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.project.mytask.R


class FullScreenFragmentDialog : DialogFragment() {

    lateinit var userId: TextView
    lateinit var id: TextView
    lateinit var title: TextView
    lateinit var body: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return inflater.inflate(R.layout.layout_full_screen_fragment, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = view.findViewById(R.id.txt1)
        id = view.findViewById(R.id.txt2)
        title = view.findViewById(R.id.txt3)
        body = view.findViewById(R.id.txt4)

        userId.text = arguments?.getString("userId")
        id.text = arguments?.getString("id")
        title.text = arguments?.getString("title")
        body.text = arguments?.getString("body")

       // view.userId.text = arguments?.getString("title")
       // view.text_account_detail_content.text = arguments?.getString("content")

       /* button.setOnClickListener {
            callbackListener.onDataReceived(editText.text.toString())
            dismiss()
        }*/
    }

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                this@FullScreenFragmentDialog.onBackPressed()
                // And maybe you also want to call cancel() here.
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun onBackPressed() {
        // your code here
    }

}