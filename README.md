[![](https://img.shields.io/badge/Kotlin-1.2.71-blue.svg)](https://kotlinlang.org) [![](https://app.bitrise.io/app/41a292091e9fb0ad.svg?token=Zdc9-YXqjcj5P5HEqTPU1Q)](https://app.bitrise.io/app/41a292091e9fb0ad)

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
The codebase is written in Kotlin. The architecture used is MVVM with RxJava. The `ViewModel` contains no Android related code, so the main logic can be [unit tested](https://github.com/worker8/Pixels/blob/b34b0a5fdf6e5e63f9298ccea51f08afaef792ca/app/src/test/java/beepbeep/pixelsforreddit/home/HomeViewModelTest.kt). 

##### RxJava
Asides from being good at manipulating threads, RxJava is very good at redirecting the streams of events. So I've decided to use in in almost every app I made. As an example, 4 different events such as: initial launch, retrying after connection failure, loading more, changing subreddit, all of them leads to the same things -- **getting more posts**, RxJava can help to merge all this flow into 1: [example code](https://github.com/worker8/Pixels/blob/b34b0a5fdf6e5e63f9298ccea51f08afaef792ca/app/src/main/java/beepbeep/pixelsforreddit/home/HomeViewModel.kt#L47)

A few tips for those unfamiliar with RxJava:
- use `doOnNext` instead of `map` when you don't need to mutate the data
- remember to `dispose()` when you don't need it anymore, otherwise use `clear()`
- avoid the use of `subjects` whenever possible (I think I can reduce a few of them...)
  - use RxBinding instead of subject for making views into Rx
- for expected errors, don't throw error but instead show correct retry UI accordingly since it will terminate the Rx stream.
 
##### Icons & Resources
SVG resources is preferrred since `VectorDrawableCompat` can enough to support for the min sdk version 21 in this app. [Adaptive icon](https://developer.android.com/guide/practices/ui_guidelines/icon_design_adaptive) is used for the logo. Vector graphics can be created by Sketch(paid, cheaper), Adobe Illustrator (paid, expensive) or Inkscape (free). I used Illustrator and Sketch personally.

The steps for creating usable vector drawable is roughly:
- create your design using any design tools mentioned above (or any other tools)
- export as **svg**
- import **svg** into Android Studio, this will turn into a drawable xml
- use drawable in `ImageView` like [here](https://github.com/worker8/Pixels/blob/98bf611db28bf2f406576058cc4b981ddf14f1d6/app/src/main/res/layout/_navigation_night_mode.xml#L15)


##### Theming
Since theming is fun, so this app is built with the capability to change theme in the future. To achieve, don't use resources directly. 

Don't :point_down: :no_good_woman:  
`android:background="@color/blue"`

Do :smiley: 
`android:background="?pixelBackgroundColor"`

##### Modularization
Currently only the network layer is extracted as a separate module. I currently do not have enough knowledge on Reddit Api, but this can potentially become a Reddit Kotlin client in the future, if I gain enough knowledge.

Since the codebase is very small now, I only have network module and main app module. It can be further divided in the future.

##### Testing, CI, Danger & Linting
Currently only unit test is added, because I haven't setup the integration tests and it doesn't bring too much help now. Bitrise CI is used to run the unit test. The Bitrise link badge is available at the top of this page.

Android Lint is also setup in Bitrise CI to help catch warnings. Whenever a PR is opened in Github, it will trigger Bitrise to run [Danger](https://github.com/danger/danger), Danger will then run Android Lint and produce a lint report file, the report file is then parsed by the [Danger-Android-Lint-library](https://github.com/loadsmart/danger-android_lint) and posted back to the PR. 

Try to fix all the warnings before merging, unless you know what the error is, and choose to ignore. In that case, feel free to open a PR to fix the `lint.xml` so that it doesn't produce meaningless warning in the future.

##### Coding styles
`.editorconfig` is used in this project to make sure that the spacing and indentations are standardized, the `editorconfig` is obtained from [ktlint project](https://github.com/shyiko/ktlint/blob/master/.editorconfig). 

