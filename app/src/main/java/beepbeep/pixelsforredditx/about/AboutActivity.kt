package beepbeep.pixelsforredditx.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.preference.ThemePreference
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupTheme() {
        if (ThemePreference.getThemePreference(this)) {
            setTheme(R.style.AppThemeDark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}
