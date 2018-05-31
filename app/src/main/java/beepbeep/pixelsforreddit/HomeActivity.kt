package beepbeep.pixelsforreddit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import beepbeep.pixelsforreddit.common.Status
import beepbeep.pixelsforreddit.extension.addTo
import beepbeep.pixelsforreddit.extension.visibility
import beepbeep.pixelsforreddit.home.HomeAdapter
import beepbeep.pixelsforreddit.home.HomeRepo
import beepbeep.pixelsforreddit.home.HomeViewModel
import beepbeep.pixelsforreddit.home.HomeViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private val adapter = HomeAdapter()
    private lateinit var viewModel: HomeViewModel
    private val disposableBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, HomeViewModelFactory(HomeRepo())).get(HomeViewModel::class.java) //getViewModel<HomeViewModel>().also { lifecycle.addObserver(it) }
        lifecycle.addObserver(viewModel)
        setupView()

        viewModel.apply {
            pagedList
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        adapter.submitList(it)
                    }.addTo(disposableBag)
            networkState
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                homeProgressBar.visibility = (it.status == Status.LOADING).visibility()
                homeList.visibility = (it.status == Status.SUCCESS).visibility()
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