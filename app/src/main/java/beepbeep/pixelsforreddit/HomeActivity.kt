package beepbeep.pixelsforreddit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import beepbeep.pixelsforreddit.extension.addTo
import beepbeep.pixelsforreddit.extension.isConnectedToInternet
import beepbeep.pixelsforreddit.home.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val adapter = HomeAdapter()
    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, HomeViewModelFactory(input, HomeRepo(), viewAction)).get(HomeViewModel::class.java) //getViewModel<HomeViewModel>().also { lifecycle.addObserver(it) }
    }
    private val disposableBag = CompositeDisposable()
    private val input = object : HomeContract.Input {
        override fun isConnectedToInternet() = this@HomeActivity.isConnectedToInternet()

    }
    private val viewAction = object : HomeContract.ViewAction {
        override fun showNoNetworkError() {
            Snackbar.make(this@HomeActivity.window.decorView, R.string.no_network, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        lifecycle.addObserver(viewModel)
        setupView()
        viewModel.apply {
            pagedList
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.submitList(it.value.getRedditImageLinks())
                }.addTo(disposableBag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableBag.dispose()
    }

    private fun setupView() {
        adapter.apply {
            homeList.adapter = this
            homeList.addItemDecoration(DividerItemDecoration(homeList.context, DividerItemDecoration.VERTICAL).apply { setDrawable(resources.getDrawable(R.drawable.recycler_view_divider)) })
        }
    }
}
