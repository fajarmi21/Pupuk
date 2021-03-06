package com.polinema.android.kotlin.pupuk.ui.kp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.e
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.ui.kp.fragment.KpDashboardFragment
import com.polinema.android.kotlin.pupuk.ui.kp.fragment.KpPetaniFragment
import com.polinema.android.kotlin.pupuk.ui.kp.fragment.KpRekapFragment
import com.polinema.android.kotlin.pupuk.ui.kp.fragment.KpUsulFragment
import com.polinema.android.kotlin.pupuk.ui.login.LoginActivity
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.activity_kp.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor


class KpActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kp)
        initToolbar()
        initMenuFragment()
        addFragment(KpDashboardFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.kp_side_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.context_menu -> {
                showContextMenuDialogFragment()
            }
        }
        return true
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.ToolbarKp))
        tvToolbarTitle.text = "Kelompok Tani"
    }

    private fun initMenuFragment() {
        val menu = MenuParams().apply {
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt()
            menuObjects = menu
            isClosableOutside = false
            animationDuration = 500
        }

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menu).apply {
            setItemClickListener { _, position ->
                when(position) {
                    0 -> { if (fragmentManager!!.findFragmentById(R.id.FrameKP) !is KpDashboardFragment) addFragment(KpDashboardFragment())}
                    1 -> { if (fragmentManager!!.findFragmentById(R.id.FrameKP) !is KpUsulFragment) addFragment(KpUsulFragment())}
                    2 -> { if (fragmentManager!!.findFragmentById(R.id.FrameKP) !is KpRekapFragment) addFragment(KpRekapFragment())}
                    3 -> { if (fragmentManager!!.findFragmentById(R.id.FrameKP) !is KpPetaniFragment) addFragment(KpPetaniFragment())}
                    4 -> {
                        Toast.makeText(context, "Logout Sukses", Toast.LENGTH_LONG).show()
                        startActivity(intentFor<LoginActivity>().clearTask().clearTop())
                        SaveSharedPreference.setLoggedIn(applicationContext, false, null, 0)
                        finish()
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private val menu = mutableListOf<MenuObject>().apply {
        val close = MenuObject().apply {
            resource = R.drawable.icn_close
            bgColor = R.color.text_color
        }
        val usul = MenuObject("Usulan").apply {
            resource = R.drawable.icn_approval
            bgColor = R.color.text_color
        }
        val rekap = MenuObject("Rekap").apply {
            resource = R.drawable.icn_task
            bgColor = R.color.text_color
        }
        val user = MenuObject("Petani").apply {
            resource = R.drawable.icn_contact
            bgColor = R.color.text_color
        }
        val signout = MenuObject("Sign Out").apply {
            resource = R.drawable.icn_signout
            bgColor = R.color.text_color
        }

        add(close)
        add(usul)
        add(rekap)
        add(user)
        add(signout)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            e("sa", supportFragmentManager.backStackEntryCount.toString())
            return if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
//                super.onKeyDown(keyCode, event)
                true
            } else {
                false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.FrameKP, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}