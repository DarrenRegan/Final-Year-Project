package com.example.ecommerce.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.Prevalent.Prevalent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.text.TextUtils
import android.widget.Toast
import com.example.ecommerce.HomeActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


@Suppress("DEPRECATION")
class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var profileImageView:CircleImageView
    private lateinit var fullNameEditText:EditText
    private lateinit var userPhoneEditText:EditText
    private lateinit var addressEditText:EditText
    private lateinit var profileChangeTxtBtn:TextView
    private lateinit var closeTextBtn:TextView
    private lateinit var saveTextBtn:TextView

    private lateinit var imageUri:Uri
    private var myUrl:String = ""
    private lateinit var storageProfilePicRef:StorageReference
    private var check:String = ""
    //private lateinit var uploadTask:StorageTask
    private lateinit var uploadTask:UploadTask
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(com.example.ecommerce.R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(com.example.ecommerce.R.id.text_share)
        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Profile pictures")
        profileImageView = root.findViewById(com.example.ecommerce.R.id.settings_profile_img)
        fullNameEditText = root.findViewById(com.example.ecommerce.R.id.settings_name)
        userPhoneEditText = root.findViewById(com.example.ecommerce.R.id.settings_phone_number)
        addressEditText = root.findViewById(com.example.ecommerce.R.id.settings_address)
        profileChangeTxtBtn = root.findViewById(com.example.ecommerce.R.id.profile_img_change_btn)
        saveTextBtn = root.findViewById(com.example.ecommerce.R.id.update_acc_settings)
        closeTextBtn = root.findViewById(com.example.ecommerce.R.id.close_settings)
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        displayUserInfo(profileImageView, fullNameEditText, userPhoneEditText, addressEditText)

        closeTextBtn.setOnClickListener{ it: View? ->
            activity?.onBackPressed();
        }

        saveTextBtn.setOnClickListener{ it: View? ->
            if (check == "clicked"){
                saveUserInfo()
            }
            else{
                updateUserInfo()
            }

        }

        profileChangeTxtBtn.setOnClickListener{ it: View? ->
            check = "clicked"

            // for fragment (DO NOT use `getActivity()`)
            this.context?.let { it1 ->
                CropImage.activity()
                    .setAspectRatio(1, 1)
                    .start(it1, this)
            }
        }

        return root
    }//VIEW

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK){
                imageUri = result.uri
                profileImageView.setImageURI(imageUri)
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                var error = result.error
                Toast.makeText(this.context, "Error, please try again ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserInfo() {
        var ref = FirebaseDatabase.getInstance().reference.child("Users")
        var userMap:HashMap<String, Any> = HashMap()
        userMap["name"] = fullNameEditText.text.toString()
        userMap["address"] = addressEditText.text.toString()
        userMap["phoneOrder"] = userPhoneEditText.text.toString()
        //userMap["phone"] = userPhoneEditText.text.toString()
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap)

        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        Toast.makeText(this.context, "Profile Updated! ", Toast.LENGTH_SHORT).show()
    }

    private fun saveUserInfo() {
        when {
            TextUtils.isEmpty(fullNameEditText.text.toString()) -> Toast.makeText(this.context, "Must have a name ", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(addressEditText.text.toString()) -> Toast.makeText(this.context, "Must have a address ", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(userPhoneEditText.text.toString()) -> Toast.makeText(this.context, "Must have a phone number ", Toast.LENGTH_SHORT).show()
            check == "clicked" -> uploadImage()
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this.context)
        progressDialog.setTitle("Update Profile")
        progressDialog.setMessage("Please wait, while we update your account info")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        if(imageUri != null) run {
            val fileRef =
                storageProfilePicRef.child(Prevalent.currentOnlineUser.getPhone() + ".jpg")
            uploadTask = fileRef.putFile(imageUri)

            val ut = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                myUrl = fileRef.downloadUrl.toString()
                return@continueWithTask fileRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    myUrl = downloadUrl.toString()

                    var ref = FirebaseDatabase.getInstance().reference.child("Users")
                    var userMap:HashMap<String, Any> = HashMap()
                    userMap["name"] = fullNameEditText.text.toString()
                    userMap["address"] = addressEditText.text.toString()
                    userMap["phoneOrder"] = userPhoneEditText.text.toString()
                    //userMap["phone"] = userPhoneEditText.text.toString()
                    userMap["image"] = myUrl
                    ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap)

                    progressDialog.dismiss()

                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this.context, "Profile Updated! ", Toast.LENGTH_SHORT).show()
                }
                else{
                    progressDialog.dismiss()
                    val ex = task.exception.toString()
                    Toast.makeText(this.context, "Error: $ex", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {task ->
                val ex = task.cause.toString()
                Toast.makeText(this.context, "Error: $ex", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }else{
            Toast.makeText(this.context, "Image not selected! ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayUserInfo(profileImageView: CircleImageView?, fullNameEditText: EditText?, userPhoneEditText: EditText?, addressEditText: EditText?) {
        val usersRef = FirebaseDatabase.getInstance().reference.child("Users").child(Prevalent.currentOnlineUser.getPhone())

        usersRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("image").exists()){
                        val image:String = dataSnapshot.child("image").value.toString()
                        val name:String = dataSnapshot.child("name").value.toString()
                        val phone:String = dataSnapshot.child("phone").value.toString()
                        val address:String = dataSnapshot.child("address").value.toString()

                        Picasso.get().load(image).into(profileImageView)
                        fullNameEditText?.setText(name)
                        userPhoneEditText?.setText(phone)
                        addressEditText?.setText(address)
                    }

                }
            }
        })//EventListener
    }//displayUserInfo

}