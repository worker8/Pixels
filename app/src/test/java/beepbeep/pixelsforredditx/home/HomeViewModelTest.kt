package beepbeep.pixelsforredditx.home

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.worker8.redditapi.Listing
import com.worker8.redditapi.ListingData
import com.worker8.redditapi.RedditLink
import com.worker8.redditapi.RedditLinkData
import io.mockk.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    lateinit var input: HomeContract.Input
    lateinit var retrySubject: PublishSubject<Unit>
    lateinit var loadMoreSubject: PublishSubject<Unit>
    lateinit var subredditSelectedSubject: PublishSubject<String>
    lateinit var randomSubredditSelectedSubject: PublishSubject<String>
    lateinit var getMorePostsSubject: PublishSubject<Result<Listing, FuelError>>
    lateinit var viewModel: HomeViewModel
    lateinit var homeRepo: HomeRepo
    lateinit var viewAction: HomeContract.ViewAction

    @Before
    fun setup() {
        retrySubject = PublishSubject.create()
        loadMoreSubject = PublishSubject.create()
        getMorePostsSubject = PublishSubject.create()
        subredditSelectedSubject = PublishSubject.create()
        randomSubredditSelectedSubject = PublishSubject.create()
        input = mockk()
        homeRepo = mockk()
        viewAction = mockk(relaxed = true)

        every { input.retry } returns retrySubject
        every { input.loadMore } returns loadMoreSubject
        every { input.subredditSelected } returns subredditSelectedSubject
        every { input.randomSubredditSelected } returns randomSubredditSelectedSubject

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
        val redditLinks: List<RedditLink> = listOf(RedditLink(value = RedditLinkData(url = "asdf")))
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

        val redditLinks: List<RedditLink> = listOf(RedditLink(value = RedditLinkData(url = "asdf")))
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
        val redditLinks: List<RedditLink> = listOf(RedditLink(value = RedditLinkData(url = "asdf")))
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

    fun makeFakeResult(redditLinks: List<RedditLink>): Result<Listing, FuelError> {
        val fakeResult = mockk<Result<Listing, FuelError>>()
        val fakeListing = mockk<Listing>()
        val fakeListingData = mockk<ListingData>()
        every { fakeListingData.getRedditImageLinks() } returns redditLinks
        every { fakeListing.value } returns fakeListingData
        every { fakeResult.component1() } returns fakeListing
        every { fakeResult.component2() } returns mockk()
        return fakeResult
    }
}
