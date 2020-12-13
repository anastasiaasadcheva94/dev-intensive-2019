package ru.skillbranch.devintensive.ui.profile

import android.annotation.SuppressLint
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.databinding.ActivityProfileBinding
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel


class ProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel
    lateinit var binding: ActivityProfileBinding
    var isEditMode = false
    lateinit var viewFields:Map<String, TextView>
    companion object{
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initView(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileActivity","update theme")
        delegate.localNightMode = mode
    }

    private fun updateUI(profile: Profile?) {
        if (profile != null) {
            profile.toMap().also {
                for ((k,v) in viewFields) {
                    v.text = it[k].toString()
                }
            }
        }
    }

    private fun initView(savedInstanceState: Bundle?) {
        viewFields = mapOf(
                "nickName" to binding.tvNickName,
                "rank" to binding.tvRank,
                "firstName" to binding.etFirstName,
                "lastName" to binding.etLastName,
                "about" to binding.etAbout,
                "repository" to binding.etRepository,
                "rating" to binding.tvRating,
                "respect" to binding.tvRespect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)

        binding.btnEdit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        binding.btnSwitchTheme.setOnClickListener {
            viewModel.switchTheme()
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("firstName", "lastName", "about", "repository").contains(it.key) }
        for ((_,v) in info){
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        binding.icEye.visibility = if (isEdit) View.GONE else View.VISIBLE
        binding.wrAbout.isCounterEnabled = isEdit


        with(binding.btnEdit){
            val filter:ColorFilter? = if (isEdit){
                PorterDuffColorFilter(
                        resources.getColor(R.color.color_accent, theme),
                        PorterDuff.Mode.SRC_IN
                )
            }else {
                null
            }

            val icon = if (isEdit){
                resources.getDrawable(R.drawable.ic_baseline_save_24, theme)
            } else {
                resources.getDrawable(R.drawable.ic_baseline_edit_24, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }


    }

    private fun saveProfileInfo(){
        Profile(
                firstName = binding.etFirstName.text.toString(),
                lastName = binding.etLastName.text.toString(),
                about = binding.etAbout.text.toString(),
                repository = binding.etRepository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }
}

