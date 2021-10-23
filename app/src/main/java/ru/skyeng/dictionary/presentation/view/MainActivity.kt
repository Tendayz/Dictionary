package ru.skyeng.dictionary.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.skyeng.dictionary.R
import ru.skyeng.dictionary.di.DaggerMainComponent
import ru.skyeng.dictionary.di.MainComponent
import ru.skyeng.dictionary.di.MainComponentProvider
import ru.skyeng.dictionary.presentation.navigation.DictionaryScreens
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainComponentProvider {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : AppNavigator(this, R.id.container) {
        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment,
        ) {
            fragmentTransaction.setCustomAnimations(
                R.anim.anim_enter_right_to_left,
                R.anim.anim_exit_right_to_left,
                R.anim.anim_enter_left_to_right,
                R.anim.anim_exit_left_to_right
            )
        }
    }

    private lateinit var component: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDependencies()
        navigator.applyCommands(arrayOf(Replace(DictionaryScreens.searchFragment())))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun provideMainComponent(): MainComponent = component

    private fun initDependencies() {
        component = DaggerMainComponent.factory().create(viewModelStore).apply {
            inject(this@MainActivity)
        }
    }
}
