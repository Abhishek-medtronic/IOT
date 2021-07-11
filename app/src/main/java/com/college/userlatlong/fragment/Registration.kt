package com.college.userlatlong.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.college.userlatlong.HomeActivity
import com.college.userlatlong.R
import com.college.userlatlong.model.RegistrationEntity
import com.college.userlatlong.model.User
import com.college.userlatlong.network.RetroInterface
import com.college.userlatlong.utils.AES256
import com.college.userlatlong.utils.SharedPreference
import com.college.userlatlong.utils.showSnackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Registration.newInstance] factory method to
 * create an instance of this fragment.
 */
class Registration : Fragment() {

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var country: EditText
    private lateinit var state: EditText
    private lateinit var city: EditText
    private lateinit var mobileNumber: EditText
    private lateinit var register: Button
    lateinit var sharedPreference: SharedPreference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreference(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_register, container, false)
        init(view)
        return view;
    }

    private fun init(view: View) {
        firstName = view.findViewById(R.id.et_name)
        lastName = view.findViewById(R.id.last_name)
        country = view.findViewById(R.id.et_country)
        state = view.findViewById(R.id.et_state)
        city = view.findViewById(R.id.et_city)
        mobileNumber = view.findViewById(R.id.et_password)
        register = view.findViewById(R.id.btn_register)
        registerClickEvent();
    }

    private fun registerClickEvent() {
        register.setOnClickListener {
            if (firstName.text.isEmpty()) {
                view?.showSnackbar("PLease enter first Name") {}
                return@setOnClickListener
            }
            if (lastName.text.isEmpty()) {
                view?.showSnackbar("PLease enter Last Name") {}
                return@setOnClickListener
            }
            if (country.text.isEmpty()) {
                view?.showSnackbar("PLease enter Country Name") {}
                return@setOnClickListener
            }
            if (state.text.isEmpty()) {
                view?.showSnackbar("PLease enter State Name") {}
                return@setOnClickListener
            }
            if (city.text.isEmpty()) {
                view?.showSnackbar("PLease enter City Name") {}
                return@setOnClickListener
            }
            if (mobileNumber.text.isEmpty()) {
                view?.showSnackbar("PLease enter Password") {}
                return@setOnClickListener
            }
            var user = User(
                firstName.text.toString(),
                lastName.text.toString(),
                country.text.toString(),
                state.text.toString(),
                city.text.toString(),
                mobileNumber.text.toString(),
                Build.MODEL.toString(),
                Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
            )
            var repo = RetroInterface.getRetrofitInstance()
            repo?.registerUser(user)?.enqueue(object : Callback<RegistrationEntity> {
                override fun onFailure(call: Call<RegistrationEntity>, t: Throwable) {
                    Log.e("Success", "Success")
                }

                override fun onResponse(call: Call<RegistrationEntity>, response: Response<RegistrationEntity>) {
                    if(response.body()?.success!!) {
                        sharedPreference.save("UserId", response.body()!!.user_id)
                        sharedPreference.save("SecKey", response.body()!!.secret_key)
                        AES256.SecretKey =response.body()!!.secret_key
                        var callback = Intent(activity, HomeActivity::class.java)
                        activity?.startActivity(callback)
                    }
                }

            });

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Registration.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Registration().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}