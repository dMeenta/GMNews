package dev.didnt.proyecto.adaptadores

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.didnt.proyecto.ui.CategoryFragment
import dev.didnt.proyecto.ui.ExitFragment
import dev.didnt.proyecto.ui.FriendsFragment
import dev.didnt.proyecto.ui.HomeFragment
import dev.didnt.proyecto.ui.ProfileFragment

class ViewPagerAdapter (
    fragmentActivity: FragmentActivity,
    val extras:Bundle?
):FragmentStateAdapter(fragmentActivity) {
    companion object{
        private const val ARG_OBJECT = "object"
    }

    override fun getItemCount(): Int {
        return 5
    }
    override fun createFragment(position: Int): Fragment {
        return when (position){
            0->{HomeFragment()}
            1->{CategoryFragment()}
            2->{val friends = FriendsFragment()
                friends.arguments=extras
                return friends}
            3->{val profile = ProfileFragment()
                profile.arguments=extras
                return profile}
            4->{val exit = ExitFragment()
                exit.arguments=extras
                return exit}
            else -> {HomeFragment()}
        }

    }

}