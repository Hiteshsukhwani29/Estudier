package com.scriptech.vstudy.ui.fragments.feedback

import android.R.layout
import android.animation.Animator
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.scriptech.vstudy.databinding.FragFeedbackBinding
import com.scriptech.vstudy.model.FeedbackModel
import com.scriptech.vstudy.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*

class Feedback : Fragment() {
    private var _binding: FragFeedbackBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: FeedbackViewModel

    var Name: String? = null
    var Number: String? = null
    var Email: String? = null

    var category = arrayOf("Add_Notes", "Add Book", "Add Video", "Report Content", "Feedback")
    var department = arrayOf(
        "Computer Engineering",
        "Information Technology",
        "Mechanical Engineering",
        "Civil Engineering",
        "Chemical Engineering",
        "CSBS",
        "Electrical Engineering",
        "Electronics Engineering",
        "ENTC",
        "None"
    )

    var dept_str: String? = null
    var pos = 0
    var feedback_type: Int = 0
    var validated: Int = 0
    var currentuser: String? = null
    var docURL: String? = ""
    lateinit var imageuri: Uri
    var dialog: ProgressDialog? = null

    private var FeedbackSubmitted: LottieAnimationView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragFeedbackBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = Color.parseColor("#20111111")
                requireActivity().window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }

        binding.uploadFile.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "application/pdf"
            startActivityForResult(galleryIntent, 1)
        }

        FeedbackSubmitted?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(p0: Animator?) {
                FeedbackDirections.actionNavigationFeedbackSelf()
            }

            override fun onAnimationCancel(p0: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(p0: Animator?) {
                TODO("Not yet implemented")
            }

        })

        val aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireActivity(), layout.simple_spinner_item, category)
        aa.setDropDownViewResource(layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = aa

        val dept: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireActivity(), layout.simple_spinner_item, department)
        dept.setDropDownViewResource(layout.simple_spinner_dropdown_item)
        binding.departmentSpin.adapter = dept

        val userRepository = UserRepository()

        val viewModelFactory = FeedbackViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FeedbackViewModel::class.java]

