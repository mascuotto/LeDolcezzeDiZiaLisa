package com.mascuttoapp.ledolcezzedizialisa

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mascuttoapp.ledolcezzedizialisa.adapter.StepAdapter
import com.mascuttoapp.ledolcezzedizialisa.util.Utils

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var mVideoView: VideoView? = null
    private var mBufferingTextView: TextView? = null
    // Current playback position (in milliseconds).
    private var mCurrentPosition = 0
    // Tag for the instance state bundle.
    private val PLAYBACK_TIME = "play_time"
    private lateinit var formulaSelected : com.mascuttoapp.ledolcezzedizialisa.bean.Formula
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var imageResult: ImageView
    private lateinit var probressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        formulaSelected = arguments?.getSerializable("formulaSelected" ) as com.mascuttoapp.ledolcezzedizialisa.bean.Formula
        viewManager = LinearLayoutManager(activity)
        viewAdapter = StepAdapter(formulaSelected.steps)
    }

   override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView!!.currentPosition)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_second, container, false)

        mVideoView = view.findViewById(R.id.videoDetail);
        mBufferingTextView = view.findViewById(R.id.buffering_textview);

        // Set up the media controller widget and attach it to the video view.
        var controller = MediaController(context) 
        controller.setMediaPlayer(mVideoView)
        mVideoView?.setMediaController(controller)

        var name = view.findViewById(R.id.nameDetail) as TextView
        var duration = view.findViewById(R.id.durationDetail) as TextView
        var level = view.findViewById(R.id.levelDetail) as TextView
        var elements = view.findViewById(R.id.elementsDetail) as TextView
        var moreElements = view.findViewById(R.id.popup_elements) as ImageView

        var elementsAL: ArrayList<String> = ArrayList()
        imageResult =  view.findViewById(R.id.imageViewResult) as ImageView
        probressBar = view.findViewById(R.id.progressBar) as ProgressBar
        formulaSelected.elements.forEach {
            elementsAL.add(it)
        }
        moreElements.setOnClickListener {
            var bundle = bundleOf("elements" to elementsAL)
            bundle?.putStringArrayList("elements", elementsAL )
            ElementFragment.newInstance(elementsAL).show(requireActivity().supportFragmentManager, "    sdcdcd")
        }

        name.text = formulaSelected.name
        duration.text = getString(R.string.duration).plus(formulaSelected.duration.toString())
        level.text = getString(R.string.level).plus(formulaSelected.level)

        val recyclerView: RecyclerView = view.findViewById(R.id.stepsDetail)
        recyclerView.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
        var stringElements = ""
        formulaSelected.elements.forEach {
            stringElements = stringElements.plus(it.plus(", "))
        }
        elements.text = stringElements
        return view
    }

    override fun onStart() {
        super.onStart()
        if(formulaSelected.video != null)
            initializePlayer(formulaSelected.video ?: "")
        else doBackgroundImage(imageResult, formulaSelected.icon)
    }

    private fun doBackgroundImage(imageResult: ImageView, icon: String?) {
        imageResult.visibility = View.VISIBLE
        probressBar.visibility = View.VISIBLE
        Utils.doInBackground(imageResult, probressBar, formulaSelected.icon)
    }

    override fun onStop() {
        super.onStop()
        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer()
    }

    private fun releasePlayer() {
        mVideoView!!.stopPlayback()
    }

    private fun initializePlayer(video: String) {
        // Show the "Buffering..." message while the video loads.
        mBufferingTextView!!.visibility = VideoView.VISIBLE

        // Buffer and decode the video sample.
        val videoUri: Uri = Utils.getMedia(video)
        mVideoView!!.setVideoURI(videoUri)

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView!!.setOnPreparedListener { // Hide buffering message.
            mBufferingTextView!!.visibility = VideoView.INVISIBLE

            // Restore saved position, if available.
            if (mCurrentPosition > 0) {
                mVideoView!!.seekTo(mCurrentPosition)
            } else {
                // Skipping to 1 shows the first frame of the video.
                mVideoView!!.seekTo(1)
            }

            // Start playing!
            mVideoView!!.start()
        }
        mVideoView!!.setOnCompletionListener {
            // Return the video position to the start.
            mVideoView!!.seekTo(0)
        }
    }
}