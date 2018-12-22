package beepbeep.pixelsforreddit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import beepbeep.pixelsforreddit.extension.addTo
import beepbeep.pixelsforreddit.home.*
import com.worker8.redditapi.RedditApi2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val adapter = HomeAdapter()
    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, HomeViewModelFactory(HomeRepo2())).get(HomeViewModel::class.java) //getViewModel<HomeViewModel>().also { lifecycle.addObserver(it) }
    }
    private val disposableBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        lifecycle.addObserver(viewModel)
        setupView()
//                RedditApi2().getPosts()
//                        .subscribeOn(Schedulers.io())
//                        .subscribe { (response, fuelError) ->
//                            response?.let { _listing ->
//                                _listing.value.children.forEach { _child ->
//                                    Log.d("ccw", "${_child.data.title}")
//                                }
//
//                                //System.out.println(it)
//                            }
//                            fuelError?.printStackTrace()
//                        }
        viewModel.apply {
            pagedList
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        adapter.submitList(it.value.valueList)
                    }.addTo(disposableBag)
//            networkState
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        if (it.status == Status.LOADING) {
//                            adapter.showFooter()
//                        } else {
//                            adapter.hideFooter()
//                        }
//                    }.addTo(disposableBag)
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