//        val user = viewModel.getUserInfo(currentuser.toString())
//        Name = user?.name.toString()
//        Email = user?.email.toString()
//        Number = user?.number.toString()
//        binding.personalInfoEmail.setText(user?.email.toString())
//        binding.personalInfoName.setText(user?.name.toString())

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                pos = position
                feedback_type = pos
                when (position) {
                    0 -> {
                        hideAll()
                        binding.uploadFile.visibility = View.VISIBLE
                        binding.AddNotes.visibility = View.VISIBLE
                    }
                    1 -> {
                        hideAll()
                        binding.uploadFile.visibility = View.VISIBLE
                        binding.AddBook.visibility = View.VISIBLE
                    }
                    2 -> {
                        hideAll()
                        binding.uploadFile.visibility = View.VISIBLE
                        binding.AddBook.visibility = View.VISIBLE
                    }
                    3 -> {
                        hideAll()
                        binding.uploadFile.visibility = View.GONE
                        binding.Report.visibility = View.VISIBLE
                    }
                    4 -> {
                        hideAll()
                        binding.uploadFile.visibility = View.GONE
                        binding.Feedback.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                hideAll()
                binding.uploadFile.visibility = View.VISIBLE
                binding.AddNotes.visibility = View.VISIBLE
            }
        }

        binding.departmentSpin.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    dept_str = department[position]
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }

        binding.submitForm.setOnClickListener {
            val date = Calendar.getInstance().time
            when (feedback_type) {
                0 -> validated = notesValidation()
                1 -> validated = booksValidation()
                2 -> validated = videosValidation()
                3 -> validated = reportValidation()
                4 -> validated = feedbackValidation()
            }
            if (validated == 1) {
                FeedbackModel(
                    date,
                    Name,
                    Email,
                    Number,
                    dept_str,
                    binding.addNotesNotesname.text.toString(),
                    binding.addNotesNotesauthor.text.toString(),
                    binding.addBooksBookname.text.toString(),
                    binding.addBooksBookauthor.text.toString(),
                    binding.addVideoVideoname.text.toString(),
                    binding.addVideoVideoauthor.text.toString(),
                    binding.reportDesc.text.toString(),
                    binding.feedbackDesc.text.toString()
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.contributeToEstudier()
                    }
                }
            }
        }
        return root
    }

    fun hideAll() {
        FeedbackSubmitted?.visibility = View.GONE
        binding.feebackMain.visibility = View.VISIBLE
        binding.AddNotes.visibility = View.GONE
        binding.AddBook.visibility = View.GONE
        binding.AddVideos.visibility = View.GONE
        binding.Report.visibility = View.GONE
        binding.Feedback.visibility = View.GONE
    }

    fun notesValidation(): Int {
        if (binding.addNotesNotesname.text.toString().length < 5) {
            binding.addNotesNotesname.error = "Enter Valid Notes Topic"
            binding.addNotesNotesname.requestFocus()
        }
        if (binding.addNotesNotesauthor.text.toString().length < 5) {
            binding.addNotesNotesauthor.error = "Enter Valid Author Name"
            binding.addNotesNotesauthor.requestFocus()
        }
        if (docURL?.isEmpty() == true) Toast.makeText(
            activity,
            "Please Upload Document",
            Toast.LENGTH_SHORT
        ).show() else return 1
        return 0
    }


    fun booksValidation(): Int {
        if (binding.addBooksBookname.text.toString().length < 5) {
            binding.addBooksBookname.error = "Enter Valid Book Name"
            binding.addBooksBookname.requestFocus()
        }
        if (binding.addBooksBookauthor.text.toString().length < 5) {
            binding.addBooksBookauthor.error = "Enter Valid Author Name"
            binding.addBooksBookauthor.requestFocus()
        }
        if (docURL?.isEmpty() == true) Toast.makeText(
            activity,
            "Please Upload Document",
            Toast.LENGTH_SHORT
        ).show() else return 1
        return 0
    }

    fun videosValidation(): Int {
        if (binding.addVideoVideoname.text.toString().length < 5) {
            binding.addVideoVideoname.error = "Enter Valid Video Name"
            binding.addVideoVideoname.requestFocus()
        }
        if (binding.addVideoVideoauthor.text.toString().length < 5) {
            binding.addVideoVideoauthor.error = "Enter Valid Author Name"
            binding.addVideoVideoauthor.requestFocus()
        }
        if (docURL?.isEmpty() == true) Toast.makeText(
            activity,
            "Please Upload Document",
            Toast.LENGTH_SHORT
        ).show() else return 1
        return 0
    }

    fun reportValidation(): Int {
        if (binding.reportDesc.text.toString().length < 5) {
            binding.reportDesc.error = "Enter Valid Book Name"
            binding.reportDesc.requestFocus()
        } else return 1
        return 0
    }


    fun feedbackValidation(): Int {
        if (binding.feedbackDesc.getText().toString().length < 5) {
            binding.feedbackDesc.setError("Enter Valid Description")
            binding.feedbackDesc.requestFocus()
        } else return 1
        return 0
    }

    fun reset() {
        binding.feebackMain.visibility = View.GONE
        FeedbackSubmitted?.visibility = View.VISIBLE
        FeedbackSubmitted?.playAnimation()
        binding.addNotesNotesname.text = null
        binding.addNotesNotesauthor.text = null
        binding.addBooksBookname.text = null
        binding.addBooksBookauthor.text = null
        binding.addVideoVideoname.text = null
        binding.addVideoVideoauthor.text = null
        binding.feedbackDesc.text = null
        binding.reportDesc.text = null
        docURL = null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            dialog = ProgressDialog(activity)
            dialog?.setMessage("Please Wait...")
            dialog?.show()
            imageuri = data?.data!!
            val timestamp = "" + System.currentTimeMillis()
            val storageReference: StorageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://edu-project-2423e.appspot.com/uploads")
            val filepath: StorageReference = storageReference.child("$timestamp.pdf")
            if (filepath.putFile(imageuri).isSuccessful) {
                filepath.downloadUrl
            }
//            filepath.putFile(imageuri).addOnCompleteListener{
//                if ()
//            }
//            val uploadTask = filepath.putFile(imageuri)
//            val urlTask = uploadTask.continueWithTask { task ->
//                if (!task.isSuccessful) {
//                    task.exception?.let {
//                        throw it
//                    }
//                }
//                ref.downloadUrl
//            }.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val downloadUri = task.result
//                } else {
//                    // Handle failures
//                    // ...
//                }
//            }

//            filepath.putFile(imageuri).continueWithTask(object : Continuation<Any?, Any?> {
//                @Throws(Exception::class)
//                fun then(task: Task<*>): Any? {
//                    if (!task.isSuccessful) {
//                        throw task.exception!!
//                    }
//                    return filepath.getDownloadUrl()
//                }
//            }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
//                if (task.isSuccessful) {
//                    dialog!!.dismiss()
//                    val uri = task.result
//                    docURL = uri.toString()
//                    Toast.makeText(activity, "Click On Submit", Toast.LENGTH_SHORT).show()
//                    val feedbackModel = FeedbackModel()
//                    feedbackModel.Document = docURL
//                } else {
//                    dialog!!.dismiss()
//                    Toast.makeText(activity, "Please Try Again", Toast.LENGTH_SHORT).show()
//                }
//            })
        }
    }

}