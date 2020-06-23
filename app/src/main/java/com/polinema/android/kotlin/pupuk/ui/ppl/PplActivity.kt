package com.polinema.android.kotlin.pupuk.ui.ppl

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.ui.login.LoginActivity
import com.polinema.android.kotlin.pupuk.ui.ppl.fragment.PplDashboardFragment
import com.polinema.android.kotlin.pupuk.ui.ppl.fragment.PplKpFragment
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.activity_ppl.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor

class PplActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ppl)
        initToolbar()
        initMenuFragment()
        addFragment(PplDashboardFragment())
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
        setSupportActionBar(findViewById(R.id.ToolbarPpl))
        tvToolbarTitlePpl.text = "PPL"
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
//                Toast.makeText(
//                    this@KpActivity,
//                    "Clicked on position: $position",
//                    Toast.LENGTH_SHORT
//                ).show()
                when(position) {
                    0 -> { if (fragmentManager!!.findFragmentById(R.id.FramePPL) !is PplDashboardFragment) addFragment(
                        PplDashboardFragment()
                    )}
//                    1 -> { if (fragmentManager!!.findFragmentById(R.id.FramePPL) !is PplKpFragment) addFragment(
//                        PplKpFragment()
//                    )}
                    2 -> { if (fragmentManager!!.findFragmentById(R.id.FramePPL) !is PplKpFragment) addFragment(
                        PplKpFragment()
                    )}
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
                    this@PplActivity,
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
        val user = MenuObject("Kelompok Tani").apply {
            resource = R.drawable.icn_contact
            bgColor = R.color.text_color
        }
        val signout = MenuObject("Sign Out").apply {
            resource = R.drawable.icn_signout
            bgColor = R.color.text_color
        }
//        val like = MenuObject("Like profile").apply {
//            bitmap = BitmapFactory.decodeResource(resources, R.drawable.icn_2)
//            bgColor = R.color.text_color
//        }
//        val addFriend = MenuObject("Add to friends").apply {
//            drawable = BitmapDrawable(
//                resources,
//                BitmapFactory.decodeResource(resources, R.drawable.icn_3)
//            )
//            bgColor = R.color.text_color
//        }
//        val addFavorite = MenuObject("Add to favorites").apply {
//            resource = R.drawable.icn_4
//            bgColor = R.color.text_color
//        }
//        val block = MenuObject("Block user").apply {
//            resource = R.drawable.icn_5
//            bgColor = R.color.text_color
//        }

        add(close)
        add(list)
        add(user)
        add(signout)
//        add(like)
//        add(addFriend)
//        add(addFavorite)
//        add(block)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("sa", supportFragmentManager.backStackEntryCount.toString())
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
            .replace(R.id.FramePPL, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}