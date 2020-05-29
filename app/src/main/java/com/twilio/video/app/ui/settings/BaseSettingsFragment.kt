package com.twilio.video.app.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.DialogFragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.twilio.video.app.data.get
import com.twilio.video.app.data.NumberPreference
import com.twilio.video.app.data.NumberPreferenceDialogFragmentCompat
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseSettingsFragment : PreferenceFragmentCompat() {

    @Inject
    internal lateinit var sharedPreferences: SharedPreferences

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference == null) {
            return
        }

        // show custom dialog preference
        if (preference is NumberPreference) {
            val dialogFragment: DialogFragment?
            dialogFragment = NumberPreferenceDialogFragmentCompat.newInstance(preference.key)

            if (dialogFragment != null) {
                dialogFragment.setTargetFragment(this, 0)
                dialogFragment.show(requireFragmentManager(), tag)
            }
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }

    protected fun setListPreferenceValue(arrayId: Int, key: String, defaultValue: String) {
        val defaultValueIndex = resources
            .getStringArray(arrayId).indexOf(sharedPreferences.get(key, defaultValue))
        (findPreference(key) as ListPreference).setValueIndex(defaultValueIndex)
    }
}