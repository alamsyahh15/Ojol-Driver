package com.udacoding.ojodriverlfirebasekotlin.request


import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.udacoding.ojodriverlfirebasekotlin.R
import com.udacoding.ojodriverlfirebasekotlin.profile.ProfileFragment
import com.udacoding.ojodriverlfirebasekotlin.request.fragment.CompleteBooking
import com.udacoding.ojodriverlfirebasekotlin.request.fragment.ProsesBooking
import com.udacoding.ojodriverlfirebasekotlin.request.fragment.RequestBooking
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_request.*

class RequestFragment : Fragment() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.request -> {

                setFragment(RequestBooking())
                return@OnNavigationItemSelectedListener true
            }
            R.id.handle -> {

                setFragment(ProsesBooking())
                return@OnNavigationItemSelectedListener true
            }
            R.id.complete -> {

                setFragment(CompleteBooking())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragment(RequestBooking())

        navigation2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    fun setFragment(fragment: Fragment) {

        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.pager, fragment)?.commit()
    }


}

