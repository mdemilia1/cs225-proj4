# Status #

| Type                  | Status                                                             |
|:----------------------|:-------------------------------------------------------------------|
| `master` build status | [![Build status][travis-status-master]][travis]                    |
| Latest stable version | [![Latest stable version][bintray-stable-version]][bintray-stable] |
| Latest dev version    | [![Latest dev version][bintray-dev-version]][bintray-dev]          |

# Tools #

* Version control system (VCS): [GitHub][github]
* Issue tracker: [GitHub Issues][github-issues]
* Wiki: [GitHub Wiki][github-wiki]
* Continous integration (CI): [Travis CI][travis]
* Binary repository manager:
	* [Bintray][bintray]: has both stable and dev versions
	* [GitHub Releases][github-releases]: only has stable versions

If you're looking for **downloads**, try [Bintray][bintray].

If you want to check the **current status** of a branch or pull request, including whether it's building correctly, use [Travis CI][travis].

# System requirements #

* Running
	* Java SE 8 or greater (typically fulfilled by Oracle JRE or OpenJDK JRE)
	* Java EE 7 or greater (typically fulfilled by GlassFish)
	* Swing (ships with Oracle JRE and non-headless builds of OpenJDK JRE)
* Building
	* Maven 3
	* JDK 8 (typically fulfilled by Oracle JDK or OpenJDK)
	* Active internet connection
* Developing
	* Maven-compatible IDE with autocompletion and error correction
		* Recommended: IntelliJ IDEA Community 2016.1 or better

# Building #

From the command line, with Maven 3 installed in `$PATH`/`%PATH%`:

	mvn package

From IntelliJ: see [the corresponding wiki page][github-wiki-intellij]



[github]:                 https://github.com/massbay-cs/cs225-proj4 "GitHub"
[github-issues]:          https://github.com/massbay-cs/cs225-proj4/issues "Issues"
[github-wiki]:            https://github.com/massbay-cs/cs225-proj4/wiki "Wiki"
[github-wiki-intellij]:   https://github.com/massbay-cs/cs225-proj4/wiki/IntelliJ "Development: IntelliJ"
[github-releases]:        https://github.com/massbay-cs/cs225-proj4/releases "GitHub Releases"

[travis]:                 https://travis-ci.org/massbay-cs/cs225-proj4 "Travis CI"
[travis-status-master]:   https://travis-ci.org/massbay-cs/cs225-proj4.svg?branch=master "Build status: master"

[bintray]:                https://bintray.com/massbay-cs/cs225-proj4 "Bintray"
[bintray-dev]:            https://bintray.com/massbay-cs/cs225-proj4/cs225-proj4-dev/_latestVersion "Latest dev version"
[bintray-dev-version]:    https://api.bintray.com/packages/massbay-cs/cs225-proj4/cs225-proj4-dev/images/download.svg "Latest dev version"
[bintray-stable]:         https://bintray.com/massbay-cs/cs225-proj4/cs225-proj4-stable/_latestVersion "Latest stable version"
[bintray-stable-version]: https://api.bintray.com/packages/massbay-cs/cs225-proj4/cs225-proj4-stable/images/download.svg "Latest stable version"
