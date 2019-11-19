package com.shambhu.kisanputra.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.base.BaseActivity
import com.shambhu.social.UserModel
import kotlinx.android.synthetic.main.activity_detail__name_.*

class Detail_Name_Activity : BaseActivity() , View.OnClickListener {

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.next->{
                if (!TextUtils.isEmpty(name.text)){
                    userModel.name=name.text.toString()
                    var intent: Intent=Intent(this,Detail_Email_Activity::class.java)
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
        setContentView(R.layout.activity_detail__name_)

        intent=this.intent
        if (intent!=null)
            userModel=intent.getParcelableExtra("userModel")


        next.setOnClickListener(this)
    }
}
