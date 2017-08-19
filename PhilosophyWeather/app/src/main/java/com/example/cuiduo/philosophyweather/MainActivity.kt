package com.example.cuiduo.philosophyweather

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cuiduo.philosophyweather.Fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = this.javaClass.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
//        toolbar.setNavigationOnClickListener { onBackPressed() }

//        initToolbar()

        val demoFragment = Fragment.instantiate(this, SampleListFragment::class.java.name)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, demoFragment)
        fragmentTransaction.commit()

        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            val actionbar = supportActionBar
            if (actionbar != null) {
                actionbar.setDisplayHomeAsUpEnabled(count > 0)
                actionbar.setDisplayShowHomeEnabled(count > 0)
            }
        }
    }

//    private fun initToolbar() {
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)
//        toolbar.setNavigationOnClickListener { onBackPressed() }
//    }

    class SampleListFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            return RecyclerView(context)
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            val adapter = SampleListAdapter()

            val recyclerView = view as RecyclerView?
            recyclerView!!.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter

            adapter.add(SampleInfo("Default", DefaultFragment::class.java.name))
            adapter.add(SampleInfo("ChangeColor",ChangeColorFragment::class.java.name))
            adapter.add(SampleInfo("CustomAnimation",CustomAnimationFragment().javaClass.name))
            adapter.add(SampleInfo("DynamicAdapter",DynamicAdapterFragment().javaClass.name))
            adapter.add(SampleInfo("LoopViewPager",LoopViewPagerFragment().javaClass.name))
            adapter.add(SampleInfo("ResetAdapter",ResetAdapterFragment().javaClass.name))
            adapter.add(SampleInfo("SnackbarBehavior", SnackbarBehaviorFragment::class.java!!.getName()))
//            adapter.add(SampleInfo("Custom Animation", CustomAnimationFragment::class.java!!.getName()))
//            adapter.add(SampleInfo("Change Color", ChangeColorFragment::class.java!!.getName()))
//            adapter.add(SampleInfo("Dynamic Adapter", DynamicAdapterFragment::class.java!!.getName()))
//            adapter.add(SampleInfo("Reset Adapter", ResetAdapterFragment::class.java!!.getName()))
//            adapter.add(SampleInfo("LoopViewPager", LoopViewPagerFragment::class.java!!.getName()))
//            adapter.add(SampleInfo("Snackbar Behavior", SnackbarBehaviorFragment::class.java!!.getName()))
        }

        private inner class SampleListAdapter : RecyclerView.Adapter<ItemViewHolder>() {

            private val mList = ArrayList<SampleInfo>()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
                return ItemViewHolder.create(parent)
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
                val sample = mList[position]
                holder.bindView(sample.title)
                holder.itemView.setOnClickListener { navigateToFragment(mList[holder.adapterPosition].fragmentName) }
            }

            override fun getItemCount() = mList.size

            fun add(`object`: SampleInfo): Boolean {
                val lastIndex = mList.size
                if (mList.add(`object`)) {
                    notifyItemInserted(lastIndex)
                    return true
                } else {
                    return false
                }
            }
        }

        private fun navigateToFragment(fragmentName: String) {
            Log.e(TAG,"context=="+context)
            val fragment = Fragment.instantiate(context, fragmentName)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(fragmentName)
            fragmentTransaction.commit()
        }

        private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindView(title: String) {
                (itemView as TextView).text = title
            }

            companion object {

                fun create(viewGroup: ViewGroup): ItemViewHolder {
                    return ItemViewHolder(LayoutInflater.from(viewGroup.context)
                            .inflate(R.layout.item_view, viewGroup, false))
                }
            }
        }

        private class SampleInfo(var title: String, var fragmentName: String)
    }
}







