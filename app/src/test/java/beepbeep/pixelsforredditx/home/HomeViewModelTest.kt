package beepbeep.pixelsforredditx.home

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.worker8.redditapi.model.t3_link.data.RedditLinkData
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData
import com.worker8.redditapi.model.t3_link.response.RedditLinkListingObject
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import io.mockk.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private lateinit var input: HomeContract.Input
    private lateinit var retrySubject: PublishSubject<Unit>
    private lateinit var postClickedSubject: PublishSubject<String>
    private lateinit var loadMoreSubject: PublishSubject<Unit>
    private lateinit var subredditSelectedSubject: PublishSubject<String>
    private lateinit var randomSubredditSelectedSubject: PublishSubject<String>
    private lateinit var aboutClickedSubject: PublishSubject<Unit>
    private lateinit var nightModeCheckChangedSubject: PublishSubject<Boolean>
    private lateinit var getMorePostsSubject: PublishSubject<Result<RedditLinkListingObject, FuelError>>
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeRepo: HomeRepo
    private lateinit var viewAction: HomeContract.ViewAction

    @Before
    fun setup() {
        retrySubject = PublishSubject.create()
        postClickedSubject = PublishSubject.create()
        loadMoreSubject = PublishSubject.create()
        getMorePostsSubject = PublishSubject.create()
        subredditSelectedSubject = PublishSubject.create()
        randomSubredditSelectedSubject = PublishSubject.create()
        aboutClickedSubject = PublishSubject.create()
        nightModeCheckChangedSubject = PublishSubject.create()

        input = mockk()
        homeRepo = mockk()
        viewAction = mockk(relaxed = true)

        every { input.retry } returns retrySubject
        every { input.postClicked } returns postClickedSubject
        every { input.loadMore } returns loadMoreSubject
        every { input.subredditSelected } returns subredditSelectedSubject
        every { input.randomSubredditSelected } returns randomSubredditSelectedSubject
        every { input.aboutClicked } returns aboutClickedSubject
        every { input.nightModeCheckChanged } returns nightModeCheckChangedSubject

        every { homeRepo.getBackgroundThread() } returns Schedulers.trampoline()
        every { homeRepo.saveSubredditSharedPreference(any()) } just runs
        every { homeRepo.selectSubreddit(any()) } just runs
        every { homeRepo.getSubredditSharedPreference() } returns ""
        every { homeRepo.getMainThread() } returns Schedulers.trampoline()
        every { homeRepo.getMorePosts() } returns getMorePostsSubject

        viewModel = HomeViewModel()
        viewModel.input = input
        viewModel.repo = homeRepo
        viewModel.viewAction = viewAction

    }

    @Test
    fun testShowNoNetworkError() {
        // setup
        every { input.isConnectedToInternet() } returns false

        // act
        viewModel.onCreate()

        // assert
        verify(exactly = 1) { viewAction.showNoNetworkErrorSnackbar() }
    }

    @Test
    fun testGetPostSuccessfully() {
        // setup
        every { input.isConnectedToInternet() } returns true
        val screenStateTestObserver = viewModel.screenState.test()

        // act
        viewModel.onCreate()

        // assert: showing loading progress bar in the middle
        verify(exactly = 0) { viewAction.showNoNetworkErrorSnackbar() }
        verify(exactly = 1) { viewAction.showLoadingProgressBar(true) }
        verify(exactly = 0) { viewAction.showBottomLoadingProgresBar(any()) }

        // act
        val redditLinks: List<RedditLinkObject> = listOf(RedditLinkObject(value = RedditLinkData(url = "asdf")))
        val fakeResult = makeFakeResult(redditLinks)

        getMorePostsSubject.onNext(fakeResult)

        // assert: hide all loading progress bar correctly
        verify(exactly = 0) { viewAction.showNoNetworkErrorSnackbar() }
        verify(exactly = 1) { viewAction.showLoadingProgressBar(false) }
        verify(exactly = 1) { viewAction.showBottomLoadingProgresBar(false) }

        // assert: data loaded correctly
        screenStateTestObserver.assertNoErrors()
        screenStateTestObserver.assertValueAt(1) {
            it.redditLinks[0].value.url == "asdf"
        }
    }

    @Test
    fun testError() {
        // setup
        every { input.isConnectedToInternet() } returns true
        val screenStateTestObserver = viewModel.screenState.test()

        // act
        viewModel.onCreate()

        val redditLinks: List<RedditLinkObject> = listOf(RedditLinkObject(value = RedditLinkData(url = "asdf")))
        val fakeResult = makeFakeResult(redditLinks)

        val throwable = mockk<Throwable>()
        getMorePostsSubject.onError(throwable)

        verify(exactly = 1) { viewAction.showLoadingProgressBar(false) }
        verify(exactly = 1) { viewAction.showBottomLoadingProgresBar(false) }
        verify(exactly = 1) { viewAction.showGenericErrorMessage() }
    }

    @Test
    fun testChangeSubreddit() {
        // setup
        every { input.isConnectedToInternet() } returns true
        val screenStateTestObserver = viewModel.screenState.test()

        // act
        viewModel.onCreate()
        val redditLinks: List<RedditLinkObject> = listOf(RedditLinkObject(value = RedditLinkData(url = "asdf")))
        val fakeResult = makeFakeResult(redditLinks)
        getMorePostsSubject.onNext(fakeResult)

        // assert:
        subredditSelectedSubject.onNext("aww")

        verify(exactly = 1) { homeRepo.saveSubredditSharedPreference("aww") }
        verify(exactly = 1) { homeRepo.selectSubreddit("aww") }
        verify(exactly = 1) { viewAction.updateToolbarSubredditText("aww") }

        screenStateTestObserver.assertNoErrors()

        screenStateTestObserver.assertValueAt(2) {
            it.redditLinks.count() == 0
        }
    }

    private fun makeFakeResult(redditLinks: List<RedditLinkObject>): Result<RedditLinkListingObject, FuelError> {
        val fakeResult = mockk<Result<RedditLinkListingObject, FuelError>>()
        val fakeListing = mockk<RedditLinkListingObject>()
        val fakeListingData = mockk<RedditLinkListingData>()
        every { fakeListingData.getRedditImageLinks() } returns redditLinks
        every { fakeListing.value } returns fakeListingData
        every { fakeResult.component1() } returns fakeListing
        every { fakeResult.component2() } returns mockk()
        return fakeResult
    }
}
