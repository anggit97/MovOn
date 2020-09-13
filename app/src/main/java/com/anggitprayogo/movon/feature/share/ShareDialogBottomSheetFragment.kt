package com.anggitprayogo.movon.feature.share

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.anggitprayogo.movon.R
import com.anggitprayogo.movon.data.local.entity.ShareEntity
import com.eoa.tech.core.util.intent.IntentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.share_bottom_sheet_layout.*


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
class ShareDialogBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var movie: ShareEntity? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(MovieData)
    }

    fun newInstance(): ShareDialogBottomSheetFragment? {
        return ShareDialogBottomSheetFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.share_bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle.text = movie?.title
        tvContent.text = movie?.body

        view.findViewById<Button>(R.id.btnShare).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnShare) {
            val content = getString(R.string.content_share_tmp, movie?.title, movie?.body)
            val shareIntent = IntentManager.createShareIntent(content)
            startActivity(Intent.createChooser(shareIntent, "Share"))
            dismiss()
        }
    }

    companion object {
        const val TAG = "ShareDialogBottomSheetFragment"
        const val MovieData = "movie_data"
    }
}