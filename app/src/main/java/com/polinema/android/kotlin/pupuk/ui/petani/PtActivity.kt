package com.polinema.android.kotlin.pupuk.ui.petani

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.ui.login.LoginActivity
import com.polinema.android.kotlin.pupuk.ui.petani.fragment.PtAddUsulanFragment
import com.polinema.android.kotlin.pupuk.ui.petani.fragment.PtDashboardFragment
import com.polinema.android.kotlin.pupuk.util.MyIntentService
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.activity_pt.*
import org.jetbrains.anko.*

class PtActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pt)
        initToolbar()
        initMenuFragment()

        addFragment(PtDashboardFragment())

        val intent = Intent(this, MyIntentService::class.java)
        startService(intent)
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
//            R.id.action_logout -> {
//                startActivity(intentFor<LoginActivity>().clearTask().clearTop())
//                finish()
//                return true
//            }
//            R.id.action_addProduct -> {
////                startActivity(intentFor<AddProductActivity>().clearTask().clearTop())
//                return true
//            }
        }
        return true
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.ToolbarPt))
        tvToolbarTitlePt.text = "Petani"
    }

    private fun initMenuFragment() {
        val menu = MenuParams().apply {
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt()
            menuObjects = menu
            isClosableOutside = false
            animationDuration = 500
            animationDelay = 100
        }

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menu).apply {
            setItemClickListener { _, position ->
                when(position) {
                    0 -> { if (fragmentManager!!.findFragmentById(R.id.FramePT) !is PtDashboardFragment) addFragment(PtDashboardFragment())}
                    1 -> { if (fragmentManager!!.findFragmentById(R.id.FramePT) !is PtAddUsulanFragment) addFragment(PtAddUsulanFragment())}
//                    2 -> { if (fragmentManager!!.findFragmentById(R.id.FramePT) !is KpPetaniFragment) addFragment(KpPetaniFragment())}
                    3 -> {
                        Toast.makeText(context, "Logout Sukses", Toast.LENGTH_LONG).show()
                        startActivity(intentFor<LoginActivity>().clearTask().clearTop())
                        SaveSharedPreference.setLoggedIn(applicationContext, false, null, 0)
                        finish()
                    }
                }
            }
            setItemLongClickListener { _, position ->
                Toast.makeText(
                        this@PtActivity,
                        "Long clicked on position: $position",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("ResourceType")
    private val menu = mutableListOf<MenuObject>().apply {
        val close = MenuObject().apply {
            resource = R.drawable.icn_close
            bgColor = R.color.text_color
        }
        val list = MenuObject("Rekap").apply {
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
        add(list)
        add(user)
        add(signout)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.FramePT, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
}