package beepbeep.pixelsforreddit.home

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    lateinit var input: HomeContract.Input
    lateinit var retrySubject: PublishSubject<Unit>
    lateinit var loadMoreSubject: PublishSubject<Unit>
    lateinit var viewModel: HomeViewModel
    lateinit var homeRepo: HomeRepo
    lateinit var viewAction: HomeContract.ViewAction

    @Before
    fun setup() {
        retrySubject = PublishSubject.create()
        loadMoreSubject = PublishSubject.create()

        input = mockk()
        homeRepo = mockk()
        viewAction = mockk()

        every { input.retry } returns retrySubject
        every { input.loadMore } returns loadMoreSubject

        every { homeRepo.getBackgroundThread() } returns Schedulers.trampoline()
        every { homeRepo.getMainThread() } returns Schedulers.trampoline()

        viewModel = HomeViewModel(input = input, repo = homeRepo, viewAction = viewAction)
    }

    @Test
    fun showNoNetworkError() {
        every { input.isConnectedToInternet() } returns false
        viewModel.onCreate()
        loadMoreSubject.onNext(Unit)

        verify(exactly = 1) { viewAction.showNoNetworkErrorSnackbar() }
    }
}
