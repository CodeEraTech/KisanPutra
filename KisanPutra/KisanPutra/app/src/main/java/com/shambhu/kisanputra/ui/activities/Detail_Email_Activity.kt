package com.shambhu.kisanputra.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.base.BaseActivity
import com.shambhu.kisanputra.utils.StringUtils
import com.shambhu.social.UserModel
import kotlinx.android.synthetic.main.activity_detail__email_.*
import kotlinx.android.synthetic.main.activity_detail__email_.view.*
import kotlinx.android.synthetic.main.activity_detail__name_.*

class Detail_Email_Activity : BaseActivity() , View.OnClickListener{
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.signup_btn->{
                if (!TextUtils.isEmpty(email.text)){
                    userModel.email=email.text.toString()
                    var intent: Intent=Intent(this,Detail_Address_Activity::class.java)
                    intent.putExtra("userModel",userModel)
                    finish()
                    startActivity(intent)

                }else{
                    showToast("Please enter name")
                }
            }
        }
    }

    lateinit var userModel : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__email_)

        intent=this.intent
        if (intent!=null)
            userModel=intent.getParcelableExtra("userModel")


        signup_btn.setOnClickListener(this)
    }

    fun validate() : Boolean{
        if(TextUtils.isEmpty(email.text)){
            email.requestFocus()
            email.setError(getString(R.string.enter_Valid_email))
            return false
        }

        if (!StringUtils.emailValidator(email.text.trim().toString())){
            email.requestFocus()
            email.setError(getString(R.string.enter_Valid_email))
            return false
        }

        return true
    }
}
