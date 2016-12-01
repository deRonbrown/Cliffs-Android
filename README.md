# Cliffs

## Overview

Cliffs is a library that makes managing and tagging summary-style analytics events
easy. What is a summary event? A summary event is an analytics event that captures
information on how a user interacts with a particular section of your app or an entire
app usage session (e.g. between foreground and background). For example, suppose you
have a social app that contains a feed of posts. Instead of (or in addition to) tagging
a separate event for each post that is liked or shared, a summary event could tag a
single event with attributes "Number of Posts Liked", "Number of Posts Shared", and/or
"Number of Posts Viewed" when the user leaves that particular view or backgrounds the
app. This allows you to more easily answer questions like "On average how many posts
are liked by users within a given session" without having to look through thousands of
individual "like" events.

## Philosophy

Generally, Android apps separate user objectives into different `Activity` classes.
With Cliffs each `Activity` can report it's own summary event by extending the
`SummaryActivity` class. For example, suppose you have a sports app that contains
a `GamesActivity`, a `TeamActivity`, and a `FavoritesActivity`. As the user interacts with and leaves each `Activity`, Cliffs can automatically report "Game Viewed",
"Team Viewed", and "Favorites Viewed" events containing specific information for
what the user did in each view.

`Activity` summaries can be "simple reporting". This means the summary event
will be automatically tagged when the user leaves the view (i.e. `onPause()`)
or when the users backgrounds the app. Summaries that start in a particular
`Activity` but end in a different `Activity` or another future moment,
such as an onboarding flow, will need to be manually reported.

`Activity` summaries also support screen tagging, which are different than
event tags in some analytics providers. Via the automated screen tagging in Cliffs,
a "Previous Screen" attribute will be included in the summary event so
you can easily understand users' previous state before starting a particular objective.

Cliffs also supports summaries not tied to any particular `Activity`, such
as a session summary. These can be created and reported as necessary.

## Summary Event Class

Summary events are created by implementing the `TrackingSummary` interface or
extending the `AbstractSummary` class. Several different property types are
supported.
* Identifier: Unique identifier only included for storing the summary in a
`Map` - not included in the final event attributes.
* Name: The name of the event that will be tagged.
* Flags: `true` and `false` values - default to `false`.
* Counters: `long` values that can be incremented and decremented.
* Timers: `Timer` values that keep time in seconds. Each summary automatically
contains a "Time Spent (Seconds)" timer.
* Attributes: Arbitrary key/values.

Each `AbstractSummary` subclass must override the `shouldReportOnBackground()`
and `getName()`. If `shouldReportOnBackground()` returns `false`, you must make sure to report the summary at an appropriate time in the future.

## SummaryActivity class

Each `SummaryActivity` subclass must override the following methods:

#### `protected boolean isSimpleSummary();`

If this method returns `true`, the summary event will be automatically tagged when
the user leaves the view, including when the user backgrounds the app
(i.e. `Activity#onPause()`). If the method returns `false`, you must make sure to
report the summary event for this `Activity` at an appropriate time in the
future.

#### `protected String getScreenName();`

Return the screen name to be associated with this `Activity`.

#### `protected TrackingSummary createSummary();`

Create an instance of the `TrackingSummary` interface (or `AbstractSummary`
subclass) associated with this `Activity`.

## Integration

1. For each summary event that you want to report, create a class implementing
the `TrackingSummary` interface. The easiest way is to extend the `AbstractSummary`
class. Be sure to initialize all flags, counters, timers, and attributes to
a default state in the constructor.
2. Subclass `SummaryActivity` in each of your `Activities` and override
the methods described above. For non-simple summaries, be sure to report them when
necessary.
3. Create and report any other non-`Activity` summaries as needed.
4. In your `Application` class's `onCreate()` method, initialize Cliffs with an
`Analytics` callback. The `Analytics` interface connects
Cliffs to your analytics providers.

## Example

To run the example project, clone the repo, and run the "Cliffs Sample" project.

## Author

DeRon Brown, dmbrown2010@gmail.com

## License

Cliffs is available under the MIT license. See the LICENSE file for more info.
