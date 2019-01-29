[![](https://img.shields.io/badge/Kotlin-1.3.11-blue.svg)](https://kotlinlang.org) [![](https://app.bitrise.io/app/41a292091e9fb0ad.svg?token=Zdc9-YXqjcj5P5HEqTPU1Q)](https://app.bitrise.io/app/41a292091e9fb0ad)

### Pixels for Reddit Introduction
<img src="https://user-images.githubusercontent.com/1988156/50760475-4ce08180-12ab-11e9-87ec-d9bb7fb512cb.png" width="150px"/>

This is a simple Reddit client to browse interesting pictures from various subreddits. I made this app to improve my Android programming skills and to build something I personally interested in.

#### Playstore Link
The app is currently available in Google Playstore as Open Beta. :point_down: 

[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="200px"/>](https://play.google.com/store/apps/details?id=beepbeep.pixelsforredditx&utm_source=github)

#### Features
The features are currently pretty limited! But here's a few that is available:
- browse posts
- change subreddit
- randomize subreddit
- night mode


#### Screenshots
| screenshots | |
| - | - |
| <img src="https://user-images.githubusercontent.com/1988156/50760058-0b9ba200-12aa-11e9-99f5-8c88c7418d2f.png" width="200px" /> | <img src="https://user-images.githubusercontent.com/1988156/50760059-0b9ba200-12aa-11e9-9917-71c25d9e0fbd.png" width="200px" /> |
| <img src="https://user-images.githubusercontent.com/1988156/50760060-0c343880-12aa-11e9-899d-d0d2af262693.png" width="200px" /> | <img src="https://user-images.githubusercontent.com/1988156/50760061-0c343880-12aa-11e9-8c20-fc0436998bc7.png" width="200px" /> |


### Technical Details
##### General Architecture 
The codebase is written in Kotlin. The architecture used is MVVM with RxJava. The `ViewModel` contains no Android related code, so the main logic can be unit tested: [example, HomeViewModelTest.kt](https://github.com/worker8/Pixels/blob/b34b0a5fdf6e5e63f9298ccea51f08afaef792ca/app/src/test/java/beepbeep/pixelsforreddit/home/HomeViewModelTest.kt). More information written in the wiki: [Architecture Explanation](https://github.com/worker8/Pixels/wiki/Architecture-Explanation).
 
##### Icons, Resources and Theming
SVG icons are preferred in this project, and night mode is supported. More details are in the wiki: [Assets, Resources & Theming
](https://github.com/worker8/Pixels/wiki/Assets,-Resources-&-Theming).

##### Testing, CI, Danger & Linting
Bitrise CI is used, and only JUnit test is available. No integration tests are added yet. Danger and Android Linting are used to report warnings in PR. More about how all they work in the wiki: [Bitrise CI, Danger, Android Lint Explanation](https://github.com/worker8/Pixels/wiki/Bitrise-CI,-Danger,-Android-Lint-Explanation).

##### Coding styles
`.editorconfig` is used in this project to make sure that the spacing and indentations are standardized, the `editorconfig` is obtained from [ktlint project](https://github.com/shyiko/ktlint/blob/master/.editorconfig). 